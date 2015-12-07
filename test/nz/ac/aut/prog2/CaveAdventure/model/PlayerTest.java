package nz.ac.aut.prog2.CaveAdventure.model;

import static org.junit.Assert.*;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After; // for @After

import java.util.ArrayList;
/**
 * Test class for Player
 * @author aphilpot
 * @version Stage 1
 */
public class PlayerTest extends junit.framework.TestCase{

    private Player lisa;
    private Position lisaPosition;
    private Position itemPosition;
    private CaveWorld world;

   
    public PlayerTest() {
    }

    @Before
    public void setUp() {
        world = new CaveWorld(5, 5);
        lisaPosition = new Position(world,1,3);
        itemPosition = new Position(world,1,3);
        lisa = new Player(lisaPosition, "Lisa", 50.0, 2);
    }

    @After
    public void tearDown() {
        lisa = null;
    }

    @Test
    public void testGetName() {
        assertEquals("Lisa", lisa.getName());
    }
    
    @Test
    public void testGetPosition()
    {
        assertEquals(lisaPosition, lisa.getPosition());
    }

    @Test
    public void testSetPosition() 
    {
        Position newPosition = new Position(world,1,2);
        lisa.setPosition(newPosition);
        assertEquals(newPosition, lisa.getPosition());
    }
    
    @Test
    public void testMoveWhenEnergyToMove()
    {
        Position newPosition = new Position(world,1,2);
        lisa.move(newPosition);
        assertEquals(newPosition, lisa.getPosition()); 
        assertEquals(45.0, lisa.getEnergyLevel(), 0.0);
    }
    
    @Test
    public void testMoveWhenNotEnoughEnergyToMove()
    {
        Position newPosition = new Position(world,1,2);
        lisa.reduceEnergyLevel(46.0);
        lisa.move(newPosition);
        assertEquals(lisaPosition, lisa.getPosition());
        assertEquals(4.0, lisa.getEnergyLevel(), 0.0);
    } 
    
    @Test
    public void testIsAliveLivePlayer()
    {
        assertTrue(lisa.isAlive());
    }
    
    @Test
    public void testIsAliveDeadPlayer()
    {
       lisa.kill();
       assertFalse(lisa.isAlive());
    }
    
    @Test
    public void testHasEnergyToMoveEnoughEnergy()
    {
        assertTrue(lisa.hasEnergyToMove());
    }

    @Test
    public void testHasEnergyToMoveMinimumEnergy()
    {
        Player homer = new Player(lisaPosition, "Homer", 5.0, 2);
        assertTrue(homer.hasEnergyToMove());
    } 

    @Test
    public void testHasEnergyToMoveInsufficientEnergy()
    {
       Player  homer = new Player(lisaPosition, "Homer", 4.9, 2);
        assertFalse(homer.hasEnergyToMove());
    }    

    @Test
    public void testGetEnergyLevelInitialLevel() {
        assertEquals(50.0, lisa.getEnergyLevel(), 0.0);
    }

    // Tests for the reduceEnergyLevel method
    @Test
    public void testReduceEnergyLevelStaysAboveMin() {
        lisa.reduceEnergyLevel(12.0);
        assertEquals(38.0, lisa.getEnergyLevel(), 0.0);
    }

    @Test
    public void testReduceEnergyLevelBelowMin() {
        lisa.reduceEnergyLevel(52.0);
        assertEquals(0.0, lisa.getEnergyLevel(), 0.0);
    }

// Tests for the increase EnergyLevel method
    @Test
    public void testIncreaseEnergyLevel() {
        lisa.increaseEnergyLevel(12.0);
        assertEquals(62.0, lisa.getEnergyLevel(), 0.0);
    }

    @Test
    public void testIncreaseEnergyLevelNotNegative() {
        lisa.increaseEnergyLevel(-2.0);
        assertEquals(50.0, lisa.getEnergyLevel(), 0.0);
    }

    @Test
    public void testGetNumItemsCarried() {
        Item item = new Item(itemPosition,"key", "Small and rusty", 4, true,0.0 );
        assertEquals(true, lisa.pickUp(item));
        assertEquals(1, lisa.getNumItemsCarried());
    }
    
//Tests of pickUp method, of class Player.

    @Test
    public void testPickUpWhenNotAbleToCarry() {
        Item item = new Item(itemPosition,"boulder", "Too heavy to lift", 4, false,0.0 );
        assertFalse(lisa.pickUp(item));
    }    

    public void testPickUpWhenSpace() {
        Item item = new Item(itemPosition,"key", "Small and rusty", 4, true,0.0 );
        assertTrue( lisa.pickUp(item));
        assertTrue(lisa.has(item));
    }

    public void testPickUpNoRoom() {
        Item item = new Item(itemPosition,"key", "Small and rusty", 4, true,0.0 );
        lisa.pickUp(item);
        assertTrue(lisa.has(item));
        Item item2 = new Item(itemPosition,"map", "Dirty treasure map", 1, true,0.0 );
        lisa.pickUp(item2);
        Item item3 = new Item(itemPosition,"apple", "Big juicy apple", 2, true,0.0 );        
        assertFalse(lisa.pickUp(item3));
        assertFalse(lisa.has(item3));
    }

     @Test
     public void testPickUpDynamite() {
        Dynamite dynamite = new Dynamite(itemPosition,"dynamite", "Scary stick of dynamite", 4);
        assertTrue( lisa.pickUp(dynamite));
        assertTrue( dynamite.countingDown());
    }
    
     @Test
     public void testPickUpNoDuplicates() {
        Item item = new Item(itemPosition,"key", "Small and rusty", 4, true,0.0 );
        assertTrue( lisa.pickUp(item));
        assertFalse( lisa.pickUp(item));
    }
//Tests of drop method, of class Player.
   @Test
   public void testDropCarriedItem() {
        Item item = new Item(itemPosition,"key", "Small and rusty", 4, true,0.0 );
        lisa.pickUp(item);
        assertTrue(lisa.drop(item));
   }
   
   @Test
   public void testDropCarriedDynamite() {
        Dynamite dynamite = new Dynamite(itemPosition,"dynamite", "Scary stick of dynamite", 4);
        lisa.pickUp(dynamite);
        assertTrue(lisa.drop(dynamite));
        assertFalse(dynamite.countingDown());
   }

   @Test
    public void testDropItemNotCarried() {
        Item item = new Item(itemPosition,"key", "Small and rusty", 4, true,0.0 );
        assertEquals(false, lisa.drop(item));
    }

    // test actionPerformed(null);
    @Test
    public void testActionPerformed()
    {
        Dynamite dynamite = new Dynamite(itemPosition,"dynamite", "Scary stick of dynamite", 4);
        assertTrue( lisa.pickUp(dynamite));
        
        lisa.actionPerformed(null);
        assertFalse(lisa.hasDynamite());
        assertFalse(lisa.isAlive());
    }
    
    
   @Test
   public void testGetRepresentation() {
        assertEquals("P", lisa.getRepresentation());
   }
}