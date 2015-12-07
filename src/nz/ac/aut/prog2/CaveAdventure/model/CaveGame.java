package nz.ac.aut.prog2.CaveAdventure.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the class that knows the Cave game rules and state and enforces those
 * rules.
 *
 * @author aphilpot
 * @version Stage 1
 * @version v1.1 - Refactored to allow for rockfalls 2013.02: Added method setup
 * rockfalls (jlw) 2013.02: Extended initialiseDataFromFile method to accomodate
 * rockfall functionality(jlw)
 * 
 * @version 1.2 add method - isWin() (Yixiao Chen)
 *                          - getPlayer() (Yixiao Chen)
 *                          - getCaves()  (Yixiao Chen)
 *                          - getWorld()  (Yixiao Chen)
 *                          - isPlayerCanMove() (Yixiao Chen)
 *              change the method
 *                          - setUpItems() create a key object(Yixiao Chen)
 *                          Date: 01/06/2013
 */
public class CaveGame {

    //constants
    private static final String FILENAME = "CaveData.txt";
    private static final String DYNAMITENAME = "Dynamite";
    //add by Yixiao Chen-1272398-29/05/2013
    private static final String KEY = "key";
    
    private CaveWorld caves;
    private int numRows;
    private int numColumns;
    private Player player;
    private Cave currentCave;
    //Count the number of keys. add by Yixiao Chen-1272398-29/05/2013
    private int keyNumber;

    /**
     * A new instance of CaveGame that reads data from "CaveData.txt"
     */
    public CaveGame() {
        keyNumber = 0;
        initialiseDataFromFile(FILENAME);
    }

    /**
     * Add a win method to let the player win the game
     * @return true if the player has picked up all keys
     */
    public boolean isWin() {
        boolean win = false;
        int number = 0;
        for (Item item : player.getItems()) {
            if (item instanceof Key) {
                number++;
            }
        }
        if (number == keyNumber) {
            win = true;
        }
        return win;
    }

    /**
     * @return current cave.
     */
    public Cave getCurrentCave() {
        return this.currentCave;
    }

    /**
     * Accessor for player energy level
     *
     * @return player energy level.
     */
    public double getPlayerEnergyLevel() {
        return player.getEnergyLevel();
    }

    /**
     * @return whether player is alive
     */
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    /**
     * @return the player detail 
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * public access for player Pickup.
     *
     * @param item to pickup
     */
    public void playerPickUp(Item item) {
        if (currentCave.hasOccupant(item)) {
            boolean result = player.pickUp(item);
            if (result) {
                // Need to remove item from cave as player now has it.
                currentCave.removeOccupant(item);
            }
        }
    }

    /**
     * public access for player Drop.
     *
     * @param item to drop
     */
    public void playerDrop(Item item) {
        if (player.has(item)) {
            player.drop(item);
        }
    }

    /**
     * @return the cave 
     */
    public CaveWorld getCaves() {
        return this.caves;
    }

    /**
     * Throw dynamite to cause or remove a rockfall
     *
     * @param Direction in which to throw.
     */
    public void throwDynamite(Direction direction) {
        //Check that player alive and has dynamite to throw.
        if (player.isAlive() && player.hasDynamite()) {
            player.removeDynamite();
            if (currentCave.canExit(direction)) {
                //No rockfall to explode, so creates one instead
                caves.addRockfall(player.getPosition(), direction.getAbbreviatedText());
            } else {
                // Will remove rockfall
                caves.removeRockfall(player.getPosition(), direction);
            }
        }

    }

    /**
     * Is there a rockfall in this direction?
     *
     * @param direction to check
     */
    public boolean hasRockfall(Direction direction) {
        return !currentCave.canExit(direction);
    }

    /**
     * Draw the caveGrid to standard output. This exists for debugging purposes.
     */
    public void drawCaves() {
        caves.draw();
    }

    /**
     * @return the cave world
     */
    public CaveWorld getWorld() {
        return caves;
    }

    /**
     * Move the player
     *
     * @param direction of move
     * @return true if player was moved.
     */
    public boolean playerMove(Direction direction) {
        boolean moved = false;
        //Can only move if live player with enough energy and no rockfall in that direction
        if (player.isAlive() && player.hasEnergyToMove() && currentCave.canExit(direction)) {
            //work out where to move to
            Position newPosition = caves.getAdjacentPosition(player.getPosition(), direction);
            moved = player.move(newPosition);
            //update the cave occupant lists
            if (moved) {
                Cave newCave = caves.getCave(newPosition);
                currentCave.removeOccupant(player);
                // Update currentCave
                currentCave = newCave;
                currentCave.addOccupant(player);

                //Have any hazards effected player?
                if (currentCave.hasFatalHazard()) {
                    player.kill();
                } else if (currentCave.hasHazard()) {
                    player.reduceEnergyLevel(currentCave.getTotalHazardEnergyImpact());
                }
            }
        }
        return moved;
    }

