package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.*;
import mindustry.core;

import mindustry.net.Call; // Этот импорт правильный
import mindustry.net.NetConnection; // Импортируем класс подключения
import mindustry.Vars; // Импортируем Vars для tilesize

public class PortalBlock extends Wall {
    // Адрес和目标 сервера
    private String targetIp = "155.212.218.241";
    private int targetPort = 6568;

    public PortalBlock(String name) {
        super(name);
        consumesTap = true; // Блок будет реагировать на нажатие
        configurable = true;
        update = false; // Нам не нужно обновление каждый тик
    }

    public class PortalBuild extends WallBuild {

        @Override
        public boolean configTapped() {
            // 1. Получаем игрока, который нажал на блок
            Player player = getPlayerTapped();

            if (player != null) {
                // 2. Получаем объект подключения (NetConnection) этого игрока
                NetConnection connection = player.con;

                // 3. Проверяем, что подключение существует и валидно
                if (connection != null && connection.isConnected()) {
                    // 4. Отправляем команду на смену сервера.
                    // ВАЖНО: Этот метод отправляет пакет КЛИЕНТУ, чтобы тот начал процесс подключения.
                    // Он работает ТОЛЬКО на СТОРОНЕ СЕРВЕРА.
                    Call.connect(connection, targetIp, targetPort);
                    return true; // Клик обработан
                }
            }
            return false; // Не удалось обработать клик
        }

        // Вспомогательный метод для поиска игрока рядом с блоком
        private Player getPlayerTapped() {
            // Ищем игрока в радиусе 10 клеток от блока.
            // Vars.tilesize - правильный способ получить размер тайла.
            return Groups.player.find(p -> p.within(this, 10f * Vars.tilesize));
        }
    }
}
