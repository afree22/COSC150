package connectFour2;

/* Game is part of the backend of Connect4
 * It's purpose is to start up GameBoard and
 * to allow other classes to have access to 
 * the necessary variables in GameBoard and
 * an instance of gameBoard itself.
 * 
 * It contains the private variable gameBoard.
 */

public class Game {

	// variable contained in Game
	private GameBoard gameBoard;

	public Game() {
		// the logic required to play
		gameBoard = new GameBoard();

	}

	// access to gameBoard
	public GameBoard getBoard() {
		return gameBoard;
	}

	// access to tileStatus in gameBoard
	public Tile gameGetStatus(int r, int c) {
		return gameBoard.getTileStatus(r, c);
	}

	// sets the tile status in gameBoard -- allows classes
	// that have access to Game to set the tile status
	public void setTileStatus(int r, int c, int val) {
		gameBoard.setTileStatus(r, c, val);
	}

}
