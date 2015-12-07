package nz.ac.aut.prog2.CaveAdventure.model;

import static org.junit.Assert.*;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After; // for @After

/**
 * Test class for Hazard
 * @author aphilpot
 * @version Stage 1
 */
public class HazardTest extends junit.framework.TestCase {

    private Hazard fatalHole;
    Position position;
    CaveWorld world;

    /**
     * Default constructor for test class HazardTest
     */
    public HazardTest() {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    public void setUp() {
        world = new CaveWorld(5, 5);
        position = new Position(world,3,4);
        fatalHole = new Hazard(position, "hole", "deep dark bottomless pit", -1.0);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    public void tearDown() {
        fatalHole = null;
    }

    @Test
    public void testGetLongDescription() {
        assertEquals("deep dark bottomless pit", fatalHole.getLongDescription());
    }

    @Test
    public void testGetName() {
        assertEquals("hole", fatalHole.getName());
    }
    
    @Test
    public void testGetPosition()
    {
        assertEquals(position, fatalHole.getPosition());
    }

    @Test
    public void testSetPosition() 
    {
        Position newPosition = new Position(world,1,2);
        fatalHole.setPosition(newPosition);
        assertEquals(newPosition, fatalHole.getPosition());
    }    

    @Test
    public void testGetImpact() {
        assertEquals(-1.0, fatalHole.getImpact(), 0.0);
    }    

    //tests of isFatal method
    @Test
    public void testIsFatal() {
        assertTrue(fatalHole.isFatal());
    }
    
    @Test
    public void testIsFatalNotFatal() {
        Hazard  nonFatalHazzard = new Hazard(position, "hole", "deep dark bottomless pit", 2.5);
        assertTrue(fatalHole.isFatal());
    }
   

    @Test
    public void testGetRepresentation() {
        assertEquals("!", fatalHole.getRepresentation());
    }

}