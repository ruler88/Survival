/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Player {
    
    boolean up;         //facing 
    boolean down;
    boolean left;
    boolean right;
    
    int x,y;        //x,y coordinates
    int stationX, stationY;     //stationary x,y coordinates
    
    int hp;
    double armor;
    int move_speed;
    int score;
    
    Image player_img;
    
    ArrayList shots = new ArrayList();
    
    
    public Player()
    {
        hp = 100;
        score = 0;
        up = false;
        down = false;    //start at down position
        left = false;
        right = false;

        //****ADJUST LATER****
        stationX = x=Survival.mainFrame.getWidth()/2 - 10;
        stationY = y=Survival.mainFrame.getHeight()/2 - 10;
        
        move_speed = 10;
    }
    
    public void shoot()
    {
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));
    }
    
    
    public Rectangle getBounds()
    {
        return new Rectangle(x,y, player_img.getWidth(null), player_img.getHeight(null));
    }
    public Rectangle getBounds(int x, int y)
    {
        return new Rectangle(x,y, player_img.getWidth(null), player_img.getHeight(null));
    }
    
    public int getRealX()
    {
        return Board.boardX+x;
    }
    public int getRealY()
    {
        return Board.boardY+y;
    }
    
    public int getRealCenterX()
    {
        return Board.boardX+x+(player_img.getWidth(null)/2);
    }
    
    public int getRealCenterY()
    {
        return Board.boardY+y+(player_img.getHeight(null)/2);
    }
    
    
    /****** MOVEMENT FIX ****/
    public boolean moveXNorm(int dx)
    {
        //edge recovery
        if(x>stationX && dx < 0)
        {   
            dx = (x+dx) > stationX ? dx : stationX-x;
            moveHelper(dx,0);
            return true;
        }
        else if(x<stationX && dx>0)
        {
            dx = (x+dx) < stationX ? dx : stationX-x;
            moveHelper(dx,0);
            return true;
        }

        return false;
    }
    
    public void moveXSide(int dx)
    {
        //towards edge         
        if (x+dx < 0)
            x = 0;
        else if (x+dx >1280-player_img.getWidth(null))
            x = 1280-player_img.getWidth(null);
        else
            moveHelper(dx, 0);

    }
    
    
    
    public boolean moveYNorm(int dy)
    {
        if(y>stationY && dy < 0)
        {   
            dy = (y+dy) > stationY ? dy : stationY-y;
            moveHelper(0,dy);
            return true;
        }
        else if(y<stationY && dy>0)
        {
            dy = (y+dy) < stationY ? dy : stationY-y;
            moveHelper(0,dy);
            return true;
        }

        return false;
    }
    
    public void moveYSide(int dy)
    {
        if (y+dy < 0)
            y = 0;
        else if (y+dy >825-player_img.getHeight(null))
            y = 825-player_img.getHeight(null);
        else
            moveHelper(0, dy);
    }
    
    
    
    public void moveHelper(int dx, int dy)
    {
        Block tempBlock;
        for(int i=0; i<MapBlock.allBlocks.size(); i++)
        {
            tempBlock = (Block) MapBlock.allBlocks.get(i);
            if(this.getBounds(x+dx, y+dy).intersects(tempBlock.getBounds()))
            {
                if(dx>0)
                {
                    //right
                    dx = tempBlock.getX() - (x + player_img.getWidth(null));
                }
                else if(dx<0)
                {
                    dx = x - (tempBlock.getX()+tempBlock.getImage().getWidth(null));
                }
                
                if(dy>0)
                {
                    //down
                    dy = tempBlock.getY() - (y + player_img.getHeight(null));
                }
                else if(dy<0)
                {
                    dy = (tempBlock.getY()+tempBlock.getImage().getHeight(null))-y;
                    System.out.println(dy);
                }
                x+=dx;
                y+=dy;
                
                return;
            }
        }
        
        x+=dx;
        y+=dy;
    }

    
    
    
}
