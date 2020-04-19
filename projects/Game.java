package projects;

import javax.swing.*;
import java.util.*;
import java.awt.*;



@SuppressWarnings("serial")
public class Game extends JPanel {
    private ArrayList<GameObject> objects;
    private Scoreboard sb;
    Pong parent;
    public Game(Scoreboard s, Pong p) {
        objects = new ArrayList<GameObject>();
        parent = p;
        sb = s;

    }
    
    public void add(GameObject obj) {
        objects.add(0, obj);
    }

    public void moveStuff() {
        for (GameObject obj : objects) {
            Point p = obj.move();
            obj.setLocation(p);
        }
        collisionCheck();
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (GameObject obj : objects) {
            g.setColor(obj.getColor());
            g.fillRect(obj.x, obj.y, obj.getWidth(), obj.getHeight());
        }
    }

    public void collisionCheck() {
        for (int i = 0; i < objects.size() - 1; i++) {
            GameObject current = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++)
                if (current.getBounds().intersects(objects.get(j).getBounds())) 
                    current.collision(objects.get(j));
            if (current instanceof Ball) 
                if (current.x < 0)
                    score(1);
                else if (current.x > Pong.GAME_WIDTH)
                    score(0);
        }
    }
    
    public void score(int player) {
        sb.updateScore(player, 1);
        objects.removeIf(n -> n instanceof Ball);
        parent.initBall();
        System.out.println("this : " + this);
    }
    
    public String toString() {
        String out = "";
        out += objects;
        return out;
    }
}