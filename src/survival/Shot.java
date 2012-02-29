/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import javax.swing.ImageIcon;

/**
 *
 * @author kchao
 */
public class Shot extends Weapon
{
    int shot_speed;
    
    boolean up;         //facing 
    boolean down;
    boolean left;
    boolean right;
    
    AffineTransform af = new AffineTransform();
    
    public Shot(int realX, int realY, boolean up, boolean down, boolean left, boolean right)
    {
        super(realX, realY, "images/" + Survival.player_name + "/shot_up.png");
        shot_speed = 10;
        blockable=true;
        
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        
        directionAdjust();
    }
    
    public void directionAdjust()
    {
        int relativeCenterX = playerCenterX-Board.boardX;
        int relativeCenterY = playerCenterY-Board.boardY;
        
        
        af.translate(getX(), getY());
        
        if(up)
        {
            if(left)
            {
                af.rotate(Math.toRadians(-45), relativeCenterX, relativeCenterY);
            }
            else if(right)
            {
                af.rotate(Math.toRadians(45), relativeCenterX, relativeCenterY);
            }
            else
            {
                af.rotate(Math.toRadians(0), relativeCenterX, relativeCenterY);
            }
        }
        else if(down)
        {
            if(left)
            {
                af.rotate(Math.toRadians(-135), relativeCenterX, relativeCenterY);
            }
            else if(right)
            {
                af.rotate(Math.toRadians(135), relativeCenterX, relativeCenterY);
            }
            else
            {
                af.rotate(Math.toRadians(180), relativeCenterX, relativeCenterY);
            }
        }
        else
        {
            if(left)
            {
                af.rotate(Math.toRadians(-90), relativeCenterX, relativeCenterY);
            }
            else if(right)
            {
                af.rotate(Math.toRadians(90), relativeCenterX, relativeCenterY);
            }
            else
            {
                af.rotate(Math.toRadians(0), relativeCenterX, relativeCenterY);
            }
        }
        
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(getX(), getY(), img.getWidth(null), img.getHeight(null));
    }
    
    public Image getImage()
    {
        return img;
    }
    
    public void move()
    {
        if(left)
            realX-=shot_speed;
        else if(right)
            realX+=shot_speed;
        
        if(up)
            realY-=shot_speed;
        else if(down)
            realY+=shot_speed;
    }
    
}
