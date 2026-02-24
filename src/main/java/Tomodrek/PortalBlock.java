package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.net;
import mindustry.core;
import mindustry.gen.*;
import mindustry.net.Call;
import mindustry.net.NetConnection;
import mindustry.Vars;

public class PortalBlock extends Wall {
    private String targetIp = "155.212.218.241";
    private int targetPort = 6568;

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
                NetConnection connection = player.con;
                if (connection != null && connection.isConnected()) {
                    Call.connect(connection, targetIp, targetPort);
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
