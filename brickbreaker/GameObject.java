package brickbreaker;

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
    
    
    /** 
     * @param x
     * @param y
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Should be overwritten by any child of GameObject
     * @param obj
     */
    public void collision(GameObject obj) { System.out.println("bonk!"); }
    
    
    /** 
     * @param move(
     * @return boolean
     */
    public boolean isAbove(GameObject obj) { return y < obj.y; }

    
    /** 
     * @param move(
     * @return boolean
     */
    public boolean isBelow(GameObject obj) { return y >= obj.y + obj.getHeight(); } 
    
    
    /** 
     * @param move(
     * @return boolean
     */
    public boolean isMoving() { return velocityX != 0 || velocityY != 0; }

    
    /** 
     * @param move(
     */
    public void setLocation(Point p) { setLocation(p.x, p.y); }

    
    /** 
     * @param move(
     * @return Rectangle
     */
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    
    /** 
     * @param move(
     * @return int
     */
    public int getWidth() { return width; }

    
    /** 
     * @param move(
     * @return int
     */
    public int getHeight() { return height; }

    
    /** 
     * @param move(
     * @return Color
     */
    public Color getColor() { return color; }

    
    /** 
     * @param move(
     */
    public void setColor(Color c) { color = c; }

    
    /** 
     * @return Point
     */
    public Point move() {
        int newX = x + velocityX;
        int newY = y - velocityY;
        Point p = new Point(newX, newY);
        return p;
    }
}