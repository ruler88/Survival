/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

/**
 *
 * @author kchao
 */
public class HPPot extends Block
{
    int healValue;
    
    public HPPot(int realX, int realY)
    {
        super("images/hp_pot.png", realX, realY);
        healValue = 50;
    }
}
