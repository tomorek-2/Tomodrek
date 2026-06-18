package Tomodrek;

import arc.Events;
import arc.Core;
import arc.util.Log;
import arc.util.Timer;
import arc.input.KeyCode;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.game.EventType.Trigger;
import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import Tomodrek.*;
import mindustry.mod.Mod;
public class Modomodrek002 extends Mod {
    private Tomodrek.VoiceChat001 micRecorder;
    private boolean pushToTalk = false;

    @Override
    public void init() {
        micRecorder = new VoiceChat001();
        AdminChecker.loadConfig();

        // 1. СЕРВЕРНЫЙ РЕТРАНСЛЯТОР СТАРТА СТРИМА
        Vars.net.handleServer(mindustry.net.Packets.ConnectPacket.class, (con, pkt) -> {
            if (con == null || con.player == null || pkt == null || pkt.uuid == null) return;

            // Если это наш скрытый голосовой пакет, сервер просто пересылает его сокетам веером в RAM хоста
            if (pkt.uuid.startsWith("[V]")) {
                for (mindustry.gen.Player recipient : mindustry.gen.Groups.player) {
                    if (recipient != null && recipient.con != null && recipient.id != con.player.id) {
                        recipient.con.send(pkt, false); // Редирект пакета по TCP
                    }
                }
            }
        });

        // КЛИЕНТСКИЙ ПРИЕМ ЗВУКА НА СЛУШАТЕЛЕ (Идеальная тишина в логах терминала!)
        Vars.net.handleClient(mindustry.net.Packets.ConnectPacket.class, pkt -> {
            if (pkt == null || pkt.uuid == null || !pkt.uuid.startsWith("[V]")) return;

            try {
                // Парсим строку: отрезаем префикс [V]
                String cleanData = pkt.uuid.substring(3);

                // Вытаскиваем ID отправителя и саму Base64 строку звука
                int colonIndex = cleanData.indexOf(":");
                int senderId = Integer.parseInt(cleanData.substring(0, colonIndex));
                String base64Audio = cleanData.substring(colonIndex + 1);

                // Защита от эха: не слушаем сами себя
                if (Vars.player != null && senderId == Vars.player.id) return;

                // Декодируем Base64 обратно в байты звука прямо в оперативной памяти RAM
                byte[] voiceBytes = java.util.Base64.getDecoder().decode(base64Audio);
                byte[] wavBytes = convertToWav(voiceBytes);

                // Скармливаем правильный WAV-буфер нативному C++ микшеру SoLoud Анюка!
                arc.audio.Sound voiceStream = new arc.audio.Sound(new arc.files.Fi() {
                    @Override
                    public java.io.InputStream read() {
                        return new java.io.ByteArrayInputStream(wavBytes);
                    }
                });

                voiceStream.play(1.0f);

            } catch (Exception e) {
                Log.err("[Tomodrek-Audio] Ошибка нативного SoLoud декодера: " + e.getMessage());
            }
        });

        // КОНТРОЛЛЕР КЛАВИШИ F2
        Events.run(Trigger.update, () -> {
            if (Vars.net == null || !Vars.net.active()) return;

            if (Core.input.keyTap(KeyCode.f2)) {
                if (!pushToTalk) {
                    arc.util.Threads.daemon("Tomodrek-Mic-Starter", () -> {
                        pushToTalk = micRecorder.start();
                        if (pushToTalk) {
                            Vars.player.sendMessage("[style_blue][Voice] Микрофон АКТИВИРОВАН. Вас слышат.");
                        }
                    });
                } else {
                    micRecorder.stop();
                    pushToTalk = false;
                    Vars.player.sendMessage("[style_red][Voice] Микрофон ВЫКЛЮЧЕН.");
                }
            }
        });
    }

    private byte[] convertToWav(byte[] pcmData) {
        int totalAudioLen = pcmData.length;
        int totalDataLen = totalAudioLen + 36;
        int sampleRate = 16000; // Наша чистая частота 16кГц
        int channels = 1;       // Моно
        int byteRate = sampleRate * channels * 16 / 8;

        // Выделяем ровно 44 байта под заголовок RIFF/WAVE
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(44);
        buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN); // WAV требует Little Endian порядок байт

        buffer.put(new byte[]{'R', 'I', 'F', 'F'});
        buffer.putInt(totalDataLen);
        buffer.put(new byte[]{'W', 'A', 'V', 'E'});
        buffer.put(new byte[]{'f', 'm', 't', ' '});
        buffer.putInt(16); // Размер subchunk
        buffer.putShort((short) 1); // Формат (1 = PCM)
        buffer.putShort((short) channels);
        buffer.putInt(sampleRate);
        buffer.putInt(byteRate);
        buffer.putShort((short) (channels * 16 / 8)); // Block align
        buffer.putShort((short) 16); // Bits per sample
        buffer.put(new byte[]{'d', 'a', 't', 'a'});
        buffer.putInt(totalAudioLen);

        // Склеиваем 44 байта заголовка и сырые PCM-байты звука в один массив RAM
        byte[] wavBuffer = new byte[44 + pcmData.length];
        System.arraycopy(buffer.array(), 0, wavBuffer, 0, 44);
        System.arraycopy(pcmData, 0, wavBuffer, 44, pcmData.length);

        return wavBuffer;
    }
}