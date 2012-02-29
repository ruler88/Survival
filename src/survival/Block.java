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
 * @author kchao
 */
public class Block 
{
        ImageIcon i;
        Image img;
        int realX, realY;   //real coordinates
        int x,y;            //appearance coordinates, placeholder for reminder
        
        public Block(String fileName, int realX, int realY)
        {
            i = new ImageIcon(Survival.class.getResource((fileName)));
            img = i.getImage();
            
            this.realX = realX;
            this.realY = realY;
            
        }
        
        public Rectangle getBounds()
        {
            return new Rectangle(getX(), getY(), img.getWidth(null), img.getHeight(null));
        }
        
        public Rectangle getBounds(int boardX, int boardY)
        {
            return new Rectangle(getX(boardX), getY(boardY), img.getWidth(null), img.getHeight(null));
        }
        
        public int getX()
        {
            return realX-Board.boardX;
        }
        public int getX(int boardX)
        {   //given boardX
            return realX-boardX;
        }
        public int getY()
        {
            return realY-Board.boardY;
        }
        public int getY(int boardY)
        {
            return realY-boardY;
        }
        
        public Image getImage()
        {
            return img;
        }
        
        
    }
