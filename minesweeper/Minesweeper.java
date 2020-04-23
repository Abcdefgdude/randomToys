package minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Minesweeper {
    Game game;
    public Minesweeper() {
        startGame();
    }
    
    public void startGame() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame();
        JPanel main = new JPanel();
        frame.add(main);
        
        Game game = new Game();

        main.setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT + 100));
        game.setBackground(Color.BLACK);
        main.add(game);

        main.requestFocusInWindow();
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });

        JPanel control = new JPanel();
        control.setPreferredSize(new Dimension(250, 100));
        control.setLayout(new GridLayout());
        JButton button1 = new JButton("RESET!");
        button1.setFocusable(false);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createAndShowGUI();
            }
        });
        control.add(button1);
        control.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        main.add(control);
        frame.setTitle("Mine mine mine!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}