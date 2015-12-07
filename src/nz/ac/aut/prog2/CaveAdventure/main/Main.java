/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.prog2.CaveAdventure.main;

import nz.ac.aut.prog2.CaveAdventure.Interface.caveGameIntroduction;

/**
 *This class is to run the interface
 * @author Yixiao Chen-1273298
 * @version  1/06/2013
*/
public class Main {

    public static void main(String[] args) {
        
        //Displaye the introduction window to let the user choose start the game or quit the game
        final caveGameIntroduction introduction = new caveGameIntroduction();
        
        //display the windos
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                introduction.setVisible(true);
            }
        });
    }
}
