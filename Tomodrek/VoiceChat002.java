package Tomodrek;


import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.net.Packet;

public class VoiceChat002 extends Packet {
    public byte[] audioData;
    public int senderId;
    
    
    @Override
    public void write(Writes write) {
        write.arrb(audioData);
        write.i(senderId);
        
    }

    @Override
    public void read(Reads read) {
        audioData = read.arrb();
        senderId = read.i();
       
    }
}
  
