package nz.ac.aut.prog2.CaveAdventure.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
/**
 *  Player represents a player of CaveAdventure
 * 
 * @author Jacqui Whalley
 * @version v1.0 - 2013.02: Created
 * 
 * @verson 2013.6.1 add method
 *                      - getMaxItem()
 *                      - setMaxItem()
 *                      - getItems()  (Yixiao Chen)
 * Extended for Assignment Stage 2 Anne Philpott
 */

public class Player extends Occupant implements ActionListener 
{
    //class constants
    public static final double MIN_ENERGY = 0.0;
    private static final double MOVE_ENERGY = 5.0;
    //add the max energy
    public static final double MAX_ENERGY = 100.0;
    private static final String PLAYER_REPRESENTATION = "(●o●)";

    // instance variables - replace the example below with your own
    private int maxItems;
    private String name;
    private boolean alive;
    private double energyLevel;
    private Set<Item> items;

    
    /**
     * Constructor for objects of class Player
     * @ param location, specifies the position of the player in the cave world
     * @ param name, player's name
     * @ param energyLevel, Player has this much energy
     * @ param maxItems, Player can carry a maximum of this many items at one time.
     */
    public Player(Position location, String name, double energyLevel, int maxItems)
    {
        super(location, name);
        this.maxItems = maxItems;
        this.energyLevel = energyLevel;
        this.alive = true;
        this.items = new HashSet<>(); 

    }
    
   /**
    * Check whether player is alive
    * @return true if player alive.
    */
    public boolean isAlive()
    {
        return this.alive;
    }
    
    /**
     * Kill the player
     */
    public void kill()
    {
        this.alive = false;
    }
    
    /**
     * Has player enough energy to move
     * @return true if player has enough energy to move
     */
    public boolean hasEnergyToMove()
    {
        return this.energyLevel >= MOVE_ENERGY;
    }

    /**
     * Increase the energyLevel of player
     * @param increase, size of increase
     */
    public void increaseEnergyLevel(double increase)
    {
        if(increase >0)
        {         
            this.energyLevel += increase; 
            //if energy bigger than max energy
            if(this.energyLevel > MAX_ENERGY)
            {
                this.energyLevel = MAX_ENERGY;
            }
        }

    }

    /**
     * Decrease the energyLevel by reduction
     * @param reduction
     */
    public void reduceEnergyLevel(double reduction)
    {
        if(reduction > 0)
        { 
            this.energyLevel -= reduction;
            if(this.energyLevel < MIN_ENERGY)
            {
                this.energyLevel = 0; 
            }
        }    

    }
    
    /**
     * Move the player to a new position if player has enough energy to move
     * @param newPosition the player should move to
     * @return true if player moves.
     */
    public boolean move(Position position)
    {
        boolean moved = false;
        if(hasEnergyToMove()&& position != null){
            setPosition(position);
            reduceEnergyLevel(MOVE_ENERGY);
            
            for(Item item : items)
            {
                item.setPosition(position);
            }
            moved = true;
        }
        return moved;
    }
    
    /**
     * get the max number of item can be carried
     * @return the number of items
     */
    public int getMaxItems()
    {
        return this.maxItems;
    }
    
    /**
     * set the max number of items can be carried
     * @param max 
     */
    public void setMaxItems(int max)
    {
        this.maxItems = max;
    }
    
    /**
     * get the items size
     * @return 
     */
     public Item[] getItems()
    {
        return items.toArray(new Item[items.size()]); 
    }
    
    /**
     * @return number of items being carried
     */
    public int getNumItemsCarried()
    {
        return this.items.size();
    }

    /**
     * Get the EnergyLevel for the player.
     * @return energyLevel
     */
    public double getEnergyLevel()
    {
        return this.energyLevel;
    }


    /**
     * Pick up an item if player does not already have max items or be carrying that item,
     * and if that item can be carried.
     * @param item to pickup
     * @return true if item picked up
     */
    public boolean pickUp(Item item)
    {
        boolean itemOK = item != null && item.okToCarry();
        boolean canPickUp = itemOK && this.items.size()< this.maxItems;
        if(canPickUp )
        {
            canPickUp = this.items.add(item);
        }
        if(canPickUp && item instanceof Dynamite)
        {
            Dynamite dynamite = (Dynamite) item;
            dynamite.startExplosionTimer(this);
        }
        return canPickUp;
    }

    /**
     * Drop an item if player is carrying it.
     * @param item to drop
     */
    public boolean drop(Item item)
    {
        boolean dropped = this.items.remove(item);
        if (dropped && item instanceof Dynamite)
        {
             Dynamite dynamite = (Dynamite) item;
            dynamite.deactivate();
        }
        return dropped;
    }

    /**
     * @param item to check for
     * @return true if player has item
     */
    public boolean has(Item item)
    {
        return this.items.contains(item);
    }
    
    /**
     * @return true if player has at least one piece of dynamite
     */
    public boolean hasDynamite()
    {
        boolean result = false;
        for(Item item :items)
        {
            if(item instanceof Dynamite) 
            {
                result = true;
            }
        }
        return result;
    }  
    
    /**
     * Action listener to respond to the dynamite timer
     */
    @Override
    public void actionPerformed(ActionEvent e) { 
        //Dynamite has exploded while player is carrying it, so player is dead 
        kill();
        
        //Dynamite no longer exists. Remove it.
        removeDynamite();
    }
    
    public void removeDynamite()
    {
        Dynamite dynamite = null;
        for(Item item :items)
        {
            if(item instanceof Dynamite && dynamite == null) 
            {
               dynamite = (Dynamite) item;
            }
        }
        if(this.items.remove(dynamite) && dynamite != null)
        {
            dynamite.deactivate();
        }
      //  this.items.remove(dynamite);
    }
    
    /**
     * 
     * @return String representation of the player.
     */
    @Override
    public String getRepresentation() 
    {
        return PLAYER_REPRESENTATION;
    }

}
