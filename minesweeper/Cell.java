package minesweeper;

public class Cell {
    private boolean flag = false;
    private boolean mine = false;
    private boolean revealed = false;
    private int dangerNeighbors = 0;

    private Game parent;
    private int x;
    private int y;

    public Cell(int ecks, int why, Game p) {
        x = ecks;
        y = why;
        parent = p;
    }
    
    public void reveal() { 
        revealed = true; 
        if (!hasMine()) {
        findDanger();
        if (dangerNeighbors == 0)
            for (Cell c : parent.getNeighbors(this))
                if (c != this && !c.isRevealed())    
                    c.reveal();
        }
    }    

    public boolean isRevealed() { return revealed; } 
    
    public boolean isFlagged() { return flag; }

    public void toggleFlag() { flag = !flag; }

    public boolean hasMine() { return mine; } 

    public boolean placeMine() { if (mine) return false; else mine = true; return mine; } 
    
    public int getX() { return x; }

    public int getY() { return y; }

    public int getDanger() { return dangerNeighbors; }
    
    public void findDanger() {
        int count = 0;
        for (Cell c : parent.getNeighbors(this))
            if (c.hasMine())
                count++;
        dangerNeighbors = count;
    }
    
    public String toString() {
        String out = "";
        out += "x : " + x;
        out += ", y : " + y;
        out += ", danger : " + getDanger();
        out += ", flagged? : " + isFlagged();
        return out;  
        
    }   

}