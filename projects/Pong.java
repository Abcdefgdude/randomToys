package projects;

import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pong {
    private Timer timer;
    private final int WIN_SCORE = 5;
    public static int GAME_WIDTH = 1000;
    public static int GAME_HEIGHT = 800;
    private final int GAME_SPEED = 8;
    
    private final int BALL_WIDTH = 25;
    private final int BALL_START_SPEED = 2;
    
    private final int PADDLE_WIDTH = 20;
    private final int PADDLE_HEIGHT = 200;

    private Scoreboard sb;
    private Ball ball;
    private ArrayList<Paddle> paddles;
    
    Game game;
    public Pong() {
        startGame();
    }
    
    public void startGame() {
        createAndShowGUI();
        initBall();
        initPaddles();
        startTimer();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame();
        JPanel main = new JPanel();
        frame.add(main);
        
        sb = new Scoreboard();
        main.add(sb, BorderLayout.NORTH);
        
        game = new Game(sb, this);
        game.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        game.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        game.setBackground(Color.BLACK);
        main.add(game, BorderLayout.CENTER);
        
        main.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                for (Paddle p : paddles) 
                    if (e.getKeyCode() == p.UP_KEY)
                        p.accelerateUp();
                    else if (e.getKeyCode() == p.DOWN_KEY)
                        p.accelerateDown();
            }
            public void keyReleased(KeyEvent e) {
                for (Paddle p : paddles) 
                    if (e.getKeyCode() == p.UP_KEY)
                        p.decelerate();
                    else if (e.getKeyCode() == p.DOWN_KEY)
                        p.decelerate();
            }
        });

        main.requestFocusInWindow();
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                main.requestFocusInWindow();
            }
        });
        frame.setTitle("Pong!");
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
    
    public void initBall() {
        ball = new Ball(BALL_WIDTH, BALL_WIDTH);
        ball.setLocation((GAME_WIDTH - BALL_WIDTH) / 2 , (GAME_HEIGHT - BALL_WIDTH) / 2);
        // ball.velocityX = BALL_START_SPEED;
        ball.velocityX = Math.random() > 0.5 ? -BALL_START_SPEED : BALL_START_SPEED;
        ball.velocityY = (int)(Math.random() * 3);
        game.add(ball);
    }
    
    public void initPaddles() {
        paddles = new ArrayList<Paddle>();
        Paddle p1 = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle p2 = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        p1.setLocation(1 , (GAME_HEIGHT - PADDLE_HEIGHT) / 2);
        p2.setLocation(GAME_WIDTH - PADDLE_WIDTH , (GAME_HEIGHT - PADDLE_HEIGHT) / 2);
        p1.setKeys(KeyEvent.VK_W, KeyEvent.VK_S);
        p2.setKeys(KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        game.add(p1);
        game.add(p2);
        paddles.add(p1);
        paddles.add(p2);

        Paddle b1 = new Paddle(GAME_WIDTH, 2);
        Paddle b2 = new Paddle(GAME_WIDTH, 2);
        b1.setLocation(0 , 0);
        b2.setLocation(0 , GAME_HEIGHT - 2);
        game.add(b1);
        game.add(b2);

    }

    
    /** 
     * @return boolean
     */
    public boolean winCheck() {
        return sb.getHighestScore() >= WIN_SCORE;
    }

}