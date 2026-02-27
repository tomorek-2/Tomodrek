package Tomodrek;

import arc.Core;
import arc.func.Cons;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.world.blocks.defense.Wall;

public class PortalBlock extends Wall {
    private final String targetIp = "155.212.218.241";
    private final int targetPort = 6568;

    public PortalBlock(String name) {
        super(name);
        consumesTap = true;
        configurable = true;
        update = false;
    }

    public class PortalBuild extends WallBuild {
        @Override
        public boolean configTapped() {
            // ✅ Правильный поиск игрока через Vars
            Player player = Vars.playerController.player();
            
            if (player == null) {
                return false;
            }

            // ✅ Используем Core.app.post для безопасности потока
            Core.app.post(() -> {
                try {
                    // Проверка: работает только на клиенте
                    if (Vars.isServer()) {
                        player.unit.getTeam().player.sendMessage("[scarlet]Портал работает только на клиенте!");
                        return;
                    }

                    // Если уже подключены — сначала отключаемся
                    if (Vars.netClient != null && Vars.netClient.active()) {
                        Vars.netClient.disconnect();
                        Log.info("Portal: Disconnected from current server");
                    }

                    // ✅ Правильная сигнатура connect с callback из официального API [[46]]
                    if (Vars.netClient != null) {
                        Vars.netClient.connect(targetIp, targetPort, () -> {
                            Log.info("Portal: Connected successfully to " + targetIp + ":" + targetPort);
                            player.unit.getTeam().player.sendMessage("[cyan]Успешное подключение к " + targetIp + ":" + targetPort);
                        });
                        
                        Log.info("Portal: Initiating connection to " + targetIp + ":" + targetPort);
                        player.unit.getTeam().player.sendMessage("[cyan]Подключение к " + targetIp + ":" + targetPort);
                    } else {
                        Log.err("Portal: NetClient is null!");
                        player.unit.getTeam().player.sendMessage("[scarlet]Ошибка: Network client not initialized!");
                    }

                } catch (Exception e) {
                    Log.err("Portal error:", e);
                    player.unit.getTeam().player.sendMessage("[scarlet]Ошибка подключения: " + e.getMessage());
                }
            });

            return true;
        }
    }
}