package Tomodrek;

import arc.*;
import arc.input.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import mindustry.net.Net;
import mindustry.net.*;

import javax.sound.sampled.*;
import java.io.*;

public class Modomodrek002 extends Mod {
    private VoiceChat001 micRecorder; // Запись (микрофон)
    private int timer = 0;
    private boolean pushToTalk = false;

    public Modomodrek002() {
        Log.info("[Tomodrek] Конструктор загружен");
    }

    @Override
    public void init() {
        Log.info("[Tomodrek] Инициализация голосового чата...");
        micRecorder = new VoiceChat001();
 Net.registerPacket(() -> new VoiceChat002());

        
        Vars.netClient.handleClient(VoiceChat002.class, packet -> {
            
            playSound(packet.audioData);
            Vars.player.send(new VoiceChat002(audioData));
        });

        // Если пакет обрабатывается на сервере:
        Vars.netServer.handleServer(VoiceChat002.class, (packet, sender) -> {
            
            Vars.net.send(new VoiceChat002(audioData), true);
            
        });
      

    
  //  byte[] audio = packet.audioData;
//playSound(packet.audioData);
    

        // Основной цикл игры
    Events.run(Trigger.update, () -> {
            if (!Vars.net.active()) return;

            // Кнопка V
            if (Core.input.keyDown(KeyCode.f2) && !pushToTalk) {
                pushToTalk = micRecorder.start();
            }
            if (Core.input.keyDown(KeyCode.f2) && pushToTalk) {
                micRecorder.stop();
                pushToTalk = false;
            }

            // Отправка
            if (pushToTalk && micRecorder.recording) {
                timer++;
                if (timer >= 10) {
                    byte[] data = micRecorder.readBuffer(4096);
                    if (data.length > 0 && hasVoice(data)) {
                        sendVoicePacket(data);
                    }
                    timer = 0;
                }
            }
        });
    }

    private void sendVoicePacket(byte[] data) {
        if (Vars.player == null) return;
        
        // ✅ СОЗДАНИЕ ПАКЕТА (Используем VoiceChat002!)
        VoiceChat002 pkt = new VoiceChat002();
        pkt.audioData = data;
        pkt.senderId = Vars.player.id;
        
        Vars.net.send(pkt, false);
    }

    private void playSound(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            AudioInputStream ais = new AudioInputStream(
                new ByteArrayInputStream(data),
                new AudioFormat(16000, 16, 1, true, false),
                data.length
            );
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {}
    }

    private boolean hasVoice(byte[] data) {
        int sum = 0;
        for (byte b : data) sum += Math.abs(b);
        return sum > data.length * 5;
    }
}
