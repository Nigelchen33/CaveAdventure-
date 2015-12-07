package nz.ac.aut.prog2.CaveAdventure.model;

import static org.junit.Assert.*;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After; // for @After

/**
 * Test class for Item
 * @author aphilpot
 * @version Stage 1
 */
public class ItemTest extends junit.framework.TestCase {

    Item carry;
    Item treasure;
    Position posCarryItem;
    Position posTreasure;
    CaveWorld world;

    /**
     * Default constructor for test class HazardTest
     */
    public ItemTest() {
    }
    
   @Before
    public void setUp() {
        world = new CaveWorld(5, 5);
        posCarryItem = new Position(world,2,1);
        posTreasure = new Position(world,1,1);
        carry = new Item(posCarryItem,"key", "Small and rusty", 0, true,0.0 );
        treasure = new Item(posTreasure,"gold", "Huge chest of old dull coins", 1, false,100.0 );
    }

    @After
    public void tearDown() {
        carry = null;
        treasure = null;
    }

    @Test
    public void testGetName() {
        assertEquals("key", carry.getName());
    }

        
    @Test
    public void testGetPosition()
    {
        assertEquals(posTreasure, treasure.getPosition());
    }

    @Test
    public void testSetPosition() 
    {
        Position newPosition = new Position(world,1,2);
        treasure.setPosition(newPosition);
        assertEquals(newPosition, treasure.getPosition());
    }
    @Test
    public void testOKToCarry() {
        assertFalse(treasure.okToCarry());
    }

    @Test
    public void testGetEnergyContribution() {
        assertEquals(100.0, treasure.getEnergyContribution(), 0.0);
    }

    @Test
    public void testGetLongDescription() {
        assertEquals("Huge chest of old dull coins", treasure.getLongDescription());
    }

    
    @Test
    public void testGetRepresentation() {
        assertEquals("*", carry.getRepresentation());
    }

}