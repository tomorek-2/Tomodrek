package Tomodrek;

import arc.*;
import arc.util.*;
import mindustry.Vars;
import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class VoiceChat001 {
    private static final AudioFormat FORMAT = new AudioFormat(16000, 16, 1, true, false);
    private TargetDataLine mic;
    private SourceDataLine speakers;
    public volatile boolean recording = false;
    private final LinkedBlockingQueue<byte[]> audioQueue = new LinkedBlockingQueue<>(100);

    public VoiceChat001() {
        // Регистрация через наш статический метод
        VoiceChat002.register();

        Vars.net.handleClient(VoiceChat002.class, packet -> {
            // КРИТИЧНО: Никогда не принимаем пакеты от самих себя
            if (packet.senderId != Vars.player.id) {
                // Если в очереди больше 3 пакетов (~100мс задержки), 
                // сбрасываем самый старый, чтобы звук не "тянулся" и не повторялся
                if (audioQueue.size() > 3) {
                    audioQueue.poll();
                }
                audioQueue.offer(packet.audioData);
            }
        });
        
        initSpeakers();
        startPlaybackThread();
    }

    private void initSpeakers() {
        try {
            DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, FORMAT);
            speakers = (SourceDataLine) AudioSystem.getLine(outInfo);
            speakers.open(FORMAT, 4096);
            speakers.start();
        } catch (Exception e) {
            Log.err("[Tomodrek-Voice] Ошибка динамиков: " + e.getMessage());
        }
    }

    private void startPlaybackThread() {
        Threads.daemon("Voice-Playback", () -> {
            while (true) {
                try {
                    byte[] data = audioQueue.take();
                    if (speakers != null && speakers.isOpen()) {
                        speakers.write(data, 0, data.length);
                    }
                } catch (Exception ignored) {}
            }
        });
    }

    public void update() {}

    public boolean start() {
        if (recording) return true;
        // Не начинаем запись, если мы еще не на сервере или ID не получен
        if (!Vars.net.active() || Vars.player == null || Vars.player.id == -1) {
            Log.warn("[Tomodrek-Voice] Нельзя включить микрофон: нет связи с сервером.");
            return false;
        }

        try {
            DataLine.Info inInfo = new DataLine.Info(TargetDataLine.class, FORMAT);
            mic = (TargetDataLine) AudioSystem.getLine(inInfo);
            mic.open(FORMAT);
            mic.start();
            recording = true;

            Threads.daemon("Voice-Capture", () -> {
                byte[] tempBuffer = new byte[1024];
                try {
                    while (recording && mic != null && mic.isOpen()) {
                        int count = mic.read(tempBuffer, 0, tempBuffer.length);
                        
                        // Проверяем связь в каждом цикле
                        if (count > 0 && recording && Vars.net.active() && Vars.player.id != -1) {
                            long sum = 0;
                            for (int i = 0; i < count; i++) sum += Math.abs(tempBuffer[i]);
                            
                            if (sum > (long)count * 20) {
                                VoiceChat002 packet = new VoiceChat002(Arrays.copyOf(tempBuffer, count), Vars.player.id);
                                
                                try {
                                    if (Vars.net.server()) {
                                        Momodrek003.relayVoice(null, packet);
                                    } else {
                                        Vars.net.send(packet, false); // UDP
                                    }
                                } catch (Exception e) {
                                    Log.err("[Tomodrek-Voice] Ошибка отправки пакета");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.err("[Tomodrek-Voice] Поток записи упал: " + e.getMessage());
                } finally {
                    stop();
                }
            });

            Log.info("[Tomodrek-Voice] Микрофон запущен.");
            return true;
        } catch (Exception e) {
            Log.err("[Tomodrek-Voice] Сбой запуска: " + e.getMessage());
            recording = false;
            return false;
        }
    }

    public synchronized void stop() {
        recording = false;
        if (mic != null) {
            try { mic.stop(); mic.close(); } catch (Exception ignored) {}
            mic = null;
        }
    }
}
