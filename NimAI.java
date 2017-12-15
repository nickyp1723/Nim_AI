/*******************
 * Nick Pinero
 * Spring 2017
 *
 * AI Game Client
 * This project is designed to link to a basic Game Server to test
 * AI-based solutions.
 * See README file for more details.
 ********************/

package cad.ai.game;

import java.util.Random;

/***********************************************************
 * The AI system for a NimGame.
 *   Most of the game control is handled by the Server but
 *   the move selection is made here - either via user or an
 *   attached AI system.
 ***********************************************************/
public class NimAI implements AI {
    public NimGame game;  // The game that this AI system is playing.
    Random ran;
    
    public NimAI() {
        game = null;
        ran = new Random();// New Random number generator.
    }

    public void attachGame(Game g) {
        game = (NimGame) g;
    }
    
    /*****************************
     * Returns the Move as a String "R,S"
     *    R=Row
     *    S=Sticks to take from that row
     *****************************/
    public synchronized String computeMove() {
        if (game == null) {// If the game there isn't a game set, then Error is reported.
            System.err.println("CODE ERROR: AI is not attached to a game.");
            return "0,0";
        }
        /* ***************************
         * Variables that keep track of the amount of sticks 
         * in each row and the amount of sticks that the AI
         * is supposed to take.
         * 
         * getStateAsObject() = Checks the state of the object.
         * ***************************/
        int[] rows = (int[]) game.getStateAsObject();
        int nimsum = 0;  
        int take = 0; 
        
        /* ***************************
         * For loop to find the nimsum, so that the AI can
         * make the correct move.
         * ***************************/
        for (int i = 0; i < rows.length; i++) {  
         nimsum = (nimsum ^ rows[i]); 
        }
        System.out.println("This is my nimsum: " + nimsum);
	   // Print out the current nimsum.
        
        /* ***************************
         * For loop to find the row that the AI should 
         * take sticks from.
         * ***************************/
        for (int i = 0; i < rows.length; i++) {
        	if ((nimsum ^ rows[i]) < rows[i]) {// If the (nimsum xor rows[i]) is less than the amount of sticks in the row.
        		take = rows[i] - (nimsum^rows[i]);// Calculate the amount of sticks to take.
        		return i + "," + take;// Return the row and amount of sticks to take.
        	}
         } 

        int r = 0;// Create an int variable to keep the amount of rows.
        
        while (rows[r] == 0){// Find the next row that is not zero.
        	r = (r + 1) % rows.length;
        }
        
        take = ran.nextInt(rows[r]) + 1;// Take a random number of sticks from row[r].
        return r + "," + take;// Return the row and the amount of sticks.
    }        

    /************************************
     * Has been implemented: Class that will make the game null,
     * which means the game has ended.
     * 
     * Has not been implemented: Inform the AI who the 
     * winner is result is either (H)ome win or (A)way win.
     ************************************/
    public synchronized void postWinner(char result) {
        // This AI doesn't care.  No learning being done...
        game = null;  // No longer playing a game though.
    }
}
