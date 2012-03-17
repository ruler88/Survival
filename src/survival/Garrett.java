/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import javax.sound.sampled.Clip;
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
        
        armor = 1.5;
        attackDelay = 1;
    }
    
    
    //implement shoot and melee
    public void shoot()
    {
        if(Board.timeSec-lastShot >= attackDelay || attackDelay ==0)
        {
            clipX.setFramePosition(0);
            clipX.loop(0);
            if(score < Board.level)
            {
                shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right));
                lastShot = Board.timeSec;
            }
            else if(score < Board.level2)
            {
                triRangeShotHelper();
                lastShot = Board.timeSec;
            }
            else
            {
                halfRangeShotHelper();
                lastShot = Board.timeSec;
            }
        }
    }
    
    public void specialAttack()
    {   
        if (clipC.isRunning())
            clipC.stop();
        
        Survival.clip.stop();
        clipC.setFramePosition(0);
        clipC.loop(2);
        int dist = 250;     //distance from center
        shots.add(new SpecialWeapon(this.getRealCenterX()+dist, this.getRealCenterY()+dist, 
                up, down, left, right));
        
        shots.add(new SpecialWeapon(this.getRealCenterX()-dist, this.getRealCenterY()-dist, 
                up, down, left, right));
        
        shots.add(new SpecialWeapon(this.getRealCenterX()-dist, this.getRealCenterY()+dist, 
                up, down, left, right));
        
        shots.add(new SpecialWeapon(this.getRealCenterX()+dist, this.getRealCenterY()-dist, 
                up, down, left, right));
        
    }
    
    public void melee()
    {
        for(int i = 0; i<shots.size(); i++)
        {   //make melee object unique
            if(((Weapon)shots.get(i)) instanceof Melee)
                return;
        }
        
        clipZ.setFramePosition(0);
        clipZ.loop(0);
        Melee tempMelee;
        if(score < Board.level)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right);
        else if(score < Board.level2)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 1.5, 1.5);
        else
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 2.0, 2.0);
        
        shots.add(tempMelee);
    }
    
    
    private class SpecialWeapon extends Melee
    {
        private double startTime;
        
        public SpecialWeapon(int realX, int realY, boolean up, boolean down, boolean left, boolean right)
        {
            super(realX, realY, up, down, left, right);
            startTime = Board.timeSec;            
        }
        
        public void move()
        {
            if(Board.timeSec - startTime <= 4)
            {
                af.scale(1.004,1.004);
                af.rotate(Math.toRadians(30),img.getWidth(null)/2, img.getHeight(null));
            }
            else
            {
                end = true;
                Survival.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
}
