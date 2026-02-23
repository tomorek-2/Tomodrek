package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.*;
import mindustry.net.Call;

public class PortalBlock extends Wall {
    private String targetServer = "155.212.218.241:6568"; // адрес другого сервера

    public PortalBlock(String name) {
        super(name);
        consumesTap = true;
        configurable = true;
        update = false;
    }

    public class PortalBuild extends WallBuild {
        @Override
        public boolean configTapped() {
            Player player = getPlayerTapped();
            if (player != null) {
                // Отправляем игрока на другой сервер
                Call.connect(player, targetServer);
                return true;
            }
            return false;
        }

        private Player getPlayerTapped() {
            return Groups.player.find(p -> p.within(this, 10f * tilesize));
        }
    }
}
//Вайбкодим, мда но пока
