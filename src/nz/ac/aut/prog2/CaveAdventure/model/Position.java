package nz.ac.aut.prog2.CaveAdventure.model;

/**
 * Represents the location of any item in the game.
 * Positions must always be valid for the particular game.
 *
 * @author Stefan Marks & Anne Philpott
 * @version v1.0 - 2012.06: Created
 * @version v1.1 - 2012.06: Modified for Assignment Stage 1 (sm)
 * @version v1.1 - 2012.06: Modified for Cave Adventure Assignment Stage 1 (jlw)
 */

public class Position
{
    // the cave world that this position is in
    private CaveWorld world;
    // the row part of this position
    private int row;
    // the column part of this position
    private int column;
     
    /**
     * Constructor for objects of class Position
     * Can only construct valid positions. Invalid arguments result in exceptions.
     * @param row, row value for positions
     * @param column, column value for positions
     * @param world, the world on which the position is
     */
    public Position(CaveWorld world, int row, int column)
    {
        if ( world == null )
        {
            throw new IllegalArgumentException(
                    "World parameter cannot be null");
        }
        if ( (row < 0) || (row >= world.getNumRows()) )
        {
            throw new IllegalArgumentException(
                    "Invalid row for position (" + row + ")");
        }
        if ( (column < 0) || (column >= world.getNumColumns()) )
        {
            throw new IllegalArgumentException(
                    "Invalid column for position (" + column + ")");
        }
        // parameters are valid -> save
        this.world = world;
        this.row    = row;
        this.column = column;
    }

    /**
     * Gets the row part of the position.
     * 
     * @return the row of this position
     */
    public int getRow()
    {
       return this.row;
    }
    
    /**
     * Gets the col part of the position.
     * 
     * @return the col of this position
     */
    public int getColumn()
    {
       return this.column;
    }   
    
    public Position calculateDestination(Direction direction)
    {
        if ( direction == null )
        {
            throw new IllegalArgumentException(
                    "Direction parameter cannot be null");
        }
        
        Position newPosition = null;
        try
        {
            int newRow = row;
            int newCol = column;
            
            switch ( direction )
            {
                case NORTH  : newRow--; break;
                case EAST  : newCol++; break;
                case SOUTH : newRow++; break;
                case WEST : newCol--; break;
            }
            newPosition = new Position(world, newRow, newCol); 
        }
        catch (IllegalArgumentException e)
        {
            // new position could not be created, so the move is invalid
            // -> do nothing and thereby return null
        }
        
        return newPosition;
    }
    
}
