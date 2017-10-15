package connectFour2;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

/* BoardGraphicsManager is responsible for painting the board.
 * It draws the lines in the correct locations to make the grid.
 * It determines if each square is filled or not filled 
 * and draws the circles for the players with their names accordingly.
 * BoardGraphicsMangers sets the size of the board
 * based on the number of squares in the board and
 * the number of rows or columns.
 * 
 */

public class BoardGraphicsManager extends JPanel {
	// private variables
	private int numRow = 6;
	private int numCol = 7;
	private Game game1; // has access to Game class
	private String name1; // responsible for player names
	private String name2; // responsible for player names

	// Constructor for BoardGraphicsManager
	// receives Game and JFrame as parameters
	public BoardGraphicsManager(Game game, JFrame frame) {
		game1 = game;
		frame.getContentPane().add(this, BorderLayout.CENTER);

	}

	// This function sets the dimension of the board
	// It is called in UIManager
	@Override
	public Dimension getMinimumSize() {

		Dimension boardSize = new Dimension(720, 720);
		return boardSize;
	}

	// This function paints the board
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int space = 100; // space between lines (size of squares)
		int startX = 10; // boarder
		int startY = 10; // boarder

		// draw lines for columns
		for (int i = 0; i < numCol + 1; i++) {
			g.drawLine(startX, startY, startX, numRow * space + startY);
			startX += space; // move x to draw lines for columns
		}

		startX = startY = 10;
		// draw lines for rows
		for (int j = 0; j < numRow + 1; j++) {
			g.drawLine(startX, startY, numCol * space + startX, startY);
			startY += space; // move y to draw lines for rows
		}

		// starting coordinates in square
		int startCoord = 30;
		// provides starting value based on the square size and the number of squares
		int rowSize = 540;
		// spaces to jump between circles
		int oSpace = 100;

		// nested for loops to iterate through board
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				// check status of square
				if (game1.gameGetStatus(i, j) == Tile.EMPTY) {
					// do not need to draw a circle

				} else if (game1.gameGetStatus(i, j) == Tile.PLAYER1) { // if the square status if PLAYER1
					// draw black circle and write name
					g.setColor(Color.BLACK);
					g.fillOval(oSpace * j + startCoord, rowSize - oSpace * i, 50, 50);
					g.setColor(Color.BLUE);
					g.drawString(name1, oSpace * j + startCoord, rowSize - oSpace * i);

				} else if (game1.gameGetStatus(i, j) == Tile.PLAYER2) {// if the square status if PLAYER2
					// draw red circle and write name
					g.setColor(Color.RED);
					g.fillOval(oSpace * j + startCoord, rowSize - oSpace * i, 50, 50);
					g.setColor(Color.BLUE);
					g.drawString(name2, oSpace * j + startCoord, rowSize - oSpace * i);

				}

			}
		}
	}

	// getters for the player names
	public String getNameP1() {
		return name1;
	}

	public String getNameP2() {
		return name2;
	}

	// setters for player names
	public void setNameP1(String s1) {
		name1 = s1;
	}

	public void setNameP2(String s2) {
		name2 = s2;
	}

}
