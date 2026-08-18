package Tomodrek;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.game.EventType.*;
import mindustry.net.NetConnection;

/**
 * Вайбкодинг, ГЧ работает но с минусами
 */
public class Momodrek003 {
    
    public static void init() {
        Log.info("[Tomodrek-Engine] Инициализация Momodrek003...");

        // Регистрируем пакет
        VoiceChat002.register();

        // 1. Голосовой ретранслятор (Relay) для VoiceChat002
        Vars.net.handleServer(Tomodrek.VoiceChat002.class, (con, packet) -> {
            if(packet.senderId != con.player.id) {con.kick("Invalid packet data: ID spoofing detected.");
                return;}
            relayVoice(con, packet);
        });


    }

    /**
     * Ретрансляция голоса (вынесено отдельно для поддержки локального хоста)
     */
    public static void relayVoice(NetConnection con, VoiceChat002 packet) {
        Groups.player.each(p -> p.con != null && p.con != con && p.con.isConnected(), p -> {
            try {
                p.con.send(packet, false);
            } catch (Exception ignored) {}
        });
    }

    /**
     * Бесшовное перенаправление игрока (BungeeCord style)
     */
    public static void redirectTo(Player player, String ip, int port) {
        if (player == null || player.con == null) return;
        Log.info("[Hub] Перенаправление @ на @:@", player.name, ip, port);
        Call.connect(player.con, ip, port);
    }

    public static void smartSearch(String query) {
        Log.info("[SmartSearch] Запрос: @", query);
    }
}
