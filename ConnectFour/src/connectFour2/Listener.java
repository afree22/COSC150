package connectFour2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

/* Listener.java implements ActionListener
 * This class ensures that the proper thing happens 
 * when a button is pressed.
 * This class does a lot of the work for the game.
 * 
 */

public class Listener implements ActionListener {
	// JButtons from ControllerUI that can be clicked
	private JButton[] columns;
	private JButton save, reset, load;
	private ControllerUI ui2;
	// ints for Rows and Columns
	private int rowNum = 6;
	private int colNum = 7;

	// Constructor for Listener
	// establishes values for variables based on
	// values from ControllerUI class
	public Listener(ControllerUI ui) // needs access to ControllerUI
	{
		// using the getter functions from ControllerUI
		// to set values for variables
		columns = ui.getColumns();
		save = ui.getSave();
		reset = ui.getReset();
		load = ui.getLoad();
		ui2 = ui;
	}

	// This function overrides the actionPerformed
	// It determines which button was pressed
	// then it executes an action appropriately
	@Override
	public void actionPerformed(ActionEvent e) {
		int currentPlayer;
		int startRow;
		Boolean found;
		String nameWinner;

		for (int i = 0; i < colNum; i++) { // for loop iterates through the columns

			// if statement selected the column where the button was pressed
			if (e.getSource() == columns[i]) {
				startRow = 0;
				found = false;

				// while loop searches through the rows for the
				// lowest empty square of selected column where the button was pressed
				while (startRow < rowNum && !found) {
					// If statement finds the lowest empty square in the column
					if (ui2.getStatus(startRow, i) == 0) {
						// sees if successfully played in column
						found = true; // exit while loop when found = true

						currentPlayer = whoseTurn(); // determine which player is playing

						// set the status of the square so that it is no longer marked as a blank spot
						ui2.setStatus(startRow, i, currentPlayer);

						// bg has values of ui2's BoardGraphicsManager (using function in ConrollerUI)
						BoardGraphicsManager bg = ui2.getBoardGraphics();

						// repaints board so that circle appears
						bg.repaint();

						// determine if there is a winner
						int w = winner(startRow, i);

						if (w != -1) { // see if someone has won
							if (w == 1) {
								nameWinner = ui2.getNameP1();
							} else {
								nameWinner = ui2.getNameP2();
							}
							// inform players someone has won
							JOptionPane.showMessageDialog(null, nameWinner + " " + " wins!");
							// reset the board so that players can't keep playing after someone has won
							resetGame();
							bg.repaint(); // repaint board so it's blank
						}
					}
					// increments to search next spot up in column to see if it is empty
					startRow++;
				}

				// There are no empty spaces in the selected column
				if (!found) {
					JOptionPane.showMessageDialog(null, "Please select a column with an empty spot");
				}

			}

		}

		// This happens when save button is pressed
		if (e.getSource() == save) {
			// call method to write names of players and status of squares to a file
			try {
				storeGame();
			} catch (IOException ex) {
				System.out.println("Error reading file");
			}
		}

		// This happens when reset button is pressed
		else if (e.getSource() == reset) {

			// sets value of Tiles to 0 -- indicating blank Tiles
			resetGame();
			// redraw the board
			BoardGraphicsManager bgraphics = ui2.getBoardGraphics();
			bgraphics.repaint();
		}

		// This happens when load button is pressed
		else if (e.getSource() == load) {
			// call method to load game most recently saved
			loadGame();
			// repaint board to reflect changes in status
			BoardGraphicsManager bg1 = ui2.getBoardGraphics();
			bg1.repaint();
		}

	}

