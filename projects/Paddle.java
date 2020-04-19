package projects;


public class Paddle extends GameObject{
    public int UP_KEY;
    public int DOWN_KEY; 
    public final int MAX_SPEED = 7;
    public Paddle(int width, int height) {
        super(width, height);
    }
    @Override
    public void collision(GameObject obj) {
        if ((obj instanceof Ball)); 
        else velocityY = 0;
    }
    public void setKeys(int up, int down) {
        UP_KEY = up;
        DOWN_KEY = down;
    }
    public void accelerateUp() {
            velocityY = MAX_SPEED;
    }
    
    public void accelerateDown() {
        velocityY = -MAX_SPEED;
    }
    
    public void decelerate() {
        velocityY = 0;
     }   
}