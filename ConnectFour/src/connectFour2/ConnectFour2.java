package connectFour2; 
// ^^ this is called connectFour2 because I already
// had a package called connectFour

/* ConnectFour2 owns the layers so that it can 
 * connect the GUI (UIManager) to the backend (Game).
 * It is a relatively simple class. 
 * It starts Game and UIManager.
 * It contains the main function which 
 * creates a new instance of UIManager and Game.
*/

// class is called ConnectFour2 because I already
// had a class called ConnectFour -- wanted to keep
// track of the versions
public class ConnectFour2 {

	public static void main(String[] args) {

		// runs the backend so that the game works
		Game game = new Game();

		// starts the GUI so players can play
		// receives instance of game so that can have access to members
		// The GUI has game so that it will receive logic for the graphics
		new UIManager(game);

	}
}
