package nz.ac.aut.prog2.CaveAdventure.model;


import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent the a Cave in the CaveWorld
 * @author aphilpot
 * @version v1.0 - Stage 1
 * @version v1.1 - 2013.02: Modified for Assignment 1 (JLW))
 * @version v1.2 - 2013.02: Added functionality to add occupants and rockfalls 
 * - added methods:
 *      - hasOccupant (JLW)
 *      - addOccupant (JLW)
 *      - isOccupied (JLW)
 * - added field to store the cave exits in this cave blocked by rockfalls (AP)
 * - added methods:
 *      - canExit (AP)
 *      - add rockfall (AP)
 *      - remove rockfall (JLW)
 *  @version1.3 Added functionality for dynamite and re;lated testing (AP)
 *  
 *  @version 1.4  Add method
 *                           - getOccupants
 *                 change the getOccupantStringRepresentation() to public   
 *                  -(Yixiao Chen) - 1272398- 29/05/2013
 */
public class Cave {

    //class constants
    private static int MAX_OCCUPANTS = 3;
    private String CAVE_STRING = " ";
    
    
    //instance variables (fields)
    private Set<Occupant> occupants;
    private boolean[] exitAvailable;

    public Cave() 
    {
        
        occupants = new HashSet<>();
        exitAvailable = new boolean[Direction.values().length];
        for ( Direction d : Direction.values() )
        {
            exitAvailable[d.ordinal()] = true;
        }
    }

    
    /**
     * Adds a rockfall to this cave.
     * @param fallDirection the direction (cave exit) that is now blocked by a rockfall
     */
    public void addRockFall(Direction fallDirection)
    {
        this.exitAvailable[fallDirection.ordinal()] = false;
    }
      
    
    /**
     * Removes a rockfall from this cave.
     * @param fallDirection the direction (cave exit) that is now blocked by a rockfall
     */
    public void removeRockFall(Direction fallDirection)
    {
        this.exitAvailable[fallDirection.ordinal()] = true;
    }
   
    /**
     * Is it possible to exit this cave in the specified direction?
     * @param direction to check
     * @return true if this exit is not blocked by a rackfall
     */
    public boolean canExit(Direction direction)
    {
        return this.exitAvailable[direction.ordinal()];
    }
    
    /**
     * Create a occupant arraylist
     * @return the size
     */
    public Occupant[] getOccupants()
    {
        return occupants.toArray(new Occupant[occupants.size()]); 
    }
      
    /**
     * Adds an occupant, if there is not already one and
     * if cave currently has fewer than the maximum number of occupants.
     * @param occupant the new occupant
     * @return true if occupant was added, false if not
     */
    public boolean addOccupant(Occupant occupant)
    {
        boolean validNewOccupant = occupant != null;
        boolean enoughRoom = this.occupants.size() < MAX_OCCUPANTS;
        if (validNewOccupant && enoughRoom) {
            validNewOccupant = occupants.add(occupant);
        }
        return validNewOccupant && enoughRoom;

    }

    /**
     * Removes an occupant from a cave
     * @param occupant to remove
     * @return true if occupant was removed
     */
    public boolean removeOccupant(Occupant occupant)
    {
        boolean validOccupant = occupant != null;
        boolean removed = false;
        if (validOccupant) {
            removed = occupants.remove(occupant);
        }
        return removed;

    }
    
    /**
     * Does this cave have a given occupant?
     *
     * @return true if so.
     */
    public boolean hasOccupant(Occupant occupant) {
        return this.occupants.contains(occupant);
    }

    /**
     * Does this cave have a fatal hazard?
     * @return true if so.
     */
    public boolean hasFatalHazard(){
        boolean hasFatal = false;
        for(Occupant occupant : occupants){
            if(occupant instanceof Hazard){
                Hazard hazard = (Hazard)occupant;
                if(hazard.isFatal()){
                   hasFatal = true;
                }
            }
        }
        return hasFatal;
    }
    
    /**
     * Does this cave have a any hazards?
     * @return true if so.
     */
    public boolean hasHazard(){
        boolean result = false;
        for(Occupant occupant : occupants){
            if(occupant instanceof Hazard){
               result = true;
            }
        }
        return result;
    }  
    
    /**
     * Does this cave have a any items?
     * @return true if so.
     */    
    public boolean hasItem(){
        boolean result = false;
        for(Occupant occupant : occupants){
            if(occupant instanceof Item){
               result = true;
            }
        }
        return result;
    }
    
    /**
     * Get an item from this cave
     * @return the last item in the list of occupants, or null if no item in cave.
     */
    public Item getItem(){
        Item result = null;
        for(Occupant occupant : occupants){
            if(occupant instanceof Item){
               result = (Item) occupant;
            }
        }
        return result;
    }
        
    /**
     * Get dynamite from this cave
     * @return dynamite or null if none in cave.
     */
    public Dynamite getDynamite(){
        Dynamite result = null;
        for(Occupant occupant : occupants){
            if(occupant instanceof Dynamite){
               result = (Dynamite) occupant;
            }
        }
        return result;
    }
    
    /**
     * Checks if the cave is occupied.
     * @param occupant to check for
     * @return true if the cave is occupied, false if not
     */
    public boolean isOccupied()
    {
        return this.occupants.size() > 0;
    }

    /**
     * @return the total impact of nonFatal hazards in this cave.
     */
    public double getTotalHazardEnergyImpact()
    {
        double impactTotal = 0.0;
        if(!hasFatalHazard())
        {
            for(Occupant occupant : occupants){
                if(occupant instanceof Hazard){
                    Hazard hazard = (Hazard)occupant;
                    impactTotal += hazard.getImpact();
                }
            }            
        }
        else
        {
            impactTotal = -1.0;
        }
        return impactTotal;
    }
    
    /**
     * @return tru if cave is lit because player is present.
     */
    public boolean isLit()
    {
        return hasPlayer();
    }
    
    /**
     * Check if player is in this cave
     */
    private boolean hasPlayer()
    {
        boolean playerPresent = false;
        for(Occupant occupant : occupants){
            if(occupant instanceof Player){
                playerPresent = true;
            }
        }        
        return playerPresent;
    }
    
    /**
     * Gets a string representation of the cave, e.g., for printing to the screen.
     * 
     * @return Representation of cave
     */
    
    public String getRepresentation()
    {
        return CAVE_STRING;
    }

    /**
     * @return string that combines strings for all occupants
     */
    public String getOccupantStringRepresentation(){
        String result = "";
        for(Occupant occupant : occupants){
            result += occupant.getRepresentation();
        }
        return result;
    }
   
    /*=====Drawing methods to display game in terminal window for debugging purposes====*/
    
    /**
     * Draw north boundary including any rockfalls
     */
    public void drawNorthBoundary(){
        String output = "--";
        if(canExit(Direction.NORTH)){
            output+= "----";
        }
        else {
            output += "XX--";
        }
        System.out.print(output);
    }
    
      /**
     * Produces an image of this Cave on standard output
     * This exists mainly for debugging purposes.
     */
    public void draw() {
        
        final int COL_WIDTH = 6;
        final String SPACE = " ";
        String output = "| ";
        
        if(!canExit(Direction.WEST)){
            output = "X ";
        }
        
        if(isOccupied())
        {
            output += getOccupantStringRepresentation();
            
        }
       
        // Pad with spaces to keep column widths equal
        while(output.length()<COL_WIDTH)
        {
            output += SPACE;
        }
        
        System.out.print(output);
    }
    
}
