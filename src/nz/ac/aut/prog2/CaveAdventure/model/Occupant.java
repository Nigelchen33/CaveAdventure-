package nz.ac.aut.prog2.CaveAdventure.model;

/**
 * A class for any occupant of a cave.
 * 
 * @author Anne Philpott and Stefan Marks
 * @version v1.1 - Stage 1
 * @version v1.1 - 2013.02: Modified for Assignment 1 (jlw)

 */
public abstract class Occupant
{
    // the position of the occupant
    private Position position;
    private String name;

    /**
     * Constructor for an occupant instance.
     * 
     * @param initialPosition the initial position of the occupant
     * @param name of the occupant
     */
    public Occupant(Position initialPosition, String name)
    {
        this.position = initialPosition;
        this.name = name;
    }

    /**
     * Gets the position of the occupant.
     * 
     * @return the position of the occupant
     */
    public Position getPosition()
    {
        return this.position;
    }

    /**
     * Gets the name of the occupant.
     * 
     * @return the name of the occupant
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the position of the occupant.
     * 
     * @param newPosition the new position of the occupant
     */
    public void setPosition(Position newPosition)
    {
       if(position != null){
            position = newPosition;
       }
    }

    /**
     * Gets a string representation of the occupant, e.g., for printing to the screen.
     * 
     * @return a string representation of the basic occupant
     */
    public abstract String getRepresentation();
   
}
