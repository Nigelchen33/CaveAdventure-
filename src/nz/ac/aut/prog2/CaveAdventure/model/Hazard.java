package nz.ac.aut.prog2.CaveAdventure.model;


/**
 * Write a description of class Hazard here.
 * 
 * @author (Jacqui Whalley) 
 * @version 0.1
 * 
 * @version  0.2 2013.06.02 - add method  toString()(Yixiao Chen) 
 */
public class Hazard extends Occupant
{
    //class constants
    private static final int FATAL = -1;
    private static final String HAZARD_REPRESENTATION = "۩";
    
    // instance variables - replace the example below with your own
    private String longDescription;
    private double impact;
    

    /**
     * Construct a hazard with known attributes
     * @param location, the position of the hazard in the cave world
     * @param name
     * @param longDescription
     * @param impact
     */
    public Hazard(Position location, String name, String longDescription, double impact)
    {
        // initialise instance variables
        super(location, name);
        this.longDescription = longDescription;
        this.impact = impact;
    }

    /**
     * Check if this hazard is fatal
     * @return true if the hazard is fatal
     */
    public boolean isFatal()
    {
        return this.impact == FATAL;
    }

    /**
     * Get descriptive string
     * @return longDescription
     */
    public String getLongDescription()
    {
        return this.longDescription;
    }

    /**
     * Get the impact value of this hazard
     * @return impact 
     */
    public double getImpact()
    {
        return this.impact;
    }

    /**
     *@returns a String representation of a Hazard
     */
    @Override
    public String getRepresentation()
    {
        return HAZARD_REPRESENTATION;
    }
    
   
     
     /**
      * To representation the item detail in the item list and play list
      */ 
    @Override
    public String toString()
    {
       String representation = getName() + " (Damage: " + getImpact() + "; " + getLongDescription() + ")";
       return representation;
    }
}
