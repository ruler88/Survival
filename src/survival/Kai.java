
package survival;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;


public class Kai extends Player implements Runnable
{
    
    double penguinStartTime;
    double penguinDelay = 0.05;
    
    public Kai()
    {
        super();
        
        specialMax = 2;
        armor = 1.0;
        attackDelay = 1;
    }
    
    
    public void shoot()
    {
        if(Board.timeSec-lastShot >= attackDelay || attackDelay ==0)
        {
            collectImagePenguin();
            ninja = true;
            lastShot = Board.timeSec;
        }
    }
    
    
    public void melee()
    {
        shoot();
    }
    
    public void collectImagePenguin()
    {
        ImageIcon i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/penguin.png"));
        player_img = i.getImage();
        down_img = i.getImage();
        
        i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/penguin.png"));
        up_img = i.getImage();
        i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/penguin.png"));
        left_img = i.getImage();
        i = new ImageIcon(this.getClass().getResource("images/"+ Survival.player_name + "/penguin.png"));
        right_img = i.getImage();
    }
    
    
    public void specialAttack()
    {  
        if (clipC.isRunning())
            clipC.stop();
        Survival.clip.stop();
        clipC.setFramePosition(0);
        clipC.loop(0);
        
        penguinStartTime = Board.timeSec;
        
        Thread penguinRush;
        penguinRush = new Thread(this);
        penguinRush.start();
        
    }

    @Override
    public void run() 
    {
        
        while(Board.timeSec - penguinStartTime <= 5.5)
        {
            int rand = (int)(Math.random() * Board.map.getHeight(null)) - 20;
            
            Shot tempShot = new Shot(-30, rand, false, false, false, true, "penguinRush.png", false);
            tempShot.shot_speed = 6;
            shots.add(tempShot);
            
            try {Thread.sleep( (int)(1000*penguinDelay));} 
            catch (InterruptedException ex) {}
        }
        Survival.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    
    
    
    
}
