/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;



public class MapBlock 
{
    static ArrayList allBlocks;     //arrayList of blocks
    
    public MapBlock()
    {
        allBlocks = new ArrayList();
        addBlocks();
    }
    
    public void addBlocks()
    {
        //add blocks here
        
        allBlocks.add(new Block("images/map_block1.png", 700, 600));
        allBlocks.add(new Block("images/map_block1.png", 100, 5));
        allBlocks.add(new Block("images/map_block1.png", 10, 2200));
        allBlocks.add(new Block("images/map_block1.png", 250, 2200));
        allBlocks.add(new Block("images/map_block1.png", 2000, 1000));
    }
    
}


