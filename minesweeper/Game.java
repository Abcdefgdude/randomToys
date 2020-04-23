package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class Game extends JPanel {
    // Game Settings
    private final int CELL_WIDTH = 30; 
    private final int GAP_WIDTH = 2;
    private final int TOTAL_WIDTH = CELL_WIDTH + GAP_WIDTH;
    
    public final Color[] TEXT_COLORS = { new Color(186, 85, 211), new Color(0, 0, 205), new Color(30, 144, 255),
            new Color(60, 179, 113), new Color(255, 255, 0), new Color(255, 165, 0), new Color(255, 69, 0), new Color(128, 0, 0) };
    private final Color REVEALED_COLOR = new Color(219, 219, 219);
    private final Color HIDDEN_COLOR = Color.GRAY;
    
    private final Polygon FLAG_POLYGON = new Polygon(new int[] {GAP_WIDTH, CELL_WIDTH / 2,CELL_WIDTH - GAP_WIDTH}, 
            new int[] {CELL_WIDTH - CELL_WIDTH / 4,GAP_WIDTH, CELL_WIDTH - CELL_WIDTH / 4}, 3);
    //
    
    private Cell[][] cells;
    private ArrayList<Cell> mines;
    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;
    private int width;
    private int height;
    
    public Game() {
        this(40, 25, 200);
    }   

    public Game(int w, int h, int numMines) {
        width = w;
        height = h;
        GAME_WIDTH = TOTAL_WIDTH * width;
        GAME_HEIGHT = TOTAL_WIDTH * height;
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
        initCells();
        mines = new ArrayList<Cell>();
        sprinkleMines(numMines);
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                // System.out.println("e.getButton() : " + e.getButton());
                if (e.getButton() == MouseEvent.BUTTON1)
                    ping(cellAt(e.getX(), e.getY())); 
                else if (e.getButton() == MouseEvent.BUTTON3)
                    flag(cellAt(e.getX(), e.getY()));
                repaint();
            }});        
    } 

    public void initCells() {
        cells = new Cell[height][width];
        for (int i = 0; i < height; i++) 
            for (int j = 0; j < width; j++)
                cells[i][j] = new Cell(j, i, this);
    }
    
    public void sprinkleMines(int count) {
        int i = 0;
        while (i < count)
            if (placeMine()) i++; 
    }

    public boolean placeMine() { 
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        if (cells[y][x].placeMine()) {
            mines.add(cells[y][x]);
            return true;
        }
        else return false;
    }

    public void revealMines() {
        javax.swing.Timer timer = new javax.swing.Timer(10000 / mines.size(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mines.size() != 0) {    
                    int i = (int) Math.random() * mines.size();
                    mines.remove(i).reveal();
                    repaint();
                }
            }
        });
        timer.start();
    }

    public void ping(Cell c) {
        c.reveal();
        if (c.hasMine())
            boom();
    }

    public void flag(Cell c) { 
        c.toggleFlag();
    }
    
    public void boom() {
        removeMouseListener(getMouseListeners()[0]);
        System.out.println("heck!");
        revealMines();
    }

    public Cell cellAt(int x, int y) {
        return cells[y / TOTAL_WIDTH][x / TOTAL_WIDTH];
    }

    public ArrayList<Cell> getNeighbors(Cell c) {
        ArrayList<Cell> out = new ArrayList<Cell>();
        for (int x = c.getX() - 1; x <= c.getX() + 1; x++) 
            for (int y = c.getY() - 1; y <= c.getY() + 1; y++) 
                if (x == c.getX() && y == c.getY())
                    continue;    
                else if ((x >= 0 && x < width) && (y >= 0 && y < height))
                    out.add(cells[y][x]);
        return out;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Corbel", Font.PLAIN, (int) (CELL_WIDTH * 1.25)));
        
        for (Cell[] a : cells) 
            for (Cell c : a) {
                int x = c.getX() * TOTAL_WIDTH;
                int y = c.getY() * TOTAL_WIDTH;
                g.setColor(c.isRevealed() ? REVEALED_COLOR : HIDDEN_COLOR);
                g.fillRect(x, y, CELL_WIDTH, CELL_WIDTH);
                if (c.isRevealed()) { 
                    if (c.hasMine()) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, CELL_WIDTH, CELL_WIDTH);
                    }
                    else if (c.getDanger() > 0) {
                        g.setColor(TEXT_COLORS[c.getDanger()]);
                        g.drawString("" + c.getDanger(), x + (int) (CELL_WIDTH / 2.45), y + (int) (CELL_WIDTH / 1.4));
                    }
                }
                else if (c.isFlagged()) {
                    g.setColor(Color.RED);
                    moveTo(FLAG_POLYGON, x, y);
                    g.fillPolygon(FLAG_POLYGON);
                }
            }
    }

    public void moveTo(Polygon poly, int x, int y) {
        poly.translate(x - poly.getBounds().x, y - poly.getBounds().y);
    }
}