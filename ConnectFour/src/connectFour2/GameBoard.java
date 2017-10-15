package connectFour2;
/* GameBoard is the bulk of the backend of Connect4.
 * It keeps track of the status of each tile in the board, 
 * using an enum.
 * 
 * It's constructor initializes the tiles to empty.
 * It has a getTileStatus and a setTileStatus function so that
 * other classes can access the status and set the status.
 * 
 * 
 */
public class GameBoard {

	// 2D array that holds that status of the Tiles
	private Tile[][] status;
	// number of rows and columns
	private int numRow = 6;
	private int numCol =7;


	//constructor makes a 6 by 7 2D array
	// initializes all of the Tiles as EMPTY 
	// (no player has played in them when board first appears)
	public GameBoard() {
		status = new Tile[numRow][numCol];
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numCol; j++) {
				status[i][j] = Tile.EMPTY;
			}

		}

	}

	// returns a Tile
	// returns Tile so that can get the status
	public Tile getTileStatus(int row, int col) {
		return status[row][col];

	}

	// sets the status for a given square [row][col] depending on the value
	// the value corresponds to an enum
	public void setTileStatus(int row, int col, int val) {

		if (val == 0) {
			status[row][col] = Tile.EMPTY;
		}
		if (val == 1) {
			status[row][col] = Tile.PLAYER1;
		}
		if (val == 2) {
			status[row][col] = Tile.PLAYER2;
		}
	}

}
