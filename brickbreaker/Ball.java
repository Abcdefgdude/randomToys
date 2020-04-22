package brickbreaker;


import javax.sound.sampled.*;
import java.io.*;
import java.awt.*;

public class Ball extends GameObject {

    private static Clip blip;
    private static Clip break1;
    private static Clip break2;

    private static String blipPath = "C:\\Users\\Ben\\Documents\\randomToys\\randomToys\\brickbreaker\\Sounds\\blip1.wav";
    private static String break1Path = "C:\\Users\\Ben\\Documents\\randomToys\\randomToys\\brickbreaker\\Sounds\\break1.wav";
    private static String break2Path = "C:\\Users\\Ben\\Documents\\randomToys\\randomToys\\brickbreaker\\Sounds\\break2.wav";

    private static boolean audioLoaded = false;
    public Ball (int width, int height) {
        super(width, height);
        initAudio();
    }

    public static void initAudio() {
        if (audioLoaded == false) {
            blip = initClip(blipPath);
            break1 = initClip(break1Path);   
            break2 = initClip(break2Path);
        }
        audioLoaded = true;
    }

    public static Clip initClip(String filepath) {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double gain = .5D;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            return clip;
        }
        catch (Exception e ) { System.out.println("e : " + e); }
        return null;
    }

    public void playSound(Clip clip) {
        if (clip.isActive())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
    
    /** 
     * @param obj 
     * @return Rectangle
     */
    public Rectangle getCollisionBox() { return new Rectangle(x + velocityX , y - velocityY , getWidth(), getHeight()); }
    /** 
     * @param obj 
     */
    public void collision(GameObject obj) {   
        if (obj instanceof Brick) 
            playSound(Math.random() > 0.5 ? break1 : break2);
        if (obj instanceof Paddle) {
            if (isAbove(obj) || isBelow(obj)) 
                velocityY = -velocityY;
            else velocityX = -velocityX;
            velocityX = velocityX + (obj.velocityX / 4);
            playSound(blip);
        }
        else if (isAbove(obj) || isBelow(obj)) 
            velocityY = -velocityY;
        else velocityX = -velocityX;
        setLocation(move());
    }
}