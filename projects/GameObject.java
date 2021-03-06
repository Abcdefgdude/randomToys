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
    public boolean isAbove(GameObject obj) { return - y > obj.y; }

    
    /** 
     * 
     * 
     */
    public boolean isBelow(GameObject obj) { 
        System.out.println("y : " + y);
        System.out.println("obj.y - obj.getHeight() : " + (obj.y - obj.getHeight()));
        return y > (obj.y - obj.getHeight()); } 
    
    
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
     * @return Point
     */
    public Point move() {
        int newX = x + velocityX;
        int newY = y - velocityY;
        Point p = new Point(newX, newY);
        return p;
    }
}