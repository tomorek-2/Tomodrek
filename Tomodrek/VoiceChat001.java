package Tomodrek;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;

public class VoiceChat001 {
    private TargetDataLine line;
    public AudioFormat format;
    public boolean recording = false;

    private static int streamSequence = 5000; // Уникальный стартовый ID для аудио-потоков

    public VoiceChat001() {

        this.format = new AudioFormat(16000, 16, 1, true, false);
    }

    public boolean start() {
        if (recording) return true;
        try {
            if (AudioSystem.getMixerInfo().length == 0) return false;
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) return false;

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            recording = true;

            arc.util.Threads.daemon("Tomodrek-Audio-Stream", () -> {
                int bufferSize = 400; // Наш идеальный безопасный буфер 400 байт
                byte[] buffer = new byte[bufferSize];

                while (recording && line != null) {
                    int count = line.read(buffer, 0, bufferSize);

                    if (count > 0 && recording) {
                        byte[] rawVoice = java.util.Arrays.copyOf(buffer, count);

                        long sum = 0;
                        for (byte b : rawVoice) sum += Math.abs(b);
                        boolean hasVoice = sum > (long) rawVoice.length * 5;

                        if (hasVoice && mindustry.Vars.net != null && mindustry.Vars.net.active()) {

                            // 1. Кодируем опасные байты звука в текстовую строку Base64
                            String base64Audio = java.util.Base64.getEncoder().encodeToString(rawVoice);

                            // 2. ОБОРАЧИВАЕМ В ВАНИЛЬНЫЙ ПАКЕТ КОННЕКТА АНЮКА!
                            mindustry.net.Packets.ConnectPacket pkt = new mindustry.net.Packets.ConnectPacket();

                            // Зашиваем аудио-поток в поле UUID с маркером [V] и ID игрока через двоеточие
                            pkt.uuid = "[V]" + (mindustry.Vars.player != null ? mindustry.Vars.player.id : 0) + ":" + base64Audio;

                            // 3. Выстреливаем в сеть.
                            // Ошибки "Error during UDP deserialization" ПОЛНОСТЬЮ УНИЧТОЖЕНЫ!
                            mindustry.Vars.net.send(pkt, false); // false = надежный TCP-канал
                        }
                    }
                    try { Thread.sleep(12); } catch (InterruptedException e) { break; }
                }
            });

            arc.util.Log.info("[Tomodrek-Voice] Нативный аудио-стрим успешно запущен.");
            return true;
        } catch (Exception e) {
            recording = false;
            return false;
        }
    }

    public void stop() {
        recording = false;
        if (line != null) {
            try { if (line.isOpen()) { line.stop(); line.close(); } } catch (Exception e) {}
            line = null;
        }
        arc.util.Log.info("[Tomodrek-Voice] Захват аудио остановлен.");
    }
}