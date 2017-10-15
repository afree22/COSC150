package connectFour2;

import java.awt.*;
import javax.swing.*;

/* ControllerUI class add buttons to use interface.
 * The buttons in the ControllerUI class are connected to
 * Listener.java (which implements ActionListener), so that
 * the appropriate action is taken when the button is pressed.
 */

public class ControllerUI {

	// private member variables
	private JButton[] cols; // array of JButtons one per column
	private JButton reset, save, load; // JButtons for reset, save, and load features
	private JPanel top; // JPanel for arranging JButtons at the Top of the screen
	private JPanel bottom; // JPanel for arranging JButtons on the Bottom of the screen
	private Game game1;
	private BoardGraphicsManager boardGraphics1;

	public ControllerUI(JFrame frame, Game game, BoardGraphicsManager boardGraphics) {

		// set values for variables
		game1 = game;
		boardGraphics1 = boardGraphics;

		// create JButtons and JPanels
		cols = new JButton[7];
		top = new JPanel();
		bottom = new JPanel();

		// set the layout of the JPanel to GridLayout so JButtons can be placed evenly
		top.setLayout(new GridLayout(1, 7));
		bottom.setLayout(new GridLayout(1, 3));

		for (int i = 0; i < cols.length; i++) {
			cols[i] = new JButton("Drop"); // create the JButtons in col[]
			cols[i].addActionListener(new Listener(this)); // add an actionListener (see Listener.java)
			top.add(cols[i]); // add JButtons to JPanel
		}

		// create JButtons and add actionListeners
		reset = new JButton("Reset");
		reset.addActionListener(new Listener(this));
		save = new JButton("Save");
		save.addActionListener(new Listener(this));
		load = new JButton("Load");
		load.addActionListener(new Listener(this));

		// Add the appropriate buttons to bottom
		bottom.add(reset);
		bottom.add(save);
		bottom.add(load);

		// get player names using JOptionPanes
		boardGraphics1.setNameP1(JOptionPane.showInputDialog("Player 1 - Please enter your name"));
		boardGraphics1.setNameP2(JOptionPane.showInputDialog("Player 2 - Please enter your name"));

		// add JPanels top and bottom to the frame
		frame.getContentPane().add(top, BorderLayout.PAGE_START);
		frame.getContentPane().add(bottom, BorderLayout.PAGE_END);

	}

	// getters for the player names
	public String getNameP1() {
		return boardGraphics1.getNameP1();
	}

	public String getNameP2() {
		return boardGraphics1.getNameP2();
	}

	// setters for player names
	public void setNameP1(String s1) {
		boardGraphics1.setNameP1(s1);
	}

	public void setNameP2(String s2) {
		boardGraphics1.setNameP2(s2);
	}

	// getter for BoardGraphicsManager
	public BoardGraphicsManager getBoardGraphics() {
		return boardGraphics1;
	}

	// these functions allow access to JButtons
	public JButton[] getColumns() {
		return cols;
	}

	public JButton getReset() {
		return reset;
	}

	public JButton getSave() {
		return save;
	}

	public JButton getLoad() {
		return load;
	}

	// getStatus for game -- converts enum into int
	// EMPTY is equivalent to 0 
	// PLAYER1 is equivalent to 1 and PLAYER2 is equivalent to 2
	public int getStatus(int row, int col) {

		if (game1.gameGetStatus(row, col) == Tile.PLAYER1) {
			return 1; // Player1
		} else if (game1.gameGetStatus(row, col) == Tile.PLAYER2) {
			return 2; // Player2
		}

		return 0; // Tile is empty
	}

	// set status for game
	public void setStatus(int row, int col, int val) {
		game1.setTileStatus(row, col, val);
	}

}
