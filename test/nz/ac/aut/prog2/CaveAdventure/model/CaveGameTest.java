package nz.ac.aut.prog2.CaveAdventure.model;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CaveGameTest.
 *
 * @author  Anne
 * @version Testing stage 2 assignment
 */
public class CaveGameTest
{

    CaveGame testGame;
    
    /**
     * Default constructor for test class CaveGameTest
     */
    public CaveGameTest()
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
        testGame = new CaveGame();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        testGame = null;
    }
    
    @Test
    public void testPlayerMoveValidNoHazard()
    {
        assertTrue(testGame.playerMove(Direction.SOUTH));
        assertTrue(testGame.isPlayerAlive());
        assertEquals(95.0, testGame.getPlayerEnergyLevel(), 0.0);
    }
    
    
    @Test
    public void testPlayerMoveInvalid()
    {
        assertFalse(testGame.playerMove(Direction.WEST));
        assertEquals(100.0, testGame.getPlayerEnergyLevel(), 0.0);
    }
    
    @Test 
    public void testPlayerMoveBlockedRockfall()
    {
        assertFalse(testGame.playerMove(Direction.EAST));
        assertEquals(100.0, testGame.getPlayerEnergyLevel(), 0.0);
    }
    
    @Test
    public void testPlayerMoveValidWithNonFatalHazard()
    {
        assertTrue(testGame.playerMove(Direction.SOUTH));
        assertTrue(testGame.playerMove(Direction.SOUTH));
        assertTrue(testGame.isPlayerAlive());
        assertEquals(75.0, testGame.getPlayerEnergyLevel(), 0.0);
    }
    
    @Test
    public void testPlayerMoveValidFatalHazard()
    {
        assertTrue(testGame.playerMove(Direction.SOUTH));
        assertTrue(testGame.playerMove(Direction.EAST));
        assertFalse(testGame.isPlayerAlive());
    }  
    
    @Test
    public void testThrowDynamiteAtRockfall()
    {
       Cave cave = testGame.getCurrentCave();
       Dynamite dynamite = cave.getDynamite();
       testGame.playerPickUp(dynamite);
       testGame.throwDynamite(Direction.EAST);
       assertFalse(testGame.hasRockfall(Direction.EAST));   
    }
    
    @Test
    public void testThrowDynamiteNoRockfall()
    {
       Cave cave = testGame.getCurrentCave();
       Dynamite dynamite = cave.getDynamite();
       testGame.playerPickUp(dynamite);
       testGame.throwDynamite(Direction.SOUTH);
       assertTrue(testGame.hasRockfall(Direction.SOUTH));   
    }    
}
