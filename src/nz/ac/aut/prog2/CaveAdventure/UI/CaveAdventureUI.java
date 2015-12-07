/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.prog2.CaveAdventure.UI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import nz.ac.aut.prog2.CaveAdventure.Interface.CavePanel;
import nz.ac.aut.prog2.CaveAdventure.model.Cave;
import nz.ac.aut.prog2.CaveAdventure.model.CaveGame;
import nz.ac.aut.prog2.CaveAdventure.model.Direction;
import nz.ac.aut.prog2.CaveAdventure.model.Dynamite;
import nz.ac.aut.prog2.CaveAdventure.model.Item;
import nz.ac.aut.prog2.CaveAdventure.model.Occupant;
import nz.ac.aut.prog2.CaveAdventure.model.Player;

/**
 * The Class is to create a interface which can be played though the button.
 * Users can use the game menu and press the new game to start a new one or
 * press the quit to quit the game
 *
 * User can use the direction button to move the player and use the pick up
 * button to pick up the items if there are items on the position that the
 * player in.
 *
 * User can use the drop button to drop the selected item and use the throw
 * button to throw the dynamite which can explorer the rock. If user pick up the
 * dynamite. There will have a timer to count. Once the timer goes to zero, the
 * player will die.
 *
 * User will have 100 energy at the beginning. Each step will cost 5 energy.
 * When player goes into the hazard, 20 energy will be reduced. If the hazard is
 * fatal. The player will die
 *
 * To win the game, player should collect two keys.
 *
 * @author Yxiao Chen-1272398
 * @version 28/05/2013
 */
public class CaveAdventureUI extends javax.swing.JFrame {

    private CaveGame game;
    private CavePanel cavePanel;
    private Player player;
    private Timer time;
    private Cave cave;

    /**
     * The constructor is the initialise the data and initialise the timer,
     * panel and the button
     *
     * @param newGame read the data
     */
    public CaveAdventureUI(CaveGame newGame) {
        this.game = newGame;
        this.player = game.getPlayer();
        this.cave = game.getCurrentCave();
        initComponents();
        //set the panel to the centre
        setLocationRelativeTo(null);
        // Create a new panel
        createCaveWorldPanel();
        //set up the timer
        setupTimer();
        throwDyname.setVisible(false);
        timePro.setVisible(false);
        update();
    }

