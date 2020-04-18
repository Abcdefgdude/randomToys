package projects;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Scoreboard extends JPanel {
    JLabel score1, score2;
    Font font = new Font("Corbel", Font.PLAIN, 32);

    public Scoreboard() {
        setPreferredSize(new Dimension(200, 50));
        
        JPanel content = new JPanel();
        this.add(content);
        
        score1 = new JLabel("0");
        score1.setFont(font);
        score2 = new JLabel("0");
        score2.setFont(font);
        content.add(score1, BorderLayout.WEST);
        content.add(score2, BorderLayout.EAST);

    }
    
    /** 
     * @param player Which score to update, 0 for player 1, 1 for player 2 
     * @param addScore how many points to add to score 
     */
    public void updateScore(int player, int addScore) {
        if (player == 0)
            score1.setText("" + (Integer.valueOf(score1.getText()) + addScore));
        else if (player == 1)
            score2.setText("" + (Integer.valueOf(score2.getText()) + addScore));
        else System.out.println("Player must be 0 or 1");
    }
}