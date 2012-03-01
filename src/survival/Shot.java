/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import javax.swing.ImageIcon;
import java.math.*;

/**
 *
 * @author kchao
 */
public class Shot extends Weapon
{
    int shot_speed;
    
    
    public Shot(int realX, int realY, boolean up, boolean down, boolean left, boolean right)
    {
        super(realX, realY, "images/" + Survival.player_name + "/shot_up.png",
                up, down, left, right);
        shot_speed = 10;
        blockable=true;
        
        
        directionAdjust();
    }
    
    public void directionAdjust()
    {
        
        af.translate(getX(), getY());
        //pivot location is img.getWidth(null)/2, img.getHeight(null)
        if(up)
        {
            if(left)
                af.rotate(Math.toRadians(-45), img.getWidth(null)/2, img.getHeight(null));
            else if(right)
                af.rotate(Math.toRadians(45), img.getWidth(null)/2, img.getHeight(null));
            else
                af.rotate(Math.toRadians(0), img.getWidth(null)/2, img.getHeight(null));
        }
        else if(down)
        {
            if(left)
                af.rotate(Math.toRadians(-135), img.getWidth(null)/2, img.getHeight(null));
            else if(right)
                af.rotate(Math.toRadians(135), img.getWidth(null)/2, img.getHeight(null));
            else
                af.rotate(Math.toRadians(180), img.getWidth(null)/2, img.getHeight(null));
        }
        else
        {
            if(left)
                af.rotate(Math.toRadians(-90), img.getWidth(null)/2, img.getHeight(null));
            else if(right)
                af.rotate(Math.toRadians(90), img.getWidth(null)/2, img.getHeight(null));
            else
                af.rotate(Math.toRadians(0), img.getWidth(null)/2, img.getHeight(null));
        }
        
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
        
        af.translate(0,-shot_speed);
    }
    
}
