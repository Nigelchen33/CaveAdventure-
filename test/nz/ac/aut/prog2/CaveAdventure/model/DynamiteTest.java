package nz.ac.aut.prog2.CaveAdventure.model;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The test class DynamiteTest.
 *
 * @author  Anne
 * @version Example answer for Stage 2 extention
 */
public class DynamiteTest
{
    Dynamite dynamite;
    Position position;
    CaveWorld world;
    boolean exploded;
    long stopTime;
    /**
     * Default constructor for test class DynamiteTest
     */
    public DynamiteTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        world = new CaveWorld(5, 5);
        position = new Position(world,2,1);
        dynamite= new Dynamite(position,"Dynamite", "A single stick of dynamite", 5 );
        exploded = false;
        stopTime = 0;
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        dynamite = null;
        world = null;
        position = null;
    }
    
    @Test
    public void testStartExplosionTimerExplodes()
    {
        ActionListener explosionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                exploded = true;
                stopTime = System.currentTimeMillis();
            }
       };
       long startTime = System.currentTimeMillis();
       dynamite.startExplosionTimer(explosionListener);
       assertTrue(dynamite.countingDown());
       
       // Now wait for 15secs
       try {
           Thread.sleep(16000);
        } catch(InterruptedException ex) {
        } 
       assertTrue(exploded);
       //Happy if time was within 1 sec of required.
       stopTime = stopTime/1000;
       startTime = (startTime + 15000)/1000;
       assertEquals(stopTime, startTime);
    }
    
    @Test
    public void testDeactivate()
    {
       ActionListener explosionListener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                //Don't do anything
            }
       };
       dynamite.startExplosionTimer(explosionListener); 
       dynamite.deactivate();
       assertFalse(dynamite.countingDown());
    }
}
