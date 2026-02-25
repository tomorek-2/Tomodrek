package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.*;
import mindustry.net.Net;
import mindustry.net.NetConnection;
import mindustry.Vars;

public class PortalBlock extends Wall {
    private String targetIp = "155.212.218.241";
    private int targetPort = 6567;

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
            player.sendMessage("Успешно");
                NetConnection connection = player.con;
                if (connection != null && connection.isConnected()) {
                    
Call.connect(connection, targetIp, targetPort);
                    player.sendMessage("Соединение есть");
                    return true;
                } else {
                    player.sendMessage("[scarlet]Портал работает только на серверах!");
                    return true;
                }
            }
            return false;
        }

        private Player getPlayerTapped() {
            return Groups.player.find(p -> p.within(this, 10f * Vars.tilesize));
        }
    }
}
