
package survival;

import java.awt.Image;
import java.awt.geom.Area;
import javax.swing.ImageIcon;


public class Weapon 
{
    boolean blockable;      //able to be blocked
    int dmg;
    int realX, realY;
    int playerCenterX, playerCenterY;
    
    ImageIcon i;
    Image img;
    
    Area areaShape;
    
    public Weapon(int realX, int realY, String img_name)
    {        
        playerCenterX = realX;
        playerCenterY = realY;
        
        i = new ImageIcon(Survival.class.getResource(img_name));
        img = i.getImage();
        
        this.realX = realX - img.getWidth(null)/2;
        this.realY = realY - img.getHeight(null);
    }
    
    public int getX()
    {
        return realX-Board.boardX;
    }
    public int getY()
    {
        return realY-Board.boardY;
    }
}
