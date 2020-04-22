package brickbreaker;

import java.awt.*;

public class Paddle extends GameObject{
    public int RIGHT_KEY;
    public int LEFT_KEY; 
    public int KEY_PRESSED;
    public final int MAX_SPEED = 5;
    // Determines how long it takes to stop moving (higher -> more slip)
    private int SLIPPERY_CONSTANT = 4;
    private int FRICTION;
    // How many frames before slipping takes effect
    private int INERTIA_COUNTER = 0;
    private int INERTIA_LIMIT = 24;

    private int decelerateCounter = 0;
    private boolean decelerating = false;
    
    public Paddle(int width, int height) {
        super(width, height);
    }
    
    /** 
     * @param obj
     */
    @Override
    
    public void collision(GameObject obj) {
        if ((obj instanceof Ball)); 
        else velocityY = 0;
    }
    
    
    /** 
     * @param right
     * @param left
     */
    public void setKeys(int right, int left) {
        RIGHT_KEY = right;
        LEFT_KEY = left;
    }
    
    public void accelerateRight() {
        velocityX = MAX_SPEED;
        KEY_PRESSED = RIGHT_KEY;
        decelerating = false;
    }
    
    public void accelerateLeft() {
        velocityX = -MAX_SPEED;
        KEY_PRESSED = LEFT_KEY;
        decelerating = false;
    }
    
    public void decelerate() {
        if (INERTIA_COUNTER >= INERTIA_LIMIT) {
            decelerating = true;
            FRICTION = 1;
        }
        else if (INERTIA_COUNTER >= INERTIA_LIMIT / 4) {
            decelerating = true;
            FRICTION = 3;
        }
        else if (INERTIA_COUNTER >= INERTIA_LIMIT / 2) {
            decelerating = true;
            FRICTION = 2;
        }
        else velocityX = 0;
        INERTIA_COUNTER = 0;
        KEY_PRESSED = 0;
    }   
    
    
    /** 
     * @return Point
     */
    public Point move() {
        int newX = x + velocityX;
        if (newX + getWidth() >= Game.GAME_WIDTH || newX <= 0)
            newX = x;
        Point p = new Point(newX, y);
        if (decelerating && velocityX != 0) {
            if (velocityX >= 0 - FRICTION && velocityX <= 0 + FRICTION)
                velocityX = 0;
            if (decelerateCounter == SLIPPERY_CONSTANT) {
                velocityX += velocityX < 0 ? FRICTION : -FRICTION;
                decelerateCounter = 0;
            }
            else decelerateCounter++;
        }
        // else System.out.println("INERTIA_COUNTER : " + INERTIA_COUNTER++);
        else INERTIA_COUNTER++;
        return p;
    }
}