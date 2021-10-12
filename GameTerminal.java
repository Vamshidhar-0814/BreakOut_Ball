package base;

import javax.swing.*;


public class GameTerminal {

     GameTerminal()
    {
        Gameplay gp = new Gameplay();
        JFrame tm = new JFrame("Breakout Ball");
        tm.setBounds(0,0,710,620);
        tm.setResizable(false);

        tm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tm.setVisible(true);
        tm.add(gp);

    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new GameTerminal();
            }
        });
    }
}
