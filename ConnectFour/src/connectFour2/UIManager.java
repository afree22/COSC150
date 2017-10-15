package connectFour2;

import javax.swing.JFrame;

/*  UIManager connects the classes of the user interface
 *  such as ControllerUI and BoardGraphicsManager.
 *  UIManager creates a JFrame that functions as the user interface
 *  and sends it to boardGraphics and UIManager so that those
 *  classes can add the proper components (such as squares, circles and buttons)
 *  to the JFrame.
 */
public class UIManager {

	// Private variables
	private BoardGraphicsManager boardGraphics;
	private JFrame frame;

	// Constructor for UIManager
	public UIManager(Game game) {
		// creates JFrame
		frame = new JFrame("Connect Four");

		// JFrame will exit when close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// creates BoardGraphicsManager with access to game and frame
		// BoardGraphicsManager draws the squares and the circles
		boardGraphics = new BoardGraphicsManager(game, frame);

		// Packs frame
		frame.pack();

		// Gets the dimensions that were established in boardGraphics
		// sets the frame size to those dimensions
		frame.setSize(boardGraphics.getMinimumSize());

		// creates ControllerUI
		// ControllerUI controls the user Interface so it needs
		// access to the frame, game, and boardGraphics
		new ControllerUI(frame, game, boardGraphics);

		// makes the frame visible
		// at this point the frame has been passed to boardGraphics and controllerUI
		// so they should have don what they needed to do to the frame
		frame.setVisible(true);

	}

}
