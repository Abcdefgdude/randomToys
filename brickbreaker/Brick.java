package brickbreaker;
import java.awt.*;

public class Brick extends GameObject{
    public Brick(int w, int h, Color c) {
        super(w, h);
        setColor(c);
    }
}