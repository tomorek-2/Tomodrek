package Tomodrek;


import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.io.ByteBufferInput;
import arc.util.io.ByteBufferOutput;
import arc.util.Buffers;
import mindustry.net.Packet;
import mindustry.net.*;

public class VoiceChat002 extends Packet {
    public byte[] audioData;
    public int senderId;
    public void VoicePacket() {
    }
        public void VoicePacket(byte[] data) {
            this.audioData = data;
        }
    @Override
    public void write(Writes write) {
         write.i(audioData.length);
        write.b(audioData);
        
        write.i(senderId);
        
    }

    @Override
    public void read(Reads read) {
        int length = read.i();
        audioData = read.b(length); 
        senderId = read.i();
       
    }

 public static void register() {
int packetId = 100;
Net.registerPacket(packetId, VoicePacket::new);
} 
} 
