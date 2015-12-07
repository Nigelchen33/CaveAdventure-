package nz.ac.aut.prog2.CaveAdventure.model;


/**
 * Enumeration class Direction 
 * Represents the directions that a player can move in CaveAdventure
 * 
 * @author Anne
 * @version Stage 1
 * @version 1.1
 * 2012:03  Refectored to allow this layer movement direction enum to be a more generic direction which
 * can then be used for rockfalls
 *        -renamed from MoveDirection to Direction (AP)
 *        -added an abbreviated version of the direction ("N" for North etc) (JW)
 *         to assist in the representation and processing of rockfalls from the data file) (JW)
 */
public enum Direction
{
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");
    
    private final String text;
    
    private Direction(String text)
    {
        this.text = text;
    }

    public String getAbbreviatedText() 
    {
        return this.text;
    }

}
