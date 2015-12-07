package nz.ac.aut.prog2.CaveAdventure.model;


/**
 * A class to repesent the world in which the game is played.
 * @author aphilpot
 * @version Stage 1
 * 2013:03 Refactored to include rockfalls
 *      - added methods to
 *        - identify the cave adjacent to a rockfall
 *        - add a new rockfall to a cave
 *        - get the geographically opposite (reverse) direction eg N->S, S->N
 */
public class CaveWorld {

    private int numRows;
    private int numColumns;
    private Cave[][] caveGrid;


   /**
    * Initial CaveWorld constructor.
    * @param numColumns
    * @param numRows
    */
    public CaveWorld( int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.caveGrid = new Cave[numRows][numColumns];
        initialiseCaves();
    }

    /**
     * Get the value of numRows
     * @return the value of numRows
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * Get the value of numColumns
     * @return the value of numColumns
     */
    public int getNumColumns() {
        return this.numColumns;
    }

    /**
     * Access a Cave in the cave world
     * @param postion of the Cave in the CaveWorld
     * @return a Cave at the specified position
     */
    public Cave getCave(Position position)
    {
        return this.caveGrid[position.getRow()][ position.getColumn()];
    }

    
    /**
     * Adds an occupant to a specified cave
     * @param location of the cave that the occupant should be added to 
     * @param occupant
     * @return true if occupant successfully added.
     */
    public boolean addOccupant(Position location, Occupant occupant){
        int row = location.getRow();
        int col = location.getColumn();
        assert (0 <= row)&&(row < this.numRows);
        assert (0 <= col)&&(col < this.numColumns);
        boolean success = caveGrid[row][col].addOccupant(occupant);
        if(success){ //update the occupants position
            occupant.setPosition(location);
        }
        return success;
    }
    
    /**
     * Add a new rockfall to a cave
     * Records rockfall in both caves on either side of the rockfall
     * @param position of cave with rockfall
     * @param direction which direction cave is blocked  
     */
    public void addRockfall(Position position, String directionText){
        Direction rockfallDirection = null;
        for( Direction d : Direction.values() ){
            String s = d.getAbbreviatedText();
            if(s.equals(directionText)){
                rockfallDirection = d;
            }
        }
        int row = position.getRow();
        int col = position.getColumn(); 
        if(rockfallDirection!=null){ 
            caveGrid[row][col].addRockFall(rockfallDirection);
           
            //Also need to add to the cave on the other side for convenience
            Position adjacentPosition = getAdjacentPosition(position, rockfallDirection);
            Direction reverseDirection = getReverseDirection(rockfallDirection);
            caveGrid[adjacentPosition.getRow()][adjacentPosition.getColumn()].addRockFall(reverseDirection);
        }         
    }
    
    /**
     * Remove a rockfall from an exit
     * @para position of cave with rockfall, direction of rockfall
     */
    public void removeRockfall(Position position,Direction rockfallDirection)
    {
        int row = position.getRow();
        int col = position.getColumn(); 
        if(rockfallDirection!=null){ 
            caveGrid[row][col].removeRockFall(rockfallDirection);
           
            //Also need to remove from the cave on the other side for convenience
            Position adjacentPosition = getAdjacentPosition(position, rockfallDirection);
            Direction reverseDirection = getReverseDirection(rockfallDirection);
            caveGrid[adjacentPosition.getRow()][adjacentPosition.getColumn()].removeRockFall(reverseDirection);
        } 
        
    }

     /**
     * Identify the adjacent cave in a particular direction
     * @param position of cave
     * @param direction of adjacent cave
     * @return position of adjacent cave or null if poutside world
     */
    public Position getAdjacentPosition(Position position,Direction direction)
    {
        int newRow = position.getRow();
        int newColumn = position.getColumn();
        Position  newPosition = null;
        switch (direction) {
            case NORTH: newRow--; break;
            case EAST: newColumn++; break;
            case SOUTH: newRow++; break;
            case WEST: newColumn--; break;
        }
        try
        {
            newPosition = new Position(this,newRow, newColumn);
        }
        catch(IllegalArgumentException e)
        {
           //Nothing to do apart from catch. Null position will be returned.
        }
        return newPosition;
    }

//============================== Debugging methods  =============================    
    /**
     * Produces an image of the created game grid on standard output
     * This exists mainly for debugging purposes.
     */
    public void draw() {
        for (int row = 0; row < this.numRows; row++) {
            
            //Draw caves northern boundary row
           for (int column = 0; column < this.numColumns; column++) {
                Cave cave = caveGrid[row][column];
                cave.drawNorthBoundary();
            }
            System.out.println();
            
            // Draw cave interiors row
            for (int column = 0; column < this.numColumns; column++) {
                Cave cave = caveGrid[row][column];
                cave.draw();
            }
            System.out.println("|");
        }
        System.out.println("--------------------------");
    }

    //============================== Private methods  =============================
    /**
     * Populates the game grid with caves
     */
    private void initialiseCaves() {
        for (int row = 0; row < this.numRows; row++) {
            for (int column = 0; column < this.numColumns; column++) {
                Cave cave = new Cave();
                caveGrid[row][column] = cave;
            }
        }
    }

      /**
     * @param direction to reverse
     * @return reverse direction
     */
    private Direction getReverseDirection(Direction direction)
    {
       Direction reverse = null;
       switch (direction) {
            case NORTH: reverse = Direction.SOUTH; break;
            case EAST: reverse = Direction.WEST; break;
            case SOUTH: reverse = Direction.NORTH; break;
            case WEST: reverse = Direction.EAST; break;
        }
        return reverse;
    } 




}
