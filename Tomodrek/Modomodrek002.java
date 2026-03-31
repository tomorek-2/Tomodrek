package Tomodrek;

import arc.*;
import arc.audio.*;
import arc.events.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.type.Category;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.game.Schematics;

import mindustry.game.EventType;
import mindustry.world.Block;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.game.EventType.PlayerChatEvent;
import arc.Input;
import arc.input.KeyCode;
import mindustry.game.EventType.Trigger;
import arc.Core;
import mindustry.game.Rules;
import mindustry.core.World;
import java.lang.reflect.Field;
import mindustry.game.FogControl;
import java.lang.reflect.*;

import java.lang.reflect.Modifier;
import arc.util.Log;

import mindustry.game.Schematics;
import mindustry.input.*;
import mindustry.input.InputHandler;

import mindustry.mod.*;


import javax.sound.sampled.*;
import java.io.*;

public class Modomodrek002 extends Mod {
    private VoiceChat001 micRecorder;
    private int timer = 0;
    private boolean pushToTalk = false;

    

    @Override
    public void init() {
       
        micRecorder = new VoiceChat001();
    }

    @Override
    public void load() {
        // Подписка на входящие пакеты (Клиент)
        Events.on(EventType.ClientLoadEvent.class, e -> {
            Vars.netClient.addListener(VoiceChat002.class, packet -> {
                playSound(packet.audioData);
            });
        });

        // Обработка на сервере (Пересылка всем игрокам)
        if (Vars.net.server()) {
            Vars.netServer.addListener(VoiceChat002.class, packet -> {
                for (Player p : Groups.player) {
                    if (p.id != packet.senderId) {
                        p.sendPacketReliable(packet);
                    }
                }
            });
        }

        // Основной цикл игры (Обновление каждый кадр)
        Events.run(Trigger.update, () -> {
            if (!Vars.net.active()) return;

            // Управление кнопкой V (Запись)
            if (Core.input.keyDown(KeyCode.f2) && !pushToTalk) {
                pushToTalk = micRecorder.start();
                if (pushToTalk) {
                    Log.info("[Tomodrek] Микрофон включён");
                }
            }
            if (Core.input.keyDown(KeyCode.f2) && pushToTalk) {
                micRecorder.stop();
                pushToTalk = false;
                Log.info("[Tomodrek] Микрофон выключен");
            }

            // Отправка звука (каждые 10 кадров ≈ 6 раз в секунду)
            if (pushToTalk && micRecorder.recording) {
                timer++;
                if (timer >= 5) {
                    byte[] data = micRecorder.readBuffer(4096);
                    
                    // Проверка на тишину (чтобы не слать мусор)
                    if (data.length > 0 && hasVoice(data)) {
                        sendVoiceChat002(data);
                    }
                    timer = 0;
                }
            }
        });
    }

    // Отправка пакета на сервер
    private void sendVoiceChat002(byte[] data) {
        if (Vars.player == null || Vars.player.unit() == null) return;
        
        VoiceChat002 pkt = new VoiceChat002();
        pkt.audioData = data;
        pkt.senderId = Vars.player.id;
        
        Vars.netClient.sendToServerReliable(pkt);
    }

    // Воспроизведение звука (Клиент)
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
        } catch (Exception e) {
            // Тихая ошибка (чтобы не спамить лог)
        }
    }

    // Проверка на наличие голоса (VAD)
    private boolean hasVoice(byte[] data) {
        int sum = 0;
        for (byte b : data) sum += Math.abs(b);
        return sum > data.length * 5; // Порог громкости
    }

    @Override
    public void dispose() {
        if (micRecorder != null) micRecorder.stop();
    }
}
