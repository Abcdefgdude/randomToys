package projects;

import java.awt.*;

public class GameObject {
    public int x, y, velocityX, velocityY;
    private int width, height;
    private Color color;
    public GameObject(int w, int h) {
        width = w;
        height = h;    
        color = Color.WHITE;
    }
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Should be overwritten by any child of GameObject
     * @param obj
     */
    public void collision(GameObject obj) { System.out.println("bonk!"); }
    
    public boolean isAbove(GameObject obj) { return -y > obj.y; }

    public boolean isBelow(GameObject obj) { return y < obj.y - obj.getHeight(); } 
    
    public void setLocation(Point p) { setLocation(p.x, p.y); }

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public Color getColor() { return color; }

    public Point move() {
        int newX = x + velocityX;
        int newY = y - velocityY;
        Point p = new Point(newX, newY);
        return p;
    }
}