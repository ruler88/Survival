/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import javax.swing.ImageIcon;

/**
 *
 * @author kchao
 */
public class Garrett extends Player
{
    public Garrett()
    {
        super();
        
        ImageIcon i = new ImageIcon(this.getClass().getResource("images/garrett/down.png"));
        player_img = i.getImage();
                
    }
}
