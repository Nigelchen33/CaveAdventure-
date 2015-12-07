package nz.ac.aut.prog2.CaveAdventure.model;


/**
 * Dynamite is a specialised item used to remove rockfalls.
 * 
 * @author Anne 
 * @version April 2013 -  Assignment Stage 2
 * @version May 2013  - JW
 * - Refactored Timer functionality deferred countingDown flag to Timer
 */

import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Dynamite extends Item
{
    //class constants
    private static final String REPRESENTATION = "۞";
    public static final int COUNT_DOWN_TIME = 15000;
    
    private Timer explosionTimer;
    private int explosionDelay;

    /**
     * Constructor for objects of class Dynamite
     */
    public Dynamite(Position position, String name, String longDescription, int id)
    {
        super(position, name, longDescription, id, true, 0.0);
        explosionDelay = COUNT_DOWN_TIME;
        explosionTimer = null;
       
    }
    
   /**
    * Has the dynamite been triggered?
    */
    public boolean countingDown()
    {
        return explosionTimer.isRunning();
    }

    /**
     * Picking up dynamite sets off the explosion timer
     * 
     */
    public void startExplosionTimer(ActionListener explosionListener)
    {
       if(explosionTimer == null )
       {
           explosionTimer= new Timer(explosionDelay, explosionListener);
           explosionTimer.setRepeats(false);
       }
       explosionTimer.start();
    }
    
    /**
     * Stop the explosion timer.
     */
    public void deactivate()
    { 
        explosionTimer.stop();
    }
    
 /**
  * @returns a string representation of dynamite
  */
 @ Override
  public String getRepresentation()
  {
       return REPRESENTATION;
  }    
}
