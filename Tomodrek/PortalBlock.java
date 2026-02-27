package Tomodrek;

import arc.Core;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.world.blocks.defense.Wall;

public class PortalBlock extends Wall {
    private final String targetIp = "155.212.218.241";
    private final int targetPort = 6567;

    public PortalBlock(String name) {
        super(name);
        consumesTap = true;
        configurable = true;
        update = false;
    }

    public class PortalBuild extends WallBuild {
        @Override
        public boolean configTapped() {
            // ✅ Правильное получение игрока в v155
            Player player = getPlayerTapped();
            if (player == null) {
                return false;
            }

            // ✅ Оборачиваем в Core.app.post() для безопасности потока
            Core.app.post(() -> {
                try {
                    // ✅ Проверка на сервер через Vars.client
                    if (Vars.client == null || !Vars.client.isConnected()) {
                        // Это клиент
                    } else {
                        // Находимся на сервере - отменяем
                        Log.info("Portal: Cannot connect from server");
                        return;
                    }

                    // ✅ Подключение через NetClient напрямую
                    if (Vars.netClient != null) {
                        Vars.netClient.connect(targetIp, targetPort);
                        Log.info("Portal: Initiating connection to " + targetIp + ":" + targetPort);
                        
                        // Отправляем сообщение игроку правильно
                        player.name.sendMessage("[cyan]Подключение к " + targetIp + ":" + targetPort);
                    } else {
                        Log.err("Portal: NetClient is null!");
                        player.name.sendMessage("[scarlet]Ошибка: Network client not initialized!");
                    }

                } catch (Exception e) {
                    Log.err("Portal error:", e);
                    player.name.sendMessage("[scarlet]Ошибка подключения: " + e.getMessage());
                }
            });

            return true;
        }

        private Player getPlayerTapped() {
            // ✅ Правильный поиск игрока в v155
            return Groups.player.find(p -> p.within(this, 10f * Vars.tileSize));
        }
    }
}