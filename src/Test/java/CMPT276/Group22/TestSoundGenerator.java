package CMPT276.Group22;

import javax.sound.sampled.*;
import java.io.*;

public class TestSoundGenerator {
    public static void main(String[] args) {
        createTestSound();
    }

    public static void createTestSound() {
        try {
            // Ensure directory exists
            new File("src/test/resources/sounds").mkdirs();
            
            // Create a very simple tone
            byte[] data = new byte[441];  // Tiny 0.01 second sound
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte)(Math.sin(i / 10.0) * 50);
            }

            // Set up audio format
            AudioFormat format = new AudioFormat(44100, 8, 1, true, true);
            
            // Create and write the sound file
            String path = "src/test/resources/sounds/test.wav";
            AudioSystem.write(
                new AudioInputStream(
                    new ByteArrayInputStream(data),
                    format,
                    data.length
                ),
                AudioFileFormat.Type.WAVE,
                new File(path)
            );
            
            System.out.println("Created test sound at: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}