/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;


public class Yena extends Player implements Runnable
{
    
    double explodeTime;
    double boomTime;
    
    public Yena()
    {
        super();
        
        armor = 1.0;
        attackDelay = 1;
        playerItem = new ArrayList();
        
        explodeTime = 2;
        boomTime = 0.26;
    }
    
    
    
    
    public void shoot()
    {
        if(Board.timeSec-lastShot >= attackDelay || attackDelay ==0)
        {
            clipX.setFramePosition(0);
            clipX.loop(0);
            
            lastShot = Board.timeSec;
            Thread bomb;
            bomb = new Thread(this);
            bomb.start();
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
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 60, 5);
        else if(score < Board.level2)
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 70, 5, 1.2, 1.2);
        else
            tempMelee = new Melee(getRealCenterX(), getRealCenterY(), up, down, left, right, 80, 5, 1.4, 1.4);
        
        clipZ.setFramePosition(0);
        clipZ.loop(0);
        shots.add(tempMelee);
    }
    
    
    public void specialAttack()
    {
        int dist = 250;     //distance from center
        shots.add(new SpecialWeapon(this.getRealCenterX()+dist+210, this.getRealCenterY()+dist+80, 
                true));
        shots.add(new SpecialWeapon(this.getRealCenterX()-dist-300, this.getRealCenterY()-dist+30, 
                false));

        if (clipC.isRunning())
            clipC.stop();
        Survival.clip.stop();
        clipC.setFramePosition(0);
        clipC.loop(0);
    }
    
    private class SpecialWeapon extends Weapon
    {
        private double startTime;
        double attackDelay = 1.4;
        double lastShot = 0;       //sec of last shot
        double stationX;
        double stationY;
        boolean leftDirection;
        
        public SpecialWeapon(int realX, int realY, boolean leftDirection)
        {
            super(realX, realY, "images/" + Survival.player_name + "/kierkegaard.png",
                true, false, false, false);
            
            startTime = Board.timeSec;
            stationX = getX();
            stationY = getY();
            
            this.leftDirection = leftDirection;
            
            directionAdjust();
        }
        
        public void move()
        {
            //adjust photo direction
            directionAdjust();
            //do shoot attack here
            if(Board.timeSec - startTime <= 12)
            {
                if(Board.timeSec - lastShot >= attackDelay)
                {
                    lastShot = Board.timeSec;
                    
                    if(leftDirection)
                        shots.add(new Shot(realX, realY+175, false, false, true, false, "philosophical_left.png", false));
                    else
                        shots.add(new Shot(realX, realY+175, false, false, false, true, "philosophical_right.png", false));
                }
            }
            
            else
            {
                end = true;
                Survival.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
    
    
    
    

    @Override
    public void run() 
    {

        //add bomb
        double bombStartTime = lastShot;
        Bomb tempBomb = new Bomb("images/" + Survival.player_name + "/bomb.png", 
                    getRealX(), getRealY(), explodeTime);

        playerItem.add(tempBomb);
        
        double timeLeft;
        while(Board.timeSec-bombStartTime <= explodeTime)
        {
            timeLeft = explodeTime - (Board.timeSec-bombStartTime);
            tempBomb.setTime(timeLeft);
            try {Thread.sleep( (int)(10));} 
            catch (InterruptedException ex) {}
        }

        //bomb ends
        //add explosion
        BombExplosion tempExplosion = new BombExplosion(
        tempBomb.realX, tempBomb.realY, "explosion.png");
        playerItem.remove(tempBomb);
        shots.add(tempExplosion);
        
        //sound section, don't worry too much about the try catch statements!! :(
        AudioInputStream audioBoom;
        Clip clipBoom;
        String tempString = "sounds/"+Survival.player_name+"/boom.wav";
        
        
        try {
            audioBoom = AudioSystem.getAudioInputStream(this.getClass().getResource(tempString));
            
            clipBoom = AudioSystem.getClip();
            clipBoom.open(audioBoom);
            
            clipBoom.setFramePosition(0);
            clipBoom.loop(0);
            
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Yena.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Yena.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Yena.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
    public class BombExplosion extends Shot
    {
        public BombExplosion(int realX, int realY, String img_name)
        {
            super(realX, realY, true, false, false, false, img_name, false);
            
            this.realY += this.img.getHeight(null);
            directionAdjust();
        }
        
        public void move()
        {
            this.directionAdjustScale(this.xscale+0.085, this.yscale+0.085);
            
            if(score < Board.level)
            {
                if(xscale >= 2.6)
                    end = true;
            
            }
            else if(score < Board.level2)
            {
                if(xscale >= 3.3)
                    end = true;
            }
            else
            {
                if(xscale >= 4.0)
                    end = true;
            }
            
        }
        
        public void directionAdjustScale(double xscale, double yscale)
        {
            af.setToTranslation(getX()+img.getWidth(null)/2-img.getWidth(null)*xscale/2, getY()+img.getHeight(null)/2-img.getHeight(null)*yscale/2);
            af.scale(xscale, yscale);

            directionAdjustHelper();

            this.xscale = xscale;
            this.yscale = yscale;

        }
                       
    }
    
    public class Bomb extends Block
    {
        String timeExplodeStr;
        double explodeTime;
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        public Bomb(String fileName, int realX, int realY, double explodeTime)
        {
            super(fileName, realX, realY);
            
            timeExplodeStr = "";
            this.explodeTime = explodeTime;
        }
        
        public void setTime(double time)
        {
            this.timeExplodeStr = twoDForm.format(time);
        }
        
        public String getTime()
        {
            return this.timeExplodeStr;
        }
        
    }
    
    
}
