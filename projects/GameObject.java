package projects;

abstract class GameObject {
    int x;
    int y;
    
    int velocityX;
    int velocityY;
    
    abstract void move(); 
}