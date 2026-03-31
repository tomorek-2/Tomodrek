package Tomodrek;


import arc.util.io.Read;
import arc.util.io.Write;
import mindustry.net.Packet;

public class VoiceChat002 extends Packet {
    public byte[] audioData;
    public int senderId;
    
    
    @Override
    public void write(Write write) {
        write.arrb(audioData);
        write.i(senderId);
        
    }

    @Override
    public void read(Read read) {
        audioData = read.arrb();
        senderId = read.i();
       
    }
}
  
