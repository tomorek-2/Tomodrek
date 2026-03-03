package Tomodrek;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Con;
import mindustry.net.Packet;

public class RedirectPacket extends Packet {
    private String ip;
    private int port;

    // Обязательный пустой конструктор для десериализации
    public RedirectPacket() {}

    public RedirectPacket(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void write(Writes buffer) {
        buffer.str(ip);
        buffer.i(port);
    }

    @Override
    public void read(Reads buffer) {
        ip = buffer.str();
        port = buffer.i();
    }

    @Override
    public void handled() {
        // Этот метод вызывается, когда пакет получен на клиенте
        // Выполняем редирект через Vars.netClient.connect()
        if (Vars.netClient != null) {
            Vars.netClient.connect(ip, port);
        }
    }
}