/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

/**
 *
 * @author kchao
 */
public class Squirrel extends Enemy
{
    public Squirrel(int x, int y)
    {
        super("images/squirrel_right.png", x,y);
        move_speed = 4;
        armor = 1;
        range = 350;
        attackDelay = 3;
    }
    
    
    public void moveEnemy()
    {
        //for ranged enemy
        if(getEnemyDistance() <= range)
        {
            rangeAttack("images/squirrel_shot.png");
        }
        else
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
    }
    
}
