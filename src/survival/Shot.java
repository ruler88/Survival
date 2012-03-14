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
        super(realX, realY, "images/" + Survival.player_name + "/shot.png",
                up, down, left, right);
        shot_speed = 10;
        blockable=true;
        
        directionAdjust();
    }
    
    public Shot(int realX, int realY, boolean up, boolean down, boolean left, boolean right, int shot_speed)
    {
        super(realX, realY, "images/" + Survival.player_name + "/shot.png",
                up, down, left, right);
        this.shot_speed = shot_speed;
        blockable=true;
        
        directionAdjust();
    }
    
    public Shot(int realX, int realY, boolean up, boolean down, boolean left, boolean right, String filename)
    {
        //for enemy shot
        super(realX, realY, filename,
                up, down, left, right);
        shot_speed = 11;
        blockable=true;
        
        directionAdjust();
    }
    
    public Shot(int realX, int realY, boolean up, boolean down, boolean left, boolean right, String filename, boolean blockable)
    {
        //manually set blockable
        super(realX, realY, "images/" + Survival.player_name + "/" + filename,
                up, down, left, right);
        shot_speed = 11;
        this.blockable = blockable;
        
        directionAdjust();
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
