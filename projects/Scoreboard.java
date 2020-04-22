package projects;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Scoreboard extends JPanel {
    JLabel score1, score2;
    Font font = new Font("Corbel", Font.PLAIN, 64);

    public Scoreboard() {
        setPreferredSize(new Dimension(300, 100));
        
        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.add(content);
        
        score1 = new JLabel("0");
        score1.setFont(font);
        score1.setAlignmentX(LEFT_ALIGNMENT);
        
        score2 = new JLabel("0");
        score2.setFont(font);
        score1.setAlignmentX(RIGHT_ALIGNMENT);
        
        JPanel filler = new JPanel();
        filler.setPreferredSize(new Dimension(100, 5));
        content.add(score1, BorderLayout.WEST);
        content.add(filler, BorderLayout.CENTER);
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

    
    /** 
     * @return int
     */
    public int getHighestScore() {
        return Math.max(Integer.valueOf( score1.getText() ), Integer.valueOf( score2.getText() ) );
    }
}