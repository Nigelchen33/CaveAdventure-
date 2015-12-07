/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.prog2.CaveAdventure.model;

/**
 *Key is specialised item used to win the game
 * @author Yixiao Chen - 1272398
 * @version 2013.06.01
 */
public class Key extends Item{
   
    //class constants
    private static final String REPRESENTATION = "&&";
    
    /**
     * Constructor for objects of class key
     */
    public Key(Position position, String name, String longDescription,int id)
    {
        super(position, name, longDescription, id, true, 0.0);
    }
    
    /**
  * @returns a string representation of key
  */
    @Override
    public String getRepresentation()
    {
        return REPRESENTATION;
    }
}
