package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.*;
import mindustry.net.Packets;
import mindustry.net.NetConnection;
import mindustry.Vars;
import mindustry.net.Packets.KickReason;

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
                NetConnection connection = player.con;
                if (connection != null) {
                    Call.connection(connect, "155.212.218.241", 6567);
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