/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener
{
    static int boardX, boardY;     //map position
    int dx, dy;
    static Timer time;
    static Image map;
    int move_speed = 10;
    
    Player p1;
    
    
    public Board()
    {
        boardX=0;
        boardY=0;
        
        ImageIcon i = new ImageIcon(this.getClass().getResource("images/map.jpg"));  //get image
        map = i.getImage();
        
        setFocusable(true);
        addKeyListener(new ActionListenerClass());
        
        time = new Timer(5, this);   //updates image every 5 ms
        time.start();
        
        if(Survival.player_name == "garrett")
        {p1=new Garrett();}
        else if(Survival.player_name == "stephen")
        {p1=new Stephen();}
        else if(Survival.player_name == "christina")
        {p1=new Christina();}
        else if(Survival.player_name == "kai")
        {p1=new Kai();}
        
        
        new MapBlock();         //initializes map blocks
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        moveMap();
        
        repaint();
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        //System.out.println("boardx: " + boardX + " boardy: " + boardY);
        
        g2d.drawImage(map, -boardX, -boardY, null, null);
        
        //draw character
        g2d.drawImage(p1.player_img, p1.x, p1.y, null);
        
        
        Block tempBlock;
        for(int i=0; i<MapBlock.allBlocks.size(); i++)
        {
            tempBlock = (Block) MapBlock.allBlocks.get(i);
            g2d.drawImage(tempBlock.getImage(), tempBlock.getX(), tempBlock.getY(), null, null);
        }
    }
    
    
    public void moveMap()
    {
        //x-movement
        if(boardX+dx < 0)
        {    
            p1.moveXSide(dx-boardX);
            boardX = 0;
        }
        else if(boardX+dx>(map.getWidth(null) - 1280))
        {   
            p1.moveXSide(dx- ((map.getWidth(null) - 1280)-boardX));
            boardX = (map.getWidth(null) - 1280);
        }
        else
        {   
            //checks for edge recovery
            if(!p1.moveXNorm(dx))
                moveMapHelper(dx, 0);   
        }
        
        
        //y-movement        
        if(boardY+dy < 0)
        {    
            p1.moveYSide(dy-boardY);
            boardY = 0;
        }
        else if(boardY+dy>(map.getHeight(null)-860))
        {    
            p1.moveYSide(dy - ((map.getHeight(null)-860)-boardY));
            boardY = map.getHeight(null)-860;
        }
        else
        {   
            if(!p1.moveYNorm(dy))
                moveMapHelper(0, dy); 
        }    
    }
    
    public void moveMapHelper(int dx, int dy)
    {
        Block tempBlock;
        for(int i=0; i<MapBlock.allBlocks.size(); i++)
        {
            tempBlock = (Block) MapBlock.allBlocks.get(i);
            if(p1.getBounds().intersects(tempBlock.getBounds(boardX+dx, boardY+dy)))
            {
                if(dx>0)
                {
                    //moving right
                    dx = tempBlock.getX() - (p1.x + p1.player_img.getWidth(null));
                }
                else if(dx<0)
                {
                    dx = p1.x - (tempBlock.getX()+tempBlock.getImage().getWidth(null));
                }
                
                if(dy>0)
                {
                    dy = tempBlock.getY() - (p1.y + p1.player_img.getHeight(null));
                }
                else if(dy<0)
                {
                    //up
                    dy = (tempBlock.getY()+tempBlock.getImage().getHeight(null))-p1.y;
                }
                
                boardX+=dx;
                boardY+=dy;
                return;
            }
        }
        
        boardX+=dx;
        boardY+=dy;
    }
    
    
    
    
    
    private class ActionListenerClass extends KeyAdapter
    {
        
        //this class watches key press
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            //key stroke for p2
            if (key == KeyEvent.VK_LEFT)
            {  dx = -move_speed;  }
            if (key == KeyEvent.VK_RIGHT)
            {  dx = move_speed; }
            if (key == KeyEvent.VK_UP)
            {  dy = -move_speed;  }
            if (key == KeyEvent.VK_DOWN)
            {  dy = move_speed;  }
        }
        
        public void keyReleased(KeyEvent e)
        {
            int key = e.getKeyCode();
            //key stroke for p2
            if (key == KeyEvent.VK_LEFT)
            {  dx=0;  }
            if (key == KeyEvent.VK_RIGHT)
            {  dx=0;  }
            if (key == KeyEvent.VK_UP)
            {  dy=0;  }
            if (key == KeyEvent.VK_DOWN)
            {  dy=0;  }
        }
        
    }
    
}
