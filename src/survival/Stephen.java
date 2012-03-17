/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import javax.sound.sampled.Clip;

/**
 *
 * @author kchao
 */
public class Stephen extends Player
{
    int shot_speed;
    public Stephen()
    {
        super();
        
        armor = 1;
        attackDelay = 0.2;
        shot_speed = 23;
    }
    
    
    
    
    public void melee()
    {
        for(int i = 0; i<shots.size(); i++)
        {   //make melee object unique
            if(((Weapon)shots.get(i)) instanceof Melee)
                return;
        }
        Melee tempMelee;
        if(score < Board.level)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 90, 5);
        else if(score < Board.level2)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 90, 5, 1.5, 1.5);
        else
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 90, 5, 2, 2);
        
        clipZ.setFramePosition(0);
        clipZ.loop(0);
        shots.add(tempMelee);
    }
    
    
    public void shoot()
    {
        if(Board.timeSec-lastShot >= attackDelay || attackDelay ==0)
        {
            clipX.setFramePosition(0);
            clipX.loop(0);
            
            if(score < Board.level)
            {
                shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, shot_speed));
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
        int dist = 250;     //distance from center
        shots.add(new SpecialWeapon(this.getRealCenterX()+dist, this.getRealCenterY()+dist, 
                up, down, left, right));
        if (clipC.isRunning())
            clipC.stop();
        Survival.clip.stop();
        clipC.setFramePosition(0);
        clipC.loop(0);
        
    }
    
    
    
    private class SpecialWeapon extends Weapon
    {
        private double startTime;
        double attackDelay = 0.5;
        double lastShot = 0;       //sec of last shot
        double stationX;
        double stationY;
        
        public SpecialWeapon(int realX, int realY, boolean up, boolean down, boolean left, boolean right)
        {
            super(realX, realY, "images/" + Survival.player_name + "/tswift.png",
                true, false, false, false);
            
            startTime = Board.timeSec;
            stationX = getX();
            stationY = getY();
            
            directionAdjust();
        }
        
        public void move()
        {
            //adjust photo direction
            directionAdjust();
            //do shoot attack here
            if(Board.timeSec - startTime <= 6)
            {
                if(Board.timeSec - lastShot >= attackDelay)
                {
                    lastShot = Board.timeSec;
                    //full set
                    shots.add(new Shot(realX+15, realY+120, true, false, false, false, noteFile(), false));
                    shots.add(new Shot(realX+15, realY+120, false, true, false, false, noteFile(), false));
                    shots.add(new Shot(realX+15, realY+120, false, false, true, false, noteFile(), false));
                    shots.add(new Shot(realX+15, realY+120, false, false, false, true, noteFile(), false));

                    shots.add(new Shot(realX+15, realY+120, true, false, true, false, noteFile(), false));
                    shots.add(new Shot(realX+15, realY+120, true, false, false, true, noteFile(), false));
                    shots.add(new Shot(realX+15, realY+120, false, true, true, false, noteFile(), false));
                    shots.add(new Shot(realX+15, realY+120, false, true, false, true, noteFile(), false));
                }
            }
            
            else
            {
                end = true;
                Survival.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        
        private String noteFile()
        {
            //gets random note
            int rand = (int)(Math.random() * 6)+1;
            String noteFile = "music" + rand + ".png";
            return noteFile;
        }
                
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //override implementation
    public void triRangeShotHelper()
    {   //shoots three shots
        boolean tempUp = up;
        boolean tempDown = down;
        boolean tempLeft = left;
        boolean tempRight = right;

        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, shot_speed));

        //shift clockwise
        up = ((tempLeft && !tempDown) || (tempUp && !tempLeft && !tempRight)) ? true : false;
        right = ((tempUp && !tempLeft) || (tempRight && !tempUp && !tempDown)) ? true : false;
        down = ((tempRight && !tempUp) || (tempDown && !tempRight && !tempLeft)) ? true : false;
        left = ((tempDown && !tempRight) || (tempLeft && !tempDown && !tempUp)) ? true : false;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, shot_speed));

        //shift counterclockwise
        up = ((tempRight && !tempDown) || (tempUp && !tempLeft && !tempRight)) ? true : false;
        right = ((tempDown && !tempLeft) || (tempRight && !tempUp && !tempDown)) ? true : false;
        down = ((tempLeft && !tempUp) || (tempDown && !tempRight && !tempLeft)) ? true : false;
        left = ((tempUp && !tempRight) || (tempLeft && !tempDown && !tempUp)) ? true : false;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, shot_speed));

        up = tempUp;
        down = tempDown;
        left = tempLeft;
        right = tempRight;
    }
    
    public void halfRangeShotHelper()
    {   //shoots 5 shots
        triRangeShotHelper();
        
        boolean tempUp = up;
        boolean tempDown = down;
        boolean tempLeft = left;
        boolean tempRight = right;
        
        //90 degree movement
        up = tempLeft; right = tempUp; down = tempRight; left = tempDown;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, shot_speed));
        
        //-90 degree movement
        up = tempRight; right = tempDown; down = tempLeft; left = tempUp;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, shot_speed));
        
        up = tempUp;
        down = tempDown;
        left = tempLeft;
        right = tempRight;
    }
    
    
    
}
