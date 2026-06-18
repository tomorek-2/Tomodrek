package Tomodrek;


import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.io.ByteBufferInput;
import arc.util.io.ByteBufferOutput;
import arc.util.Buffers;
import mindustry.net.Packet;
import mindustry.net.*;
public class VoiceChat002 extends mindustry.net.Packet {
    public byte[] audioData;
    public int senderId;

    // Обязательный пустой конструктор для Kryo-сериализатора
    public VoiceChat002() {
    }

    public VoiceChat002(byte[] data) {
        this.audioData = data;
    }
}
