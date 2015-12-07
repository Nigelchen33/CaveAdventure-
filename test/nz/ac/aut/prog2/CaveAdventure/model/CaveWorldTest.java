package nz.ac.aut.prog2.CaveAdventure.model;

import static org.junit.Assert.*;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After; // for @After
/**
 * The test class CaveWorldTest.
 *
 * @author  Anne
 * @version v1.0 - 2011.02: Created
 * @version v1.0 - 2013.02: Modified for Cave Adventure Stage 1(jlw), now allows use of junit tags
 */
public class CaveWorldTest extends junit.framework.TestCase
{
    CaveWorld testWorld;
    /**
     * Default constructor for test class CaveWorldTest
     */
    public CaveWorldTest()
    {
    }

    @Before
    protected void setUp()
    {
        testWorld = new CaveWorld(4,5);
    }

    @After
    protected void tearDown()
    {
        testWorld = null;
    }

    @Test
    public void testGetNumRows() 
    {
        assertEquals(4, testWorld.getNumRows());
    }  

    @Test
    public void testGetNumColumns() 
    {
        assertEquals(5, testWorld.getNumColumns());
    } 

    @Test
    public void testAddOccupant() 
    {
        Player lisa = new Player(new Position(testWorld,1,1), "Lisa", 50.0, 2);
        Position newPosition = new Position(testWorld,2,3);
        assertTrue(testWorld.addOccupant(newPosition, lisa));
        assertEquals(2, lisa.getPosition().getRow());
        assertEquals(3, lisa.getPosition().getColumn());        
    } 

}
