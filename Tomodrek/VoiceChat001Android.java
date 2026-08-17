package Tomodrek;

import arc.*;
import arc.util.*;
import mindustry.Vars;

import android.Manifest;
import android.media.*;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class VoiceChat001Android {
    private static final int SAMPLE_RATE = 16000;
    private AudioRecord mic;
    private AudioTrack speakers;
    public volatile boolean recording = false;
    private final LinkedBlockingQueue<byte[]> audioQueue = new LinkedBlockingQueue<>(100);

    public VoiceChat001Android() {
        // Регистрация пакета
        VoiceChat002.register();

        Vars.net.handleClient(VoiceChat002.class, packet -> {
            if (packet.senderId != Vars.player.id) {
                // Джиттер-буфер для Android
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

            int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, 
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            
            // Используем Builder (доступен с API 23+)
            speakers = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build())
                .setAudioFormat(new AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(SAMPLE_RATE)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build())
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build();
                
            speakers.play();
            Log.info("[Tomodrek-Voice] Android динамики запущены.");
        } catch (Exception e) {
            Log.err("[Tomodrek-Voice] Ошибка динамиков Android: " + e.getMessage());
        }
    }

    private void startPlaybackThread() {
        Threads.daemon("Voice-Playback-Android", () -> {
            while (true) {
                try {
                    byte[] data = audioQueue.take();
                    if (speakers != null && speakers.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                        speakers.write(data, 0, data.length);
                    }
                } catch (Exception ignored) {}
            }
        });
    }

    public void update() {}

    public boolean start() {
        if (recording) return true;
        if (!Vars.net.active() || Vars.player == null || Vars.player.id == -1) {
            Log.warn("[Tomodrek-Voice] Микрофон недоступен: нет связи.");
            return false;
        }

        try {
            int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, 
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            
            mic = new AudioRecord(MediaRecorder.AudioSource.MIC, 
                SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, 
                AudioFormat.ENCODING_PCM_16BIT, minBufferSize);

            if (mic.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.err("[Tomodrek-Voice] Не удалось инициализировать микрофон Android.");
                return false;
            }

            mic.startRecording();
            recording = true;

            Threads.daemon("Voice-Capture-Android", () -> {
                byte[] tempBuffer = new byte[1024];
                try {
                    while (recording && mic != null) {
                        int count = mic.read(tempBuffer, 0, tempBuffer.length);
                        
                        if (count > 0 && recording && Vars.net.active()) {
                            long sum = 0;
                            for (int i = 0; i < count; i++) sum += Math.abs(tempBuffer[i]);

                            if (sum > (long)count * 20) {
                                VoiceChat002 packet = new VoiceChat002(Arrays.copyOf(tempBuffer, count), Vars.player.id);
                                if (Vars.net.server()) {
                                    Momodrek003.relayVoice(null, packet);
                                } else {
                                    Vars.net.send(packet, false);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.err("[Tomodrek-Voice] Ошибка захвата Android: " + e.getMessage());
                } finally {
                    stop();
                }
            });

            Log.info("[Tomodrek-Voice] Android микрофон запущен.");
            return true;
        } catch (Exception e) {
            Log.err("[Tomodrek-Voice] Сбой запуска на Android: " + e.getMessage());
            recording = false;
            return false;
        }
    }

    public synchronized void stop() {
        recording = false;
        if (mic != null) {
            try {
                if (mic.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) mic.stop();
                mic.release();
            } catch (Exception ignored) {}
            mic = null;
        }
    }
}
