package brickbreaker;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrickBreaker {
    private Timer timer;
    public final int GAME_SPEED = 8;
    Game game;
    public BrickBreaker() {
        startGame();
    }
    
    public void startGame() {
        createAndShowGUI();
        startTimer();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame();
        JPanel main = new JPanel();
        frame.add(main);
        main.setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT + 250));
        game = new Game(8, 14, 120);
        game.setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT));
        game.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        game.setBackground(Color.BLACK);
        // main.add(game, BorderLayout.NORTH);
        main.add(game);

        main.requestFocusInWindow();
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });

        JPanel control = new JPanel();
        control.setPreferredSize(new Dimension(Game.GAME_WIDTH, 250));
        control.setLayout(new GridLayout());
        JButton button1 = new JButton("RESET!");
        button1.setFocusable(false);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.initStuff();
            }
        });
        
        JButton button2 = new JButton("ADD BALL!");
        button2.setFocusable(false);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.initBall();
            }
        });
        
        control.add(button1);
        control.add(button2);
        control.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        // main.add(control, BorderLayout.SOUTH);
        main.add(control);
        frame.setTitle("Brick Breakin'!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                game.moveStuff();
            }};
        timer.scheduleAtFixedRate(task, 0, GAME_SPEED);        
    }
}