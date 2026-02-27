package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.*;
import mindustry.net.NetConnection;

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
        public boolean configTapped(Player tapper) {  // Используем tapper напрямую
            if (tapper == null || !tapper.isLocal()) return false;

            tapper.sendMessage("Успешно");
            NetConnection con = tapper.con();
            if (con != null && con.isConnected()) {
                // Отправляем пакет клиенту
                Call.sendPacket(con, new RedirectPacket(targetIp, targetPort));
                tapper.sendMessage("Соединение есть");
                return true;
            } else {
                tapper.sendMessage("[scarlet]Портал работает только на серверах!");
                return true;
            }
        }
    }
}
package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.*;
import mindustry.net.NetConnection;

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
        public boolean configTapped(Player tapper) {  // Используем tapper напрямую
            if (tapper == null || !tapper.isLocal()) return false;

            tapper.sendMessage("Успешно");
            NetConnection con = tapper.con();
            if (con != null && con.isConnected()) {
                // Отправляем пакет клиенту
                Call.sendPacket(con, new RedirectPacket(targetIp, targetPort));
                tapper.sendMessage("Соединение есть");
                return true;
            } else {
                tapper.sendMessage("[scarlet]Портал работает только на серверах!");
                return true;
            }
        }
    }
}
