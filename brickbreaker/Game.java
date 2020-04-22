package brickbreaker;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Game extends JPanel {

    // Game Settings
    public static int GAME_WIDTH = 1280; 
    public static int GAME_HEIGHT = 720;
    
    public final int GAP = 5;
    
    public int ROW_WIDTH;
    public int ROW_COUNT;
    public final int BLOCK_WIDTH;
    public final int BLOCK_HEIGHT = 25; 
    public final Color[] ROW_COLORS = {
        new Color(186,85,211), new Color(0,0,205), new Color(30,144,255), new Color(60,179,113), 
        new Color(255,255,0), new Color(255,165,0), new Color(255,69,0), new Color(128,0,0)};
    
    public final int BALL_WIDTH = 30;

    public int PADDLE_WIDTH;
    public final int PADDLE_LEFT = KeyEvent.VK_A;
    public final int PADDLE_RIGHT = KeyEvent.VK_D;
    
    ArrayList<GameObject> objects;
    ArrayList<Brick> bricks;     
    private ArrayList<Ball> balls;
    private Paddle paddle;
    
    // default settings
    public Game() {
        this(5, 14, 120);
    }
    /** Custom difficulty constructor for brick breaker game.
     *  
     * @param numRows The number of rows. Must be within 1 and 8.
     * @param bricksPerRow The number of bricks in each row 
     * @param pw The width of the paddle, in pixels
     */
    public Game(int numRows, int bricksPerRow, int pw) {
        ROW_WIDTH = bricksPerRow;
        ROW_COUNT = numRows;
        PADDLE_WIDTH = pw;
        BLOCK_WIDTH = (GAME_WIDTH / ROW_WIDTH) - GAP;
        
        setFocusable(true);
        initStuff();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == paddle.RIGHT_KEY)
                    paddle.accelerateRight();
                else if (e.getKeyCode() == paddle.LEFT_KEY)
                    paddle.accelerateLeft();
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == paddle.RIGHT_KEY && paddle.KEY_PRESSED == paddle.RIGHT_KEY)
                    paddle.decelerate();
                else if (e.getKeyCode() == paddle.LEFT_KEY && paddle.KEY_PRESSED == paddle.LEFT_KEY)
                    paddle.decelerate();
            }
        });
    }
    
    public void initStuff() {
        bricks = new ArrayList<Brick>();
        objects = new ArrayList<GameObject>();
        balls = new ArrayList<Ball>();
        initBricks();
        initBouncys();
        initBall();
    }
    
    public void initBricks() {
        for (int i = 0; i < ROW_COUNT; i++) 
            for (int j = 0; j < ROW_WIDTH; j++) {
                Brick current = new Brick(BLOCK_WIDTH, BLOCK_HEIGHT, ROW_COLORS[i]);
                current.setLocation((BLOCK_WIDTH + GAP) * j, 75 + (BLOCK_HEIGHT + GAP) * i);
                bricks.add(current);
            }            
    }
    
    public void initBouncys() {

        paddle = new Paddle(PADDLE_WIDTH, 20);
        paddle.setLocation((GAME_WIDTH - PADDLE_WIDTH) / 2, GAME_HEIGHT - 100 + BALL_WIDTH);
        paddle.setKeys(PADDLE_RIGHT, PADDLE_LEFT);
        objects.add(paddle);

        GameObject leftBarrier = new GameObject(1, GAME_HEIGHT - 1);
        leftBarrier.setLocation(0, 1);
        GameObject rightBarrier = new GameObject(1, GAME_HEIGHT - 1);
        rightBarrier.setLocation(GAME_WIDTH - 1, 1);
        GameObject topBarrier = new GameObject(GAME_WIDTH, 1);
        topBarrier.setLocation(1, 0);
        objects.add(leftBarrier);
        objects.add(rightBarrier);
        objects.add(topBarrier);
    }
    
    public void initBall() {
        Ball ball = new Ball(BALL_WIDTH, BALL_WIDTH);
        ball.setLocation((GAME_WIDTH - BALL_WIDTH) / 2, GAME_HEIGHT - 100);
        ball.velocityX = (int) (Math.random() * 4) + 1;
        ball.velocityX = Math.random() > 0.5 ? -ball.velocityX : ball.velocityX;
        ball.velocityY = (int) (Math.random() * 2) + 2;
        objects.add(ball);
        balls.add(ball);
    }
    /** Called periodically by master timer to tick game forward */
    public void moveStuff() {
        checkCollisions();
        for (GameObject obj : objects) 
            if (obj.isMoving())
                obj.setLocation(obj.move());

        repaint();
    }

    public void gameOver() {

    }
    
    public void checkCollisions() {
        ArrayList<Brick> toRemove = new ArrayList<Brick>();
        for (Ball b : balls) {
            Rectangle collision = b.getCollisionBox();
            if (collision.y <= bricks.get(bricks.size()-1).y + bricks.get(bricks.size() - 1).getHeight() && collision.y + collision.height >= bricks.get(0).y) {
                for (Brick brick : bricks) 
                    if (collision.intersects(brick.getBounds())) {
                        b.collision(brick);
                        toRemove.add(brick);
                        break;
                    }
            }
            for (GameObject obj : objects) 
                if (collision.intersects(obj.getBounds()) && obj != b)
                    b.collision(obj);
        }
        bricks.removeAll(toRemove);
    }

    
    /** Override of default paint method. Paints all game objects
     * @param g Graphics g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.fillRect(0, bricks.get(0).y, GAME_WIDTH, bricks.get(bricks.size()-1).y - bricks.get(0).y + bricks.get(bricks.size()-1).getHeight());
        Rectangle rect = new Rectangle();
        for (Brick b : bricks) {
            g.setColor(b.getColor());
            g.fillRect(b.x, b.y, b.getWidth(), b.getHeight());
        }
        for (GameObject obj : objects) {
            g.setColor(obj.getColor());
            g.fillRect(obj.x, obj.y, obj.getWidth(), obj.getHeight());
        }
        for (Ball ball : balls) {
            g.setColor(ball.getColor());
            g.fillRect(ball.x, ball.y, ball.getWidth(), ball.getHeight());
/*             g.setColor(Color.RED);
            Rectangle r = ball.getCollisionBox();
            g.fillRect(r.x, r.y, r.width, r.height); */
        }
    }
}