    /**
     * Move the player if there is no wall and no rock
     * @param direction the direction of moving 
     * @return true if the player has successeful moved
     */
    public boolean isPlayerCanMove(Direction direction) {
        boolean canMove = false;
        Position position = player.getPosition().calculateDestination(direction);
        if (position != null) {
            //If the player is alive, has enough energy to move and the direction cen exit
            if (player.isAlive() && player.hasEnergyToMove() && currentCave.canExit(direction)) {
                canMove = true;
            }
        }
        return canMove;
    }

    /**
     * At this stage this method assumes that the data file is correct and just
     * throws an exception or ignores it if it is not. We will consider how to
     * handle this better later. Don't worry too much about the exception
     * handling at this stage either. We cover that later in the course.
     *
     * @param fileName
     */
    private void initialiseDataFromFile(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));
            input.useDelimiter("\\s*,\\s*");

            //First create the correct number of caves
            numRows = input.nextInt();
            numColumns = input.nextInt();
            caves = new CaveWorld(numRows, numColumns);

            //Read and setup the player
            setUpPlayer(input);

            //Read and setup the items 
            setUpItems(input);

            //Read and set up hazards
            setUpHazards(input);

            //Read and set up rock falls-CHALLENGE TASK -Stage 1
            setUpRockfalls(input);

            input.close();
        } catch (FileNotFoundException e) {
            System.err.println(" Unable to find file :" + fileName);
        } catch (IOException e) {
            System.err.println("Problem encountered processing file.");
        }

    }

    /**
     * Reads player data and create player
     *
     * @param input
     */
    private void setUpPlayer(Scanner input) {
        String name = input.next();
        double energy = input.nextDouble();
        int canCarry = input.nextInt();
        int positionRow = input.nextInt();
        int positionColumn = input.nextInt();

        Position location = new Position(caves, positionRow, positionColumn);
        player = new Player(location, name, energy, canCarry);
        caves.addOccupant(location, player);
        currentCave = caves.getCave(location);

    }

    /**
     * Creates items listed in the file and adds them to the CaveWorld At this
     * stage any attempts to add more than the maximum occupants to a single
     * cave are ignored. We will need to fix this later.
     *
     * @param input
     */
    private void setUpItems(Scanner input) {
        int numItems = input.nextInt();
        for (int i = 0; i < numItems; i++) {
            String name = input.next();
            String desc = input.next();
            int id = input.nextInt();
            boolean canCarry = input.nextBoolean();
            int energy = input.nextInt();
            int positionRow = input.nextInt();
            int positionColumn = input.nextInt();
            Position location = new Position(caves, positionRow, positionColumn);

            //create the required object
            Item item = null;
            if (name.equals(DYNAMITENAME)) {
                item = new Dynamite(location, name, desc, id);
            } 
            //create the win object 
            else if (name.equals(KEY)) {
                item = new Key(location, name, desc, id);
                keyNumber++;
            } else {
                item = new Item(location, name, desc, id, canCarry, energy);
            }
            caves.addOccupant(location, item);
        }
        if (keyNumber > player.getMaxItems()) {
            player.setMaxItems(numRows);
        }
    }

    /**
     * Creates hazards listed in the file and adds them to the CaveWorld At this
     * stage any attempts to add more than the maximum occupants to a single
     * cave are ignored. We will need to fix this later.
     *
     * @param input
     */
    private void setUpHazards(Scanner input) {
        int numItems = input.nextInt();
        for (int i = 0; i < numItems; i++) {
            String name = input.next();
            String desc = input.next();
            double impact = input.nextDouble();
            int positionRow = input.nextInt();
            int positionColumn = input.nextInt();
            Position location = new Position(caves, positionRow, positionColumn);
            Hazard hazard = new Hazard(location, name, desc, impact);
            caves.addOccupant(location, hazard);
        }
    }

    /**
     * Creates rockfalls listed in the file and adds them to the CaveWorld This
     * assumes only valid input is received from the file!
     *
     * @param input
     */
    private void setUpRockfalls(Scanner input) {
        int numRockfalls = input.nextInt();
        for (int i = 0; i < numRockfalls; i++) {
            int positionRow = input.nextInt();
            int positionColumn = input.nextInt();
            Position position = new Position(caves, positionRow, positionColumn);
            String direction = input.next();

            caves.addRockfall(position, direction);
        }
    }
}