    /**
     * This method is to create a new cave world
     */
    private void createCaveWorldPanel() {

        // integer the rows of the world
        int rows = game.getWorld().getNumRows();

        // integer the columns of the world
        int columns = game.getWorld().getNumColumns();

        // Remove the olf world
        caveWorldPanel.removeAll();

        //Create a new cave world
        caveWorldPanel.setLayout(new java.awt.GridLayout(rows, columns));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                CavePanel co = new CavePanel(game, row, col);
                caveWorldPanel.add(co);

            }
        }
    }

    /**
     * This method is to update the data when there is any change when the
     * player plays the game.
     */
    private void update() {

        //Set the space of the item that the player can carried which can be
        //more clearly to player.
        this.carrySpace.setText("Item carried : " + player.getNumItemsCarried() + "/3");

        //If the east of player is wall or there is a rock fall, the button will be enable 
        EButton.setEnabled(game.isPlayerCanMove(Direction.EAST));

        //If the north of player is wall or there is a rock fall, the button will be enable 
        NButton.setEnabled(game.isPlayerCanMove(Direction.NORTH));

        //If the south of player is wall or there is a rock fall, the button will be enable 
        SButton.setEnabled(game.isPlayerCanMove(Direction.SOUTH));

        //If the west of player is wall or there is a rock fall, the button will be enable 
        WButton.setEnabled(game.isPlayerCanMove(Direction.WEST));

        for (Component component : caveWorldPanel.getComponents()) {
            cavePanel = (CavePanel) component;
            cavePanel.update();

        }

        //set the player's energy to visiable.
        energyBar.setValue((int) game.getPlayerEnergyLevel());

        DefaultListModel model = new DefaultListModel();
        ItemList.setModel(model);
        Cave currectCave = game.getCurrentCave();
        for (Occupant occupant : currectCave.getOccupants()) {
            if (!(occupant instanceof Player)) {
                model.addElement(occupant);
            }
        }
        
         //This is to show the rock position to the player
          if(game.hasRockfall(Direction.NORTH))
        {
            model.addElement(("RockFall at " +"North"));
        }
          else if(game.hasRockfall(Direction.SOUTH))
          {
              model.addElement(("RockFall at "+ "South"));
            }
          else if(game.hasRockfall(Direction.EAST))
          {
              model.addElement(("RockFall at " + "East"));
          }
          else if(game.hasRockfall(Direction.WEST))
          {
              model.addElement(("RockFall at "+ "West"));
          }
        //without choosing the item, the pick up button should be enable.
        pickUp.setEnabled(false);

        //add the item to the Item list.
        model = new DefaultListModel();
        playerItem.setModel(model);
        for (Item item : player.getItems()) {
            model.addElement(item);
        }

        //without choosing the item, the drop item should be enable.
        dropItem.setEnabled(false);

        //If the player has not enough energy, there is a message to tell the user
        //that player has no energy
        if (!(player.hasEnergyToMove())) {
            messageNoEnergy();
        } 
        
        //If the player is dead, there is message to show that player is dead and 
        //let the user to choose restart ot quit the game
        else if (!(player.isAlive())) {
            messagePlayerDead();
        }
        
        //If the player has already collected two keys, there is a messgae to 
        // show the user wins the game
        else if (game.isWin()) {
            messagePlayerWin();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        caveWorldPanel = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        movePanel = new javax.swing.JPanel();
        NButton = new javax.swing.JButton();
        WButton = new javax.swing.JButton();
        SButton = new javax.swing.JButton();
        EButton = new javax.swing.JButton();
        throwDyname = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ItemList = new javax.swing.JList();
        pickUp = new javax.swing.JButton();
        playerDetail = new javax.swing.JPanel();
        carrySpace = new javax.swing.JLabel();
        energyBar = new javax.swing.JProgressBar();
        playerItem = new javax.swing.JList();
        dropItem = new javax.swing.JButton();
        timePro = new javax.swing.JProgressBar();
        loseGame = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        newGameBar = new javax.swing.JMenuItem();
        exitGameBar = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        instructionMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cave Adventure");

        caveWorldPanel.setLayout(new java.awt.GridLayout(1, 0));

        controlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        movePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Direction"));

        NButton.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        NButton.setText("N");
        NButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NButtonActionPerformed(evt);
            }
        });

        WButton.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        WButton.setText("W");
        WButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WButtonActionPerformed(evt);
            }
        });

        SButton.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        SButton.setText("S");
        SButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SButtonActionPerformed(evt);
            }
        });

        EButton.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        EButton.setText("E");
        EButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EButtonActionPerformed(evt);
            }
        });

        throwDyname.setText("Throw");
        throwDyname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                throwDynameMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout movePanelLayout = new javax.swing.GroupLayout(movePanel);
        movePanel.setLayout(movePanelLayout);
        movePanelLayout.setHorizontalGroup(
            movePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movePanelLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(WButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(movePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(movePanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(movePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(146, 146, 146))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, movePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(throwDyname, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        movePanelLayout.setVerticalGroup(
            movePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(movePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(throwDyname, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Items & Hazard"));

        ItemList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ItemList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ItemListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ItemList);

        pickUp.setText("Pick Up");
        pickUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pickUpMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(movePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pickUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pickUp)
                .addGap(31, 31, 31)
                .addComponent(movePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        carrySpace.setText("Item Carried");

        energyBar.setForeground(new java.awt.Color(0, 102, 153));
        energyBar.setMaximum((int) Player.MAX_ENERGY);
        energyBar.setMinimum((int) Player.MIN_ENERGY);
        energyBar.setBorder(javax.swing.BorderFactory.createTitledBorder("Energy"));
        energyBar.setStringPainted(true);

        playerItem.setBorder(javax.swing.BorderFactory.createTitledBorder("Item List"));
        playerItem.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        playerItem.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                playerItemValueChanged(evt);
            }
        });

        dropItem.setText("Drop");
        dropItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dropItemMouseReleased(evt);
            }
        });

        timePro.setMaximum(Dynamite.COUNT_DOWN_TIME/1000);
        timePro.setMinimum(0);
        timePro.setValue(Dynamite.COUNT_DOWN_TIME/1000);
        timePro.setBorder(javax.swing.BorderFactory.createTitledBorder("Time rest"));
        timePro.setStringPainted(true);

        loseGame.setText("Direct lose the Game");
        loseGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                loseGameMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout playerDetailLayout = new javax.swing.GroupLayout(playerDetail);
        playerDetail.setLayout(playerDetailLayout);
        playerDetailLayout.setHorizontalGroup(
            playerDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(playerDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(energyBar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carrySpace)
                    .addComponent(playerItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dropItem, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(timePro, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loseGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        playerDetailLayout.setVerticalGroup(
            playerDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerDetailLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(energyBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(timePro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(playerItem, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(carrySpace)
                .addGap(18, 18, 18)
                .addComponent(dropItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loseGame)
                .addGap(26, 26, 26))
        );

        jMenuBar1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(90, 27));

        gameMenu.setText("Game");
        gameMenu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        newGameBar.setText("Start New Game");
        newGameBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBarActionPerformed(evt);
            }
        });
        gameMenu.add(newGameBar);

        exitGameBar.setText("Exit Game");
        exitGameBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitGameBarActionPerformed(evt);
            }
        });
        gameMenu.add(exitGameBar);

        jMenuBar1.add(gameMenu);

        helpMenu.setText("Help");
        helpMenu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        instructionMenu.setText("Instruction");
        instructionMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instructionMenuActionPerformed(evt);
            }
        });
        helpMenu.add(instructionMenu);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(caveWorldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(playerDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(caveWorldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The method is to start new game if the player choose the new game
     * @param evt 
     */
    private void newGameBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameBarActionPerformed


        CaveGame game = new CaveGame();
        final CaveAdventureUI ui = new CaveAdventureUI(game);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.setVisible(true);
            }
        });
        restoreTime();
        dispose();

    }//GEN-LAST:event_newGameBarActionPerformed

    /**
     * This method is to quit the game if the player choose the quir button
     * @param evt 
     */
    private void exitGameBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitGameBarActionPerformed
        dispose();
    }//GEN-LAST:event_exitGameBarActionPerformed

    /**
     * This method is to show the instruction of the game 
     * @param evt 
     */
    private void instructionMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructionMenuActionPerformed
        instruction();
    }//GEN-LAST:event_instructionMenuActionPerformed

    /**
     * This method is to move the playe to east if the player press the east button
     * @param evt 
     */
    private void EButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EButtonActionPerformed
        game.playerMove(Direction.EAST);

        update();
    }//GEN-LAST:event_EButtonActionPerformed

    /**
     * This method is to move the playe to north if the player press the north button
     * @param evt 
     */
    private void NButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NButtonActionPerformed
        game.playerMove(Direction.NORTH);

        update();
    }//GEN-LAST:event_NButtonActionPerformed

    /**
     * This method is to move the playe to west if the player press the west button
     * @param evt 
     */
    private void WButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WButtonActionPerformed
        game.playerMove(Direction.WEST);

        update();
    }//GEN-LAST:event_WButtonActionPerformed

    /**
     * This method is to move the playe to south if the player press the south button
     * @param evt 
     */
    private void SButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SButtonActionPerformed
        game.playerMove(Direction.SOUTH);

        update();
    }//GEN-LAST:event_SButtonActionPerformed

    /**
     * This method is to pick up the items if the player click the pick up button
     * @param evt 
     */
    private void pickUpMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pickUpMouseReleased
        if (pickUp.isEnabled() && !player.hasDynamite()) {
            Item item = (Item) ItemList.getSelectedValue();
            game.playerPickUp(item);
            if (item instanceof Dynamite) {
                timePro.setVisible(true);
                throwDyname.setVisible(true);
                time.start();
            }
        }
        update();
    }//GEN-LAST:event_pickUpMouseReleased

    /**
     * This method is to show the item to user
     * @param evt 
     */
    private void ItemListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ItemListValueChanged

        if (!evt.getValueIsAdjusting()) {
            pickUp.setEnabled(false);
            Object obj = ItemList.getSelectedValue();
            if (obj instanceof Occupant) {
                Occupant occ = (Occupant) ItemList.getSelectedValue();
                if (occ instanceof Item) {
                    Item item = (Item) occ;
                    if (item.okToCarry()) {
                        if (player.getNumItemsCarried() < player.getMaxItems()) {
                            pickUp.setEnabled(true);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_ItemListValueChanged

    /**
     * This method is the show the item in the player list if player picked the item up
     * @param evt 
     */
    private void playerItemValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_playerItemValueChanged

        if (!evt.getValueIsAdjusting()) {
            Object object = playerItem.getSelectedValue();
            if (object != null) {
                dropItem.setEnabled(true);
                if (!(object instanceof Dynamite)) {
                    throwDyname.setSelected(false);
                }
            }
        }
    }//GEN-LAST:event_playerItemValueChanged

    /**
     * This method is the drop the item when the player choose the item to drop. 
     * @param evt 
     */
    private void dropItemMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dropItemMouseReleased

        if (dropItem.isEnabled()) {
            Item item = (Item) playerItem.getSelectedValue();

            game.playerDrop(item);
            if (item instanceof Dynamite) {
                restoreTime();
            }
            update();
        }
    }//GEN-LAST:event_dropItemMouseReleased

    /**
     * This method is to throw the dynamite 
     * @param evt 
     */
    private void throwDynameMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_throwDynameMouseReleased

        Object[] obj = {"North", "South", "West", "East"};
        int response = JOptionPane.showOptionDialog(null, "Choose the direction \n To throw the dynamite", "Cave Adventure", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, obj, obj[0]);
        if (response == 0 && (game.hasRockfall(Direction.NORTH))) {
            game.throwDynamite(Direction.NORTH);
            restoreTime();

        } else if (response == 1 && (game.hasRockfall(Direction.SOUTH))) {
            game.throwDynamite(Direction.SOUTH);
            restoreTime();

        } else if (response == 2 && (game.hasRockfall(Direction.WEST))) {
            game.throwDynamite(Direction.WEST);
            restoreTime();

        } else if (response == 3 && (game.hasRockfall(Direction.EAST))) {

            game.throwDynamite(Direction.EAST);
            restoreTime();


        } else {
            JOptionPane.showMessageDialog(null, "Can not throw the dynamite to the place\nwhich does not have rock fall", "Warning", JOptionPane.OK_OPTION, null);
        }


        update();
    }//GEN-LAST:event_throwDynameMouseReleased

    private void loseGameMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loseGameMouseReleased
        player.reduceEnergyLevel(player.getEnergyLevel());
        update();
    }//GEN-LAST:event_loseGameMouseReleased

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EButton;
    private javax.swing.JList ItemList;
    private javax.swing.JButton NButton;
    private javax.swing.JButton SButton;
    private javax.swing.JButton WButton;
    private javax.swing.JLabel carrySpace;
    private javax.swing.JPanel caveWorldPanel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton dropItem;
    private javax.swing.JProgressBar energyBar;
    private javax.swing.JMenuItem exitGameBar;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem instructionMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loseGame;
    private javax.swing.JPanel movePanel;
    private javax.swing.JMenuItem newGameBar;
    private javax.swing.JButton pickUp;
    private javax.swing.JPanel playerDetail;
    private javax.swing.JList playerItem;
    private javax.swing.JButton throwDyname;
    private javax.swing.JProgressBar timePro;
    // End of variables declaration//GEN-END:variables

    /**
     * This method is to restart the time if the player throw the dynamite.
     */
    private void restoreTime() {
        //Stop the timer.
        time.stop();
        //set the timer to the beginning
        timePro.setValue(Dynamite.COUNT_DOWN_TIME / 1000);
        //disviiable the time process bar
        timePro.setVisible(false);
        
        throwDyname.setVisible(false);
        throwDyname.setSelected(false);
    }

    /**
     * This method is to show the instruction to user.
     */
    private void instruction() {
        String instructions = "\n Watch out every step that you move."
                + "\nDon't forget to pick the detonate to explore the rock so that you can go through."
                + "\nThe fatal hazard will kill you. "
                + "\n\nTo win the Game, you must collect the only two keys"
                + "\n\nIf the edge of the cave become red means there is a rock fall."
                + "\n\nTips: \n۩ = Hazard \n▄ = Item\n۞ = detonate\n●o● = Player\n"
                + "\n\nControls: \nUse mouse click to play. \nClick N, S, W and E to move."
                + "\n\nCollect items using pick up item, and item will show on the collect bar."
                + "\n\nRED grid displays a Hazard."
                + "\nGREEN grid displays the item position"
                + "\nBLUE grid display player.";
        JOptionPane.showMessageDialog(this, instructions,"Instructions",JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * This method is to display the no energy message.
     */
    private void messageNoEnergy() {

        Object[] obj = {"Restart", "Quit Game"};
        int response = JOptionPane.showOptionDialog(null, showNoEnergyMessage(), "Cave Adventure", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, obj, obj[0]);
        responseChoice(response);
    }

    /**
     * This method is to display the player dead message.
     */
    private void messagePlayerDead() {

        Object[] obj = {"Restart", "Quit Game"};
        int response = JOptionPane.showOptionDialog(null, showPlayerDeadMessage(), "Cave Adventure", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, obj, obj[0]);
        responseChoice(response);
    }

    /**
     * This method is to display the player win message.
     */
    private void messagePlayerWin() {

        Object[] obj = {"Restart", "Quit Game"};
        int response = JOptionPane.showOptionDialog(null, showPlayeWin(), "Cave Adventure", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, obj, obj[0]);
        responseChoice(response);
    }

    /**
     * This method is to display the different result according to the uesr' choice.
     * @param response read the player's choice
     */
    private void responseChoice(int response) {
        
        //If user chooses the restart button, restart the game.
        if (response == 0) {
            newGameBarActionPerformed(null);
        } 
        //If the user chooses the quit button, quit the game 
        else if (response == 1) {
            dispose();
        }
    }

    /**
     * This method is to return the no energy message
     * @return message the message will be displayed when player has no energy
     */
    private String showNoEnergyMessage() {
        String message = "_________________________________________________"
                + "\n"
                + "\n                                  Do not have enough energy"
                + "\n"
                + "\n                                            Game Over."
                + "\n           You can press the Restart button to start new game"
                + "\n                You can press the Quit to quit game."
                + "\n"
                + "________________________________________________"
                + "\n\n";
        return message;
    }

    /**
     * This method is to show the player dead message if the player is dead
     * @return message the message will be displayed when player dead
     */
    private String showPlayerDeadMessage() {

        String message = "_________________________________________________"
                + "\n"
                + "\n                                           You are dead"
                + "\n"
                + "\n                                            Game Over."
                + "\n           You can press the Restart button to start new game"
                + "\n                You can press the Quit to quit game."
                + "\n"
                + "________________________________________________"
                + "\n\n";
        return message;
    }

    /**
     * This method is to show the player win message if the player is win
     * @return message the message will be displayed when player win
     */
    private String showPlayeWin() {
        String message = "_________________________________________________"
                + "\n"
                + "\n                                Congratulations! you win the game"
                + "\n"
                + "\n           You can press the Restart button to start new game"
                + "\n                You can press the Quit to quit game."
                + "\n"
                + "________________________________________________"
                + "\n\n";
        return message;
    }

    /**
     * set up the timer
     */
    private void setupTimer() {
        //set up the timer for dynamite
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = timePro.getValue();
                timePro.setValue(value - 1);
                if (value == 0) {
                    update();
                }
            }
        });
    }
}
