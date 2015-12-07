package nz.ac.aut.prog2.CaveAdventure.model;

import static org.junit.Assert.*;
import org.junit.Test; // for @Test
import org.junit.Before; // for @Before
import org.junit.After; // for @After

/**
 * Test framework for the Position class.
 *
 * @author Anne Philpott and Stefan Marks
 * @version v1.0 - 2012.06: Created
 */

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PositionTest 
{
    private Position position;
    private CaveWorld world;
    
    /**
     * Default constructor for test class.
     */
    public PositionTest()
    {
    }

   
    @Before
    public void setUp()
    {
        world = new CaveWorld(5, 5);
        position = new Position(world, 1, 2);
    }

    
    @After
    public void tearDown()
    {
        world = null;
        position = null;      
    }

    // Tests to ensure correct exceptions thrown for invalid arguments
    @Test
    public void testIllegalArgumentNoWorld() throws Exception 
    {
        try 
        {
            Position invalidPosition = new Position(null,0,0);
            fail("No exception thrown when world null.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message",expected.getMessage().indexOf("World") >= 0);
        }
        
    }

    @Test
    public void testIllegalArgumentRowNegative() throws Exception 
    {
        try 
        {
            Position invalidPosition = new Position(world,-1,0);
            fail("No exception thrown when row negative.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message",expected.getMessage().indexOf("Invalid row") >= 0);
        }
        
    }
    
    @Test
    public void testIllegalArgumentRowTooLarge() throws Exception
    {
        try 
        {
            Position invalidPosition = new Position(world,5,0);
            fail("No exception thrown when row too large.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message",expected.getMessage().indexOf("Invalid row") >= 0);
        }
        
    } 
    
    @Test
    public void testIllegalArgumentColumnNegative() throws Exception
    {
        try 
        {
            Position invalidPosition = new Position(world,1,-1);
            fail("No exception thrown when column negative.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message",expected.getMessage().indexOf("Invalid column") >= 0);
        }
        
    }
    
    @Test
    public void testIllegalArgumentColumnTooLarge() throws Exception
    {
        try 
        {
            Position invalidPosition = new Position(world,0,5);
            fail("No exception thrown when column too large.");
        }
        catch (IllegalArgumentException expected) 
        {
            assertTrue("Not expected exception message",expected.getMessage().indexOf("Invalid column") >= 0);
        }
        
    }
    
    // Test column accessor
    @Test
    public void testGetColumn()
    {
        assertEquals(2, position.getColumn());
    }    

    // Test row accessor
    @Test
    public void testGetRow()
    {
        assertEquals(1, position.getRow());
    }  

}
