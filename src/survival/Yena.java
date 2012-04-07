/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


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
