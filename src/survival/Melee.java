/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survival;

/**
 *
 * @author kchao
 */
public class Melee extends Weapon
{
    int finalRotation;
    int currentRotation;
    int rotationSpeed;
    double xscale = 0.0;
    double yscale = 0.0;
    
    public Melee(int realX, int realY, boolean up, boolean down, boolean left, boolean right)
    {
        super(realX, realY, "images/" + Survival.player_name + "/melee.png",
                up, down, left, right);
        
        blockable=false;
        finalRotation = 180;
        currentRotation = 0;
        rotationSpeed = 12;
        
        directionAdjust();
        meleeAdjust();
    }
    
    public Melee(int realX, int realY, boolean up, boolean down, boolean left, boolean right, int finalRotation, int rotationSpeed)
    {
        super(realX, realY, "images/" + Survival.player_name + "/melee.png",
                up, down, left, right);
        
        blockable=false;
        this.finalRotation = finalRotation;
        currentRotation = 0;
        this.rotationSpeed = rotationSpeed;
        
        directionAdjust();
        meleeAdjust();
    }
    
    
    public Melee(int realX, int realY, boolean up, boolean down, boolean left, boolean right, int finalRotation, int rotationSpeed, double xscale, double yscale)
    {
        super(realX, realY, "images/" + Survival.player_name + "/melee.png",
                up, down, left, right);
        
        blockable=false;
        this.finalRotation = finalRotation;
        currentRotation = 0;
        this.rotationSpeed = rotationSpeed;
        this.xscale = xscale;
        this.yscale = yscale;
        
        directionAdjustScale(xscale, yscale);
        meleeAdjust();
    }
    
    public Melee(int realX, int realY, boolean up, boolean down, boolean left, boolean right, double xscale, double yscale)
    {
        
        super(realX, realY, "images/" + Survival.player_name + "/melee.png",
                up, down, left, right);
        System.out.println(Math.random()+"");
        blockable=false;
        finalRotation = 180;
        currentRotation = 0;
        rotationSpeed = 10;
        this.xscale = xscale;
        this.yscale = yscale;
        
        
        directionAdjustScale(xscale, yscale);
        meleeAdjust();
    }
    
    
    public Melee(int realX, int realY, boolean up, boolean down, boolean left, boolean right, String filename)
    {
        super(realX, realY, "images/" + Survival.player_name + "/" + filename,
                up, down, left, right);
        
        blockable=false;
        finalRotation = 180;
        currentRotation = 0;
        rotationSpeed = 12;
        
        directionAdjust();
        meleeAdjust();
    }
    
    public void meleeAdjust()
    {
        af.rotate(Math.toRadians(-finalRotation/2), img.getWidth(null)/2, img.getHeight(null));
    }
    
    public void move()
    {
        af.rotate(Math.toRadians(rotationSpeed),img.getWidth(null)/2, img.getHeight(null));
        currentRotation+=rotationSpeed;
        
        if(currentRotation>=finalRotation)
        {
            end = true;
        }
    }
    
    
    public void playerMove()
    {
        af.setToRotation(Math.toRadians(0));
        af.setToTranslation(Board.p1.getRelativeCenterX() - img.getWidth(null)/2, Board.p1.getRelativeCenterY() - img.getHeight(null));
        
        if(xscale != 0.0 || yscale != 0.0)
        {
            af.setToTranslation(Board.p1.getRelativeCenterX() - img.getWidth(null)*xscale/2, Board.p1.getRelativeCenterY() - img.getHeight(null)*yscale);
            af.scale(xscale, yscale);
        }
        
        directionAdjustHelper();
        
        af.rotate(Math.toRadians(-finalRotation/2+currentRotation), img.getWidth(null)/2, img.getHeight(null));
        
    }
    
    
        
}
