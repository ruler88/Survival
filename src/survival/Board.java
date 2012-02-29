/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener
{
    static int boardX, boardY;     //map position
    int dx, dy;
    static Timer time;
    static Image map;
    
    double timeCounter = 0;         
    double timeSec = 0;             //keeps track of seconds of the game
    
    static Player p1;
    ArrayList enemyList = new ArrayList();
    
    Set pressedDir = new HashSet();
    
    public Board()
    {
        boardX=0;
        boardY=0;
        
        ImageIcon i = new ImageIcon(this.getClass().getResource("images/map.jpg"));  //get image
        map = i.getImage();
        
        setFocusable(true);
        addKeyListener(new ActionListenerClass());
        
        time = new Timer(10, this);   //updates image every 5 ms
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
        spawn();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        timeCounter+=1;
        timeSec = timeCounter / (1000/time.getDelay());
        
        moveMap();
        for(int i=0; i<enemyList.size(); i++)
        {
            ((Enemy) enemyList.get(i)).moveEnemy();
        }
        
        spawn();
        repaint();
        
        System.out.println("up: "+ p1.up + " down: "+ p1.down + " left: " + p1.left + " right: " + p1.right);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(map, -boardX, -boardY, null, null);
        
        //draw character
        g2d.drawImage(p1.player_img, p1.x, p1.y, null);
        
        Block tempBlock;
        for(int i=0; i<MapBlock.allBlocks.size(); i++)
        {
            tempBlock = (Block) MapBlock.allBlocks.get(i);
            g2d.drawImage(tempBlock.getImage(), tempBlock.getX(), tempBlock.getY(), null, null);
        }
        
        Enemy tempEnemy;
        for(int i=0; i<enemyList.size(); i++)
        {
            tempEnemy = (Enemy) enemyList.get(i);
            g2d.drawImage(tempEnemy.getImage(), tempEnemy.getX(), tempEnemy.getY(), null, null);
        }
        
        Shot tempShot;
        for(int i=0; i<p1.shots.size(); i++)
        {
            tempShot = (Shot) p1.shots.get(i);
            
            g2d.drawImage(tempShot.getImage(), tempShot.af, this);
            
        }
    
        
        g2d.drawString("Time: "+ timeSec, 10,10);
    }
    
    
    public void spawn()
    {
        if(timeSec % 5 == 0)
        {
            //spawn every 5 sec
            double rand = Math.random()*10;
            
            //4 spawn points, 70% spawn
            if(rand>7)
                enemyList.add(new Ghost(0,0));
                
            rand = Math.random()*10;
            if(rand>7)
                enemyList.add(new Ghost(1000,1000));
            
            rand = Math.random()*10;
            if(rand>7)
                enemyList.add(new Ghost(2000,2000));
            
            rand = Math.random()*10;
            if(rand>7)
                enemyList.add(new Ghost(3000,10));
                
        
        //enemyList.add(new Ghost(500,500));
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
            
            if (key == KeyEvent.VK_X)
            {
                p1.shoot();
                return;
            }
            
            //direction only
            pressedDir.add(key);
            
            p1.up=false;p1.down=false;
            p1.left=false;p1.right=false;
            
            Iterator ite = pressedDir.iterator();
            while(ite.hasNext())
                keyPressedHelper( (Integer) ite.next());
                      
            
        }
        
        
        public void keyPressedHelper(int key)
        {
            if (key == KeyEvent.VK_UP)
            {  
                p1.up = true;
                dy = -p1.move_speed; 
            }
            if (key == KeyEvent.VK_DOWN)
            { 
                p1.down = true;
                dy = p1.move_speed; 
            }
            if (key == KeyEvent.VK_LEFT)
            {
                dx = -p1.move_speed;
                p1.left = true;
            }
            if (key == KeyEvent.VK_RIGHT)
            {
                dx = p1.move_speed; 
                p1.right = true;
            }
            
        }
        
        public void keyReleased(KeyEvent e)
        {
            int key = e.getKeyCode();
            pressedDir.remove(key);
            
            if (key == KeyEvent.VK_LEFT)
            {  dx=0;  
                if(p1.right || p1.up || p1.down)
                    p1.left = false;
            }
            if (key == KeyEvent.VK_RIGHT)
            {  dx=0;  
                if(p1.left || p1.up || p1.down)
                    p1.right = false;
            }
            if (key == KeyEvent.VK_UP)
            {  dy=0;  
                if(p1.left || p1.right || p1.down)
                    p1.up = false;
            }
            if (key == KeyEvent.VK_DOWN)
            {  
                dy=0;  
                if(p1.left || p1.right || p1.up)
                    p1.down = false;
            }
        }
        
    }
    
}
