/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.prog2.CaveAdventure.Interface;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import nz.ac.aut.prog2.CaveAdventure.model.Cave;
import nz.ac.aut.prog2.CaveAdventure.model.CaveGame;
import nz.ac.aut.prog2.CaveAdventure.model.Direction;
import nz.ac.aut.prog2.CaveAdventure.model.Position;

/**
 * This is to create a panel which will representation each cave in the cave
 * world
 *
 * @author Yixiao Chen - 1272398
 * @version 30/05/2013
 */
public class CavePanel extends javax.swing.JPanel {

    private CaveGame game;
    private int row;
    private int column;
    private static boolean useLighting;
   

    /**
     * Constructor to initialise the data
     *
     * @param game input the txt data into the panel to draw the panel
     * @param rows The row of the panel
     * @param columns The columns of the panel
     */
    public CavePanel(CaveGame game, int rows, int columns) {
        // Initialise the data
        this.game = game;
        this.row = rows;
        this.column = columns;
        initComponents();
        //set the back ground to the black at the beginning
        lblContents.setBackground(Color.BLACK);
        
        update();
    }

    /**
     * If there is a rock in the Direction of the player the rock side fo the
     * cave will become red to reminde the player that there is a rock fall.
     */
    public void showRockFall() {
        setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        // set the north side to red when there is rock fall on the north
        if ((game.hasRockfall(Direction.NORTH))) {
            setBorder(new MatteBorder(15, 0, 0, 0, Color.RED));
        }
        // set the east side to red when there is rock fall on the east
        if ((game.hasRockfall(Direction.EAST))) {
            setBorder(new MatteBorder(0, 0, 0, 15, Color.RED));
        }
        // set the south side to red when there is rock fall on the south
        if ((game.hasRockfall(Direction.SOUTH))) {
            setBorder(new MatteBorder(0, 0, 15, 0, Color.RED));
        }
        // set the west side to red when there is rock fall on the west
        if ((game.hasRockfall(Direction.WEST))) {
            setBorder(new MatteBorder(0, 15, 0, 0, Color.RED));
        }
    }

    /**
     * This method is to update the cave when the player start the game.
     */
    public void update() {

        Position position = new Position(game.getWorld(), row, column);
        Cave cave = game.getWorld().getCave(position);
        showRockFall();
        //change the player position to gray
        if (cave.isLit() && !useLighting) {
            lblContents.setText(cave.getOccupantStringRepresentation());
            lblContents.setOpaque(false);
            lblContents.setBackground(Color.DARK_GRAY);

            //If the cave has item. Change the cave color to green
            if (cave.hasItem()) {
                setBackground(Color.GREEN);
            } //If the cave has hazard. Change the cave color to pink
            else if (cave.hasHazard()) {
                setBackground(Color.PINK);
            } //If the cave has hazard which is fatal. Change the cave colot to red
            else if (cave.hasFatalHazard()) {
                setBackground(Color.RED);
            }
        }
        //If the player has not enter to the cave, the color of the cave should be black and unknow
        else {
            lblContents.setText("?");
            lblContents.setBackground(Color.black);
            lblContents.setOpaque(true);
            setBorder(new BevelBorder(BevelBorder.RAISED));
        }
    }
    
    
    //For light the cave
    public static void toggleLighting() {
        useLighting = !useLighting;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblContents = new javax.swing.JLabel();

        setBorder(new javax.swing.border.MatteBorder(null));
        setLayout(new java.awt.BorderLayout());

        lblContents.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblContents.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lblContents, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblContents;
    // End of variables declaration//GEN-END:variables
}
