/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;


public class Player {
    
    boolean up;         //facing 
    boolean down;
    boolean left;
    boolean right;
    
    int x,y;        //x,y coordinates
    int stationX, stationY;     //stationary x,y coordinates
    
    int hp;
    double armor;       //1 for normal, range 0-2
    int move_speed;
    int score;
    boolean alive;
    
    Image player_img;
    Image up_img;
    Image down_img;
    Image left_img;
    Image right_img;
    
    ArrayList shots = new ArrayList();
    double attackDelay;
    double lastShot = 0;       //sec of last shot
    int special = 0;         //special skill meter
    int specialMax = 2;
    
    AudioInputStream audioZ;
    AudioInputStream audioX;
    AudioInputStream audioC;
    Clip clipZ;
    Clip clipX;
    Clip clipC;
    
    
    
    public Player()
    {
        hp = 100;
        score = 0;
        up = false;
        down = true;    //start at down position
        left = false;
        right = false;
        
        alive = true;
        
        collectImage();
        try {
            collectSound();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //****ADJUST LATER****
        stationX = x=Survival.mainFrame.getWidth()/2 - 10;
        stationY = y=Survival.mainFrame.getHeight()/2 - 10;
        
        move_speed = 8;
    }
    
    public void collectSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        String tempString;
        tempString = "sounds/"+Survival.player_name+"/z.wav";
        audioZ = AudioSystem.getAudioInputStream(this.getClass().getResource(tempString));
        clipZ = AudioSystem.getClip();
        clipZ.open(audioZ);
        
        tempString = "sounds/"+Survival.player_name+"/x.wav";
        audioX = AudioSystem.getAudioInputStream(this.getClass().getResource(tempString));
        clipX = AudioSystem.getClip();
        clipX.open(audioX);
        
        tempString = "sounds/"+Survival.player_name+"/c.wav";
        audioC = AudioSystem.getAudioInputStream(this.getClass().getResource(tempString));
        clipC = AudioSystem.getClip();
        clipC.open(audioC);
    }
    
    public void collectImage()
    {
        ImageIcon i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/down.png"));
        player_img = i.getImage();
        down_img = i.getImage();
        
        i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/up.png"));
        up_img = i.getImage();
        i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/left.png"));
        left_img = i.getImage();
        i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/right.png"));
        right_img = i.getImage();
    }
    
    
    public void imgRefresh()
    {
        if(up)
            player_img = up_img;
        else if(down)
            player_img = down_img;
        if(left)
            player_img = left_img;
        else if(right)
            player_img = right_img;
    }
    
    public void shoot()
    {
        if(Board.timeSec-lastShot >= attackDelay || attackDelay ==0)
        {
            shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));
            lastShot = Board.timeSec;
        }
    }
    
    public void specialAttack()
    {   //for implementation
    }
    
    public void triRangeShotHelper()
    {   //shoots three shots
        boolean tempUp = up;
        boolean tempDown = down;
        boolean tempLeft = left;
        boolean tempRight = right;

        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));

        //shift clockwise
        up = ((tempLeft && !tempDown) || (tempUp && !tempLeft && !tempRight)) ? true : false;
        right = ((tempUp && !tempLeft) || (tempRight && !tempUp && !tempDown)) ? true : false;
        down = ((tempRight && !tempUp) || (tempDown && !tempRight && !tempLeft)) ? true : false;
        left = ((tempDown && !tempRight) || (tempLeft && !tempDown && !tempUp)) ? true : false;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));

        //shift counterclockwise
        up = ((tempRight && !tempDown) || (tempUp && !tempLeft && !tempRight)) ? true : false;
        right = ((tempDown && !tempLeft) || (tempRight && !tempUp && !tempDown)) ? true : false;
        down = ((tempLeft && !tempUp) || (tempDown && !tempRight && !tempLeft)) ? true : false;
        left = ((tempUp && !tempRight) || (tempLeft && !tempDown && !tempUp)) ? true : false;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));

        up = tempUp;
        down = tempDown;
        left = tempLeft;
        right = tempRight;
    }
    
    public void halfRangeShotHelper()
    {   //shoots 5 shots
        triRangeShotHelper();
        
        boolean tempUp = up;
        boolean tempDown = down;
        boolean tempLeft = left;
        boolean tempRight = right;
        
        //90 degree movement
        up = tempLeft; right = tempUp; down = tempRight; left = tempDown;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));
        
        //-90 degree movement
        up = tempRight; right = tempDown; down = tempLeft; left = tempUp;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));
        
        up = tempUp;
        down = tempDown;
        left = tempLeft;
        right = tempRight;
    }
    
    public void melee()
    {
        for(int i = 0; i<shots.size(); i++)
        {   //make melee object unique
            if(((Weapon)shots.get(i)) instanceof Melee)
                return;
        }
        
        shots.add(new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right));
    }
    
    public void hit()
    {
        if(hp-10/armor > 0)
            hp -= 10/armor;
        else
        {
            hp = 0;
            alive = false;
        }
        if(special+1<=specialMax)
            special +=1;
    }
    
    
    public Rectangle getBounds()
    {
        return new Rectangle(x,y, player_img.getWidth(null), player_img.getHeight(null));
    }
    public Rectangle getBounds(int x, int y)
    {
        //argument x,y coordinates
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
    
    public int getRelativeCenterX()
    {
        return x+(player_img.getWidth(null)/2);
    }
    
    public int getRelativeCenterY()
    {
        return y+(player_img.getHeight(null)/2);
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
        else if (x+dx >Survival.mainFrame.getWidth()-player_img.getWidth(null))
            x = Survival.mainFrame.getWidth()-player_img.getWidth(null);
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
                }
                x+=dx;
                y+=dy;
                
                for(int a=0; a<shots.size(); a++)
                {
                    //move melee weapon with player
                    if(shots.get(a) instanceof Melee)
                        ((Melee)shots.get(a)).playerMove();
                }
                
                return;
            }
        }
        
        x+=dx;
        y+=dy;
        
        
        for(int a=0; a<shots.size(); a++)
        {
            //move melee weapon with player
            if(shots.get(a) instanceof Melee)
                ((Melee)shots.get(a)).playerMove();
        }
        
        
        
    }

    
    
    
}
