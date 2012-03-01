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
    static double timeSec = 0;             //keeps track of seconds of the game
    
    static Player p1;
    ArrayList enemyList = new ArrayList();
    static ArrayList enemyShots = new ArrayList();        //list of enemy shots
    
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
        
        moveMap();          //moves map and player
        moveEnemy();
        moveWeapon();       //ally and enemy weapons
        spawn();
        repaint();
        
        //System.out.println("up: "+ p1.up + " down: "+ p1.down + " left: " + p1.left + " right: " + p1.right);
    }
    
    public void moveEnemy()
    {
        for(int i=0; i<enemyList.size(); i++)
        {
            if(((Enemy) enemyList.get(i)).hp<=0)
            {
                enemyList.remove(i);
                p1.score+=1;
            }
            else
            {
                if(((Enemy) enemyList.get(i)).getBounds().intersects(p1.getBounds()))
                {
                    enemyList.remove(i);
                    p1.hit();
                }
                else
                    ((Enemy) enemyList.get(i)).moveEnemy();
            }
        }
    }
    
    public void moveWeapon()
    {
        Weapon tempWeapon;
        //moving the shots
        for(int i=p1.shots.size()-1; i>=0; i--)
        {
            ((Weapon) p1.shots.get(i)).move();
            tempWeapon = (Weapon) p1.shots.get(i);
            
            if(tempWeapon.realX > map.getWidth(null) || tempWeapon.realX < -tempWeapon.img.getWidth(null) ||
                    tempWeapon.realY > map.getHeight(null) || tempWeapon.realY< -tempWeapon.img.getHeight(null))
                p1.shots.remove(i); //remove if out of bounds
            
            
            for(int j=enemyList.size()-1; j>=0; j--)
            {
                //check collision with enemy
                if(tempWeapon.getBounds().intersects(((Enemy)enemyList.get(j)).getBounds()))
                {
                    ((Enemy) enemyList.get(j)).hit();
                    if(((Weapon) p1.shots.get(i)).blockable)
                    {
                        p1.shots.remove(i);
                    }
                }
            }
            
            for(int k=MapBlock.allBlocks.size()-1; k>=0; k--)
            {
                //check collison with map blocks
                if(tempWeapon.blockable && tempWeapon.getBounds().intersects(((Block) MapBlock.allBlocks.get(k)).getBounds()))
                {
                    p1.shots.remove(i);
                }
            }
            
            if(tempWeapon.end)
                p1.shots.remove(i);
        }
        
        for(int i=enemyShots.size()-1; i>=0; i--)
        {
            ((Weapon) enemyShots.get(i)).move();
            tempWeapon = (Weapon) enemyShots.get(i);
            if(tempWeapon.realX > map.getWidth(null) || tempWeapon.realX < -tempWeapon.img.getWidth(null) ||
                    tempWeapon.realY > map.getHeight(null) || tempWeapon.realY< -tempWeapon.img.getHeight(null))
                enemyShots.remove(i); //remove if out of bounds
            
            
            for(int k=MapBlock.allBlocks.size()-1; k>=0; k--)
            {
                //check collison with map blocks
                if(tempWeapon.blockable && tempWeapon.getBounds().intersects(((Block) MapBlock.allBlocks.get(k)).getBounds()))
                {
                    enemyShots.remove(i);
                }
            }
            
            if(tempWeapon.getBounds().intersects(p1.getBounds()))
            {
                //check collision with player
                p1.hit();
                enemyShots.remove(i);
            }
            
            
        }
        
        
        
        
        
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
        
        Weapon tempWeapon;  //ally weapon
        for(int i=0; i<p1.shots.size(); i++)
        {
            tempWeapon = (Weapon) p1.shots.get(i);
            g2d.drawImage(tempWeapon.getImage(), tempWeapon.af, null);
        }
        
        for(int i=enemyShots.size()-1; i>=0; i--)
        {
            //enemy weapon
            tempWeapon = (Weapon) enemyShots.get(i);
            g2d.drawImage(tempWeapon.getImage(), tempWeapon.af, null);
        }
    
        
        g2d.drawString("Time: "+ timeSec, 10,10);
        g2d.drawString("Score: "+ p1.score, 10,20);
        g2d.drawString("HP: "+ p1.hp, 10,30);
    }
    
    
    public void spawn()
    {
        if(timeSec % 3 == 0)
        {
            //spawn every 5 sec
            double rand = Math.random()*(10+timeSec/60);   //increasing difficulty
            
            
            if(rand>7)
                enemyList.add(new Ghost(0,0));
                
            rand = Math.random()*(10+timeSec/60);
            if(rand>8)
                enemyList.add(new Ghost(3000,2250));
            
            rand = Math.random()*(10+timeSec/60);
            if(rand>8)
                enemyList.add(new Ghost(3000,0));
            
            rand = Math.random()*(10+timeSec/60);
            if(rand>8)
            {
                enemyList.add(new Ghost(0,2250));
                enemyList.add(new Ghost(2000,0));
            }
            
            rand = Math.random()*(10+timeSec/60);
            if(rand>0)
                enemyList.add(new Squirrel(1000,1000));
                
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
        else if(boardX+dx>(map.getWidth(null) - Survival.mainFrame.getWidth()))
        {   
            p1.moveXSide(dx- ((map.getWidth(null) - Survival.mainFrame.getWidth())-boardX));
            boardX = (map.getWidth(null) - Survival.mainFrame.getWidth());
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
        else if(boardY+dy>(map.getHeight(null)-Survival.mainFrame.getHeight()))
        {    
            p1.moveYSide(dy - ((map.getHeight(null)-Survival.mainFrame.getHeight())-boardY));
            boardY = map.getHeight(null)-Survival.mainFrame.getHeight();
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
            if (key == KeyEvent.VK_Z)
            {
                p1.melee();
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
