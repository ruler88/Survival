/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

/**
 *
 * @author user
 */
public class Ghost extends Enemy
{
    public Ghost(int x, int y)
    {
        super("images/ghost_right.png", x,y);
        move_speed = 2;
    }
}
