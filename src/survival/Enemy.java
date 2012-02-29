/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;


/**
 *
 * @author user
 */
public class Enemy extends Block
{
    double armor;
    int hp;
    
    boolean left;
    boolean right;
    
    int move_speed;
    
    /* BLOCK VARIABLES:
        ImageIcon i;
        Image img;
        int realX, realY;   //real coordinates
        int x,y;            //appearance coordinates, placeholder for reminder
     */
    
    public Enemy(String fileName, int realX, int realY)
    {
        super(fileName, realX, realY);
    }
    
    
    public void moveEnemy()
    {
        if(getX() > Board.p1.x)
        {
            realX-=move_speed;
        }
        else if (getX() < Board.p1.x)
        {
            realX+=move_speed;
        }
        
        if(getY() > Board.p1.y)
        {
            realY-=move_speed;
        }
        else if (getY() < Board.p1.y)
        {
            realY+=move_speed;
        }
    }
}
