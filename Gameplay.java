package base;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gameplay extends JPanel implements ActionListener,KeyListener
{
    private boolean gameInProgress = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int panelX = 300;

    private int ballPosX = 120;
    private int ballPosY = 350;

    private int ballXdir = -1;
    private int ballYdir = -2;

    private BrickGenerator newScreen;

    public Gameplay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();

        newScreen = new BrickGenerator(3,7);
    }

    public void paint(Graphics g)
    {
        //Setting bavkground
        g.setColor(Color.black);
        g.fillRect(0,0,710,620);

        //Setting bricks
        newScreen.draw((Graphics2D)g);

        //Borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,620);
        g.fillRect(0,0,710,3);
        g.fillRect(690,0,3,620);

        //text to display score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+ score, 590, 30);

        //panel
        g.setColor(Color.green);
        g.fillRect(panelX,550,100,8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        //when all bricks are smashed
        if(totalBricks<=0)
        {
            gameInProgress=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Final Score: "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);

        }

        //when ball is out of bounds
        if(ballPosY>=620)
        {
            gameInProgress=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("arial",Font.BOLD, 30));
            g.drawString("GAME OVER", 190, 300);
            g.drawString("SCORE  " +score, 190, 340);


            g.setFont(new Font("arial", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 400);
        }

        g.dispose();
    }
        
    public void keyTyped(KeyEvent e) {   
        }

    public void keyPressed(KeyEvent e) {
        
        //to control the panel with the arrow keys
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            if(panelX>=600)
                panelX=600;
            else
                moveRight();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {
            if(panelX<10)
                panelX=10;
            else
                moveLeft();
        }

        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!gameInProgress)
            {
                gameInProgress=true;
                ballPosX=120;
                ballPosY=350;
                panelX=310;
                score=0;
                totalBricks=21;
                newScreen = new BrickGenerator(3, 7);
                repaint();
            }
        }
        
    }

    void moveRight()
    {
        gameInProgress = true;
        panelX+=20;
    }
    void moveLeft()
    {
        gameInProgress = true;
        panelX-=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(gameInProgress==true)
        {
            if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(panelX,550,100,8)))
            {
                ballYdir =-ballYdir;
            }

           A: for(int i=0; i<newScreen.map.length; i++)
            {
                for(int j =0; j<newScreen.map[0].length; j++)
                {
                    if(newScreen.map[i][j]==1)
                    {
                        int brickX = j*newScreen.brickWidth + 80;
                        int brickY = i*newScreen.brickHeight + 50;
                        int brickWidth = newScreen.brickWidth;
                        int brickHeight = newScreen.brickHeight;
                        
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        Rectangle brickRect = new Rectangle(brickX,brickY,brickWidth,brickHeight);

                        if(brickRect.intersects(ballRect))
                        {
                            newScreen.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballPosX+19 <= brickRect.x || ballPosX+1 >= brickRect.x + brickRect.width)
                            {
                                ballXdir= -ballXdir;   
                            }
                            else
                            if(ballPosY+19 <= brickRect.y || ballPosY >= brickRect.y + brickRect.height)
                                ballYdir= -ballYdir;

                        }
                        break A;
                    }
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if(ballPosX<=0)
            {
                ballXdir= -ballXdir;
            }
            if(ballPosY<=0)
            {
                ballYdir= -ballYdir;
            }
            if(ballPosX >= 670)
            {
                ballXdir= -ballXdir;
            }

        }
        repaint();
        
    }
    
}

