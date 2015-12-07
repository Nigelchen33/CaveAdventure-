package nz.ac.aut.prog2.CaveAdventure.model;

import static org.junit.Assert.*;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After; // for @After

/**
 * The test class CaveTest.
 *
 * @author  Anne Philpott
 * @version v1.0 - 2011.02: Created
 * @version v1.0 - 2013.02: Modified for Cave Adventure Stage 1(jlw)
 */
public class CaveTest extends junit.framework.TestCase
{
    Cave emptyCave;
    CaveWorld world;
    Cave occupiedCave;
    Player lisa;
    Position position;    
    /**
     * Default constructor for test class CaveTest
     */
    public CaveTest()
    {
    }

   @Before
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
        world = new CaveWorld(5,5);
        emptyCave = new Cave();
        occupiedCave = new Cave();
        position = new Position(world, 1,1);
        lisa = new Player(position, "Lisa", 50.0, 2);
        occupiedCave.addOccupant(lisa);
        
    }

    @After
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
        emptyCave = null;
        occupiedCave = null;
        lisa = null;
    }
   // Tests for hasOccupant method 
   // !! Uncomment when you have implemented the hasOccupant() method !!
    @Test
    public void testHasOccupantWhenOccupied() 
    {
        assertTrue(occupiedCave.hasOccupant(lisa));
    } 

    @Test
    public void testHasOccupantWhenNotOccupied() 
    {
        assertFalse(emptyCave.hasOccupant(lisa));
    }

    @Test
    public void testIsLitPlayerPresent()
    {
        assertTrue(occupiedCave.isLit());
    }
    
    @Test
    public void testIsLitNoPlayerPresent()
    {
        Item key = new Item(position,"key", "Small and rusty", 0, true,0.0);        
        assertTrue(emptyCave.addOccupant(key));         
        assertFalse(emptyCave.isLit());
    }
    
   // Tests for addOccupant method 
    @Test
    public void testAddOccupantWhenRoom() 
    {
        Item key = new Item(position,"key", "Small and rusty", 0, true,0.0);        
        assertTrue(occupiedCave.addOccupant(key));        
        assertTrue(occupiedCave.hasOccupant(key));
    } 
    
    @Test
    public void testAddOccupantDuplicate() 
    {
        Item key = new Item(position,"key", "Small and rusty", 0, true,0.0);        
        assertTrue(occupiedCave.addOccupant(key));        
        assertTrue(occupiedCave.hasOccupant(key));
        assertFalse(occupiedCave.addOccupant(key));
    }    

    @Test
    public void testAddOccupantWhenFull() 
    {
        Item key = new Item(position,"key", "Small and rusty", 0, true,0.0);        
        occupiedCave.addOccupant(key);  
        // Another item to add, courtesy of Alex
        Item book = new Item(position,"book", "Large and dusty", 0, true, 0.0);
        occupiedCave.addOccupant(book); 
        //Now the cave has three occupants it should not be possible to add another
        Player homer =new Player(position, "Homer", 50.0, 2); 
        assertFalse(occupiedCave.addOccupant(homer));
        assertFalse(occupiedCave.hasOccupant(homer));
    }

    @Test
    public void testRemoveOccupantWhenPresent()
    {
        assertTrue(occupiedCave.removeOccupant(lisa));
    }

    
    @Test
    public void testRemoveOccupantWhenNull()
    {
        assertFalse(occupiedCave.removeOccupant(null));
    }    

    @Test
    public void testRemoveOccupantWhenNotPresent()
    {
       Item key = new Item(position,"key", "Small and rusty", 0, true,0.0); 
        assertFalse(occupiedCave.removeOccupant(key));
    }
    
   // Tests for isOccupied method 
    @Test
    public void testisOccupied_WhenEmpty()
    {
        assertFalse(emptyCave.isOccupied());
    }

    @Test
    public void testisOccupied_WhenOccupied()
    {
        assertTrue(occupiedCave.isOccupied());
    }

    @Test
    public void testHasFatalHazardNotFatal()
    {
        Hazard nonFatal = new Hazard(position, "hole", "tiny pothole", 1.0);
        occupiedCave.addOccupant(nonFatal);
        assertFalse(occupiedCave.hasFatalHazard());
    }
    
    @Test
    public void testHasFatalHazardFatal()
    {
        Hazard fatal = new Hazard(position, "hole", "bottomless pit", -1.0);
        occupiedCave.addOccupant(fatal);
        assertTrue(occupiedCave.hasFatalHazard());
    }
    
    @Test
    public void testHasFatalHazardMultipleHazardsOneFatal()
    {
        Hazard fatal = new Hazard(position, "hole", "bottomless pit", -1.0);
        emptyCave.addOccupant(fatal);
        Hazard nonFatal = new Hazard(position, "hole", "tiny pothole", 1.0);
        emptyCave.addOccupant(nonFatal);
        assertTrue(emptyCave.hasFatalHazard());
    }
    
    @Test
    public void testHasFatalHazardNoHazard()
    {
        assertFalse(occupiedCave.hasFatalHazard());
    }
    
    @Test
    public void testGetTotalHazardEnergyImpactSingleHazard()
    {
        Hazard nonFatal = new Hazard(position, "hole", "tiny pothole", 1.0);
        emptyCave.addOccupant(nonFatal);
        assertEquals(1.0, emptyCave.getTotalHazardEnergyImpact(), 0.0);
    }
    
    @Test
    public void testGetTotalHazardEnergyImpactMultipleHazards()
    {
        Hazard nonFatal1 = new Hazard(position, "hole", "tiny pothole", 1.5);
        emptyCave.addOccupant(nonFatal1);
        Hazard nonFatal2 = new Hazard(position, "hole", "tiny pothole", 5.0);
        emptyCave.addOccupant(nonFatal2);        
        assertEquals(6.5, emptyCave.getTotalHazardEnergyImpact(), 0.0);
    }
    
    @Test
    public void testGetTotalHazardEnergyImpactFatalHazard()
    {
        Hazard fatal = new Hazard(position, "hole", "bottomless pit", -1.0);
        emptyCave.addOccupant(fatal);
        assertEquals(-1.0, emptyCave.getTotalHazardEnergyImpact(), 0.0);
    }    
        
    // Tests for rockfall 
    @Test
    public void testCanExit_WithRockfall()
    {  
        occupiedCave.addRockFall(Direction.EAST);
        assertFalse(occupiedCave.canExit(Direction.EAST));    
    }
    
    public void testCanExit_NoRockfall()
    {  
        assertTrue(occupiedCave.canExit(Direction.WEST));    
    }
        
   
}
