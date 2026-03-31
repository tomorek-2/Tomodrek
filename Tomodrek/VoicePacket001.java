package Tomodrek;
import arc.Core;
import arc.audio.AudioInputStream;
import arc.events.EventType;
import arc.util.Log;
import arc.util.io.Bytes;
import arc.util.io.Streams;
import mindustry.Vars;
import mindustry.game.Player;
import mindustry.mod.Mod;
import mindustry.world.Block;
import modomodrek.packets.VoicePacket;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class VoicePacket001 {
    private TargetDataLine line;
  
    
    public boolean start() {
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            
            
            if (!AudioSystem.isLineSupported(info)) {
                
                return false; 
            }
            
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            recording = true;
            return true;
        } catch (Exception e) {
          
            Log.err("[Tomodrek] VoicePacket001Error: " + e.getMessage());
            return false;
        }
    }
}



