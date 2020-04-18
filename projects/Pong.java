package projects;

import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Pong {
    private Timer timer;
    private ArrayList<GameObject> objects;

    public Pong() {
        startGame();
    }
    
    public void startGame() {
        createAndShowGUI();
        objects = new ArrayList<GameObject>();
        
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame();
        JPanel main = new JPanel();
        main.add(new Scoreboard());

        frame.setTitle("Pong!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                update();
            }};
        timer.scheduleAtFixedRate(task, 0, 16);        
    }
    
    public void update() {

    }
    
    public void collisionCheck() {

    }
    
    public void winCheck() {

    }
    
}