	// This function will reset the status of the squares to 0
	// in effect, reseting the game
	public void resetGame() {
		// iterates through the board
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				// sets all of the squares to empty
				ui2.setStatus(i, j, 0);
			}
		}

	}

	// This function returns the integer
	// of the player whose turn it is
	public int whoseTurn() {
		int count = 0;
		int playerNumber = 0;
		// for loop to collect count
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				if (ui2.getStatus(i, j) == 0) {
					count++;
				}
			}
		}
		count = count % 2; // find the remainder of count

		if (count == 0) { // even number of moves so Player2 just went
			playerNumber = 1; // playerNumber 1 goes next
		} else { // odd number of moves so Player1 just went
			playerNumber = 2; // playerNumber 2 goes next
		}
		return playerNumber;
	}


	//This function stores the game
	// It writes the names of the players and the status of 
	// each square into a text file
	public void storeGame() throws IOException {
		File fout = new File("temp.txt");
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		// write player names to text file
		bw.write(ui2.getNameP1());
		bw.newLine();
		bw.write(ui2.getNameP2());
		bw.newLine();

		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				// write status of square to text file
				bw.write(Integer.toString(ui2.getStatus(i, j)));
			}
			bw.newLine();
		}
		bw.close();
	}
	
	
	//This function loads the most recently stored game
	// It reads in the names of the players and the status of 
	// each square from the text file and sets the values appropriately
	public void loadGame() {

		
		String filename = "temp.txt"; // The name of the file to open.
		String line; //used to read in lines from file

		try {
			// open the file
			File f = new File(filename);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			// read in and set name for P1
			line = br.readLine();
			if (line != null) {
				ui2.setNameP1(line);
			}

			// read in and set name for P2
			line = br.readLine();
			if (line != null) {
				ui2.setNameP2(line);
			}

			int val = 0;
			// Iterate through board
			// read in value and set the status for each square
			for (int i = 0; i < rowNum && br.ready(); i++) {
				line = br.readLine();
				for (int j = 0; j < colNum; j++) {
					val = Integer.parseInt("" + line.charAt(j));
					ui2.setStatus(i, j, val);
				}
			}
			br.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		} catch (NoSuchElementException n) {
		}
	}

	// This function will return an integer to signify the winner
	// 1 for P1 -- 2 for P2 --- or -1 if no winner
	// to do this is checks all the squares around it to see if it is 
	// part of a winning pattern
	public int winner(int row, int col) {
		int win = -1; // assume no winner

		int playerID = ui2.getStatus(row, col); //1 for P1, 2 for P2

		/*Check to see if the piece won across the row
		 * Need to do 4 separate checks as  the piece 
		 * can be the first, second, third or forth.
		 */
		// this is when the piece is the leftmost piece in a winning row
		if (col + 3 < colNum) // make sure won't go out of bounds
		{
			if (playerID == ui2.getStatus(row, col + 1) && playerID == ui2.getStatus(row, col + 2)
					&& playerID == ui2.getStatus(row, col + 3)) {
				win = playerID;
			}
		}

		// the piece is the second in a winning row
		if (col + 2 < colNum && col - 1 >= 0) // make sure won't go out of bounds
		{
			if (playerID == ui2.getStatus(row, col + 1) && playerID == ui2.getStatus(row, col + 2)
					&& playerID == ui2.getStatus(row, col - 1)) {
				win = playerID;
			}
		}

		// the piece is the third in a row
		if (col + 1 < colNum && col - 2 >= 0) // make sure won't go out of bounds
		{
			if (playerID == ui2.getStatus(row, col + 1) && playerID == ui2.getStatus(row, col - 1)
					&& playerID == ui2.getStatus(row, col - 2)) {
				win = playerID;
			}
		}

		// this is when the piece is the rightmost piece in a winning row
		if (col - 3 >= 0) // make sure won't go out of bounds
		{
			if (playerID == ui2.getStatus(row, col - 1) && playerID == ui2.getStatus(row, col - 2)
					&& playerID == ui2.getStatus(row, col - 3)) {
				win = playerID;
			}
		}

		
		/*Check to see if the piece won in a column
		 * Since columns are created from the ground up
		 * The last piece played will always be the top piece
		 * So only one check is required
		 */
		// this happens when the piece is the top piece
		if (row - 3 >= 0) // make sure won't go out of bounds
		{
			if (playerID == ui2.getStatus(row - 1, col) && playerID == ui2.getStatus(row - 2, col)
					&& playerID == ui2.getStatus(row - 3, col)) {
				win = playerID;
			}
		}

		
		/* Now check to see if the piece won in a diagonal
		 * there are four cases for a diagonal going "up" from left to right and
		 * there are also four cases for a diagonal going "down" from left to right
		 * because the piece could be first, second, third, or last in either the
		 * "up" or "down" diagonal
		 */
		
		// at bottom of "up" diagonal
		if (row + 3 < rowNum && col + 3 < colNum) {
			if (playerID == ui2.getStatus(row + 1, col + 1) && playerID == ui2.getStatus(row + 2, col + 2)
					&& playerID == ui2.getStatus(row + 3, col + 3)) {
				win = playerID;
			}
		}

		// second in "up" diagonol
		if (row - 1 >= 0 && row + 2 < rowNum && col + 2 < colNum && col - 1 >= 0) {
			if (playerID == ui2.getStatus(row - 1, col - 1) && playerID == ui2.getStatus(row + 1, col + 1)
					&& playerID == ui2.getStatus(row + 2, col + 2)) {
				win = playerID;
			}
		}

		// third in "up" diagonal
		if (row - 2 >= 0 && row + 1 < rowNum && col + 1 < colNum && col - 2 >= 0) {
			if (playerID == ui2.getStatus(row - 1, col - 1) && playerID == ui2.getStatus(row - 2, col - 2)
					&& playerID == ui2.getStatus(row + 1, col + 1)) {
				win = playerID;
			}
		}

		// top of "up" diagonal
		if (row - 3 >= 0 && col - 3 >= 0) {
			if (playerID == ui2.getStatus(row - 1, col - 1) && playerID == ui2.getStatus(row - 2, col - 2)
					&& playerID == ui2.getStatus(row - 3, col - 3)) {
				win = playerID;
			}
		}

		// at bottom of "down" diagonal
		if (row + 3 < rowNum && col - 3 >= 0) {
			if (playerID == ui2.getStatus(row + 1, col - 1) && playerID == ui2.getStatus(row + 2, col - 2)
					&& playerID == ui2.getStatus(row + 3, col - 3)) {
				win = playerID;
			}
		}

		// second in "down" diagonol
		if (row - 2 >= 0 && row + 1 < rowNum && col + 2 < colNum && col - 1 >= 0) {
			if (playerID == ui2.getStatus(row + 1, col - 1) && playerID == ui2.getStatus(row - 1, col + 1)
					&& playerID == ui2.getStatus(row - 2, col + 2)) {
				win = playerID;
			}
		}

		// third in "down" diagonol
		if (row - 1 >= 0 && row + 2 < rowNum && col + 1 < colNum && col - 2 >= 0) {
			if (playerID == ui2.getStatus(row + 1, col - 1) && playerID == ui2.getStatus(row + 2, col - 2)
					&& playerID == ui2.getStatus(row - 1, col + 1)) {
				win = playerID;
			}
		}

		// top of "down" diagonal
		if (row - 3 >= 0 && col + 3 < colNum) {
			if (playerID == ui2.getStatus(row - 1, col + 1) && playerID == ui2.getStatus(row - 2, col + 2)
					&& playerID == ui2.getStatus(row - 3, col + 3)) {
				win = playerID;
			}
		}

		// All of the possible ways to score by entering a piece have been checked
		// If the person scored their playerID was stored in win
		// If the person did not score win still contains -1
		return win;
	}

}
