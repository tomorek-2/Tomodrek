package Tomodrek;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;

public class VoiceChat001 {
    private TargetDataLine line;

public AudioFormat format;
    public boolean recording = false;
    
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
          
            
            return false;
        }
    }
}



