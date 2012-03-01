/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


/**
 *
 * @author user
 */
public class Enemy extends Block
{
    double armor;
    int hp;
    
    int move_speed;
    
    Image img_left;
    Image img_right;
    
    /* BLOCK VARIABLES:
        ImageIcon i;
        Image img;
        int realX, realY;   //real coordinates
        int x,y;            //appearance coordinates, placeholder for reminder
     */
    
    public Enemy(String fileName, int realX, int realY)
    {
        super(fileName, realX, realY);
        
        //declare left and right image
        hp=100;
        img_right = img;
        i = new ImageIcon(Survival.class.getResource((fileName.replace("right", "left"))));
        img_left = i.getImage();
    }
    
    
    public void moveEnemy()
    {
        if(getX() > Board.p1.x)
        {
            moveHelper(-move_speed, 0);
            img = img_left;
        }
        else if (getX() < Board.p1.x)
        {
            moveHelper(move_speed, 0);
            img = img_right;
        }
        
        if(getY() > Board.p1.y)
        {
            moveHelper(0,-move_speed);
        }
        else if (getY() < Board.p1.y)
        {
            moveHelper(0,move_speed);
        }
    }
    
    public void moveHelper(int dx, int dy)
    {
        Block tempBlock;
        boolean intersect = false;
        for(int i=0; i<MapBlock.allBlocks.size(); i++)
        {
            tempBlock = (Block) MapBlock.allBlocks.get(i);
            if((this.getBounds(realX+dx, realY+dy).intersects(tempBlock.getBounds())))
            {
                intersect = true;
            }
        }
        
        if(!intersect)
        {
            realX+=dx;
            realY+=dy;
        }
    }
    
    public void hit()
    {
        hp -= 100/armor;
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(realX-Board.boardX, realY-Board.boardY, img.getWidth(null), img.getHeight(null));
    }

    public Rectangle getBounds(int realX, int realY)
    {
        return new Rectangle(realX-Board.boardX, realY-Board.boardY, img.getWidth(null), img.getHeight(null));
    }
    
    
}
