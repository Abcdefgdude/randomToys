package projects;

import javax.sound.sampled.*;
import java.io.*;

public class Ball extends GameObject {
    private int counter = 0;
    private static Clip clip;
    public Ball (int width, int height) {
        super(width, height);
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("C:\\Users\\Ben\\Documents\\randomToys\\randomToys\\projects\\blip1.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.1f);
            // System.out.println("clip.getControls() : " + clip.getControls());
        }
        catch (Exception e ) {
            System.out.println("e : " + e);
        }
    }
    
    public void playSound() {
        clip.setFramePosition(0);
        clip.start();
    }
    
    
    /** 
     * @param obj
     */
    public void collision(GameObject obj) {   
        playSound();
        if (isAbove(obj) || isBelow(obj)) 
            velocityY = -velocityY;
        else if (obj instanceof Paddle) {
            velocityX = -velocityX;
            counter++;
            if (counter == 3) {
                velocityX += velocityX < 0 ? -1 : 1;
                counter = 0;
            }
            velocityY = velocityY + (obj.velocityY / 4);
        }
        else velocityY = -velocityY;
    }
}