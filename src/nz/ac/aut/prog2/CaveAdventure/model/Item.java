package nz.ac.aut.prog2.CaveAdventure.model;

/**
 * Class represents an item that can be found in a cave.
 *
 * @author aphilpot
 * @version 1.0 2011.02: Created
 * @version 1.1 2013.02: Modified to include Position (jlw)
 * @version  1.1 2013.06.02: add method toString() (Yixiao Chen)
 */
public class Item extends Occupant {
    //class constants

    private static final String ITEM_REPRESENTATION = "▄ ";
    // instance variables - replace the example below with your own
    private String longDescription;
    private int id;
    private boolean canBeCarried;
    private double energyContribution;

    /**
     * Construct an item with known attributes
     *
     * @param location, position in cave world of item
     * @param name
     * @param longDescription
     * @param ID
     * @param canBeCarried
     * @param energyContribution
     */
    public Item(Position position, String name, String longDescription, int id, boolean canBeCarried, double energyContribution) {
        // initialise instance variables
        super(position, name);
        this.longDescription = longDescription;
        this.id = id;
        this.canBeCarried = canBeCarried;
        this.energyContribution = energyContribution;
    }

    /**
     * Long description for item.
     *
     * @return longDescription
     */
    public String getLongDescription() {
        return this.longDescription;
    }

    /**
     * Get the item energy contribution
     *
     * @return energyContribution
     */
    public double getEnergyContribution() {
        return this.energyContribution;
    }

    /**
     * Can this item be carried?
     *
     * @return canBeCarried
     */
    public boolean okToCarry() {
        return this.canBeCarried;
    }

    /**
     * @returns a string representation of an item
     */
    @Override
    public String getRepresentation() {
        return ITEM_REPRESENTATION;
    }

    /**
     *  To representation the hazard detail in the item list and play list
     * @return 
     */
    @Override
    public String toString() {
        String representation = getName() + " (" + getLongDescription() + ")";
        return representation;
    }
}
