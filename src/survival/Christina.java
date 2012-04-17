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
public class Christina extends Player
{
    String img_filename = "shot.png";
    
    public Christina()
    {
        super();
        
        armor = 0.7;
        attackDelay = 1;
        
    }
    
    
    public void shoot()
    {
        if(Board.timeSec-lastShot >= attackDelay || attackDelay ==0)
        {
            clipX.setFramePosition(0);
            clipX.loop(0);
            if(score < Board.level)
            {
                shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, img_filename, false));
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
    
    public void melee()
    {
        for(int i = 0; i<shots.size(); i++)
        {   //make melee object unique
            if(((Weapon)shots.get(i)) instanceof Melee)
                return;
        }
        Melee tempMelee;
        if(score < Board.level)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 50, 5);
        else if(score < Board.level2)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 50, 5, 1.5, 1.5);
        else
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 50, 5, 2, 2);
        
        clipZ.setFramePosition(0);
        clipZ.loop(0);
        shots.add(tempMelee);
    }
    
    
    public void specialAttack()
    {   
        int dist = 250;     //distance from center
        shots.add(new SpecialWeapon(Board.boardX-50, this.getRealCenterY()+dist, 
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
        double attackDelay = 0.1;
        double lastShot = 0;       //sec of last shot
        double stationX;
        double stationY;
        
        public SpecialWeapon(int realX, int realY, boolean up, boolean down, boolean left, boolean right)
        {
            super(realX, realY, "images/" + Survival.player_name + "/pikachu.png",
                false, false, false, true);
            
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
            if(Board.timeSec - startTime <= 5)
            {
                if(Board.timeSec - lastShot >= attackDelay)
                {
                    lastShot = Board.timeSec;
                    //full set
                    int rand = (int) (Math.random() * 1280);
                    shots.add(new Shot(Board.boardX+rand, 0, false, true, false, false, "thunderbolt.png", false));
                    
                }
                pikaMoveHelper();
            }
            
            else
            {
                end = true;
                Survival.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        
        public void pikaMoveHelper()
        {
            int shot_speed = 3;
            if(left)
                realX-=shot_speed;
            else if(right)
                realX+=shot_speed;        

            if(up)
                realY-=shot_speed;
            else if(down)
                realY+=shot_speed;

            af.translate(0,-shot_speed);
        }
    }
    
    
    
    
    
    //override implementation
    public void triRangeShotHelper()
    {   //shoots three shots
        boolean tempUp = up;
        boolean tempDown = down;
        boolean tempLeft = left;
        boolean tempRight = right;

        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, img_filename, false));

        //shift clockwise
        up = ((tempLeft && !tempDown) || (tempUp && !tempLeft && !tempRight)) ? true : false;
        right = ((tempUp && !tempLeft) || (tempRight && !tempUp && !tempDown)) ? true : false;
        down = ((tempRight && !tempUp) || (tempDown && !tempRight && !tempLeft)) ? true : false;
        left = ((tempDown && !tempRight) || (tempLeft && !tempDown && !tempUp)) ? true : false;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, img_filename, false));

        //shift counterclockwise
        up = ((tempRight && !tempDown) || (tempUp && !tempLeft && !tempRight)) ? true : false;
        right = ((tempDown && !tempLeft) || (tempRight && !tempUp && !tempDown)) ? true : false;
        down = ((tempLeft && !tempUp) || (tempDown && !tempRight && !tempLeft)) ? true : false;
        left = ((tempUp && !tempRight) || (tempLeft && !tempDown && !tempUp)) ? true : false;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, img_filename, false));

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
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, img_filename, false));
        
        //-90 degree movement
        up = tempRight; right = tempDown; down = tempLeft; left = tempUp;
        shots.add(new Shot(getRealCenterX(), getRealCenterY(), up, down, left, right, img_filename, false));
        
        up = tempUp;
        down = tempDown;
        left = tempLeft;
        right = tempRight;
    }
    
    
}
