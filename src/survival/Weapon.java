
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import javax.swing.ImageIcon;


public class Weapon 
{
    boolean blockable;      //able to be blocked
    int dmg;
    int realX, realY;
    int playerCenterX, playerCenterY;
    
    boolean up;         //facing 
    boolean down;
    boolean left;
    boolean right;
    
    boolean end;        //check if melee attack has ended
    
    ImageIcon i;
    Image img;
    
    Area areaShape;
    AffineTransform af;
    
    public Weapon(int realX, int realY, String img_name, boolean up, boolean down, boolean left, boolean right)
    {        
        playerCenterX = realX;
        playerCenterY = realY;
        
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        
        af = new AffineTransform();
        
        i = new ImageIcon(Survival.class.getResource(img_name));
        img = i.getImage();
        
        this.realX = realX - img.getWidth(null)/2;
        this.realY = realY - img.getHeight(null);
        
        end = false;
    }
    
    public void directionAdjust()
    {
        
        af.setToTranslation(getX(), getY());
        //pivot location is img.getWidth(null)/2, img.getHeight(null)
        directionAdjustHelper();
    }
    
    public void directionAdjustHelper()
    {
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
    {   //for inheritance implementation
    }
    
    public Shape getBounds()
    {
        Shape tempRect = new Rectangle(0, 0, img.getWidth(null), img.getHeight(null));
        Area a = new Area(tempRect);
        a.transform(af);
        return a;
    }
    
    public int getX()
    {
        return realX-Board.boardX;
    }
    public int getY()
    {
        return realY-Board.boardY;
    }
    
    public Image getImage()
    {
        return img;
    }
}
