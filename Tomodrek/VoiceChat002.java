package Tomodrek;


import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.io.ByteBufferInput;
import arc.util.io.ByteBufferOutput;
import arc.util.Buffers;
import mindustry.net.Packet;

public class VoiceChat002 extends Packet {
    public byte[] audioData;
    public int senderId;
    
    
    @Override
    public void write(Writes write) {
        write.array(audioData);
        write.i(senderId);
        
    }

    @Override
    public void read(Reads read) {
        audioData = read.array();
        senderId = read.i();
       
    }
}
  
