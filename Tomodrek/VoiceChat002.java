package Tomodrek;

import arc.util.io.*;
import mindustry.net.Net;
import mindustry.net.Packet;

public class VoiceChat002 extends Packet {
    public byte[] audioData;
    public int senderId;
    private static boolean registered = false;

    public VoiceChat002() {}

    public VoiceChat002(byte[] data, int senderId) {
        this.audioData = data;
        this.senderId = senderId;
    }

    @Override
    public void write(Writes write) {
        write.i(senderId);
        if (audioData != null) {
            write.i(audioData.length);
            write.b(audioData);
        } else {
            write.i(0);
        }
    }

    @Override
    public void read(Reads read) {
        senderId = read.i();
        int len = read.i();
        if (len > 0 && len < 20000) { // Лимит для безопасности (20KB)
            audioData = new byte[len];
            read.b(audioData);
        } else {
            audioData = new byte[0];
        }
    }

    // Регистрация пакета для сетевого движка
    public static void register() {
        if (!registered) {
            Net.registerPacket(VoiceChat002::new);
            registered = true;
        }
    }
}
