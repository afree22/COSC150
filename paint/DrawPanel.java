package paint;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/*
 * This class handles mouse events and uses them to draw shapes. It contains the
 * LinkedList myShapes which is the shapes drawn on the panel. It has variables
 * for the current shape and setters for the current shape. It also has the
 * shapeMode and the moveType. It has the methods required to save, load, and
 * reset. It has a private inner class MouseHandler which extends MouseAdapter
 * and handles mouse and mouse motion events used for drawing and moving shapes.
 * 
 */
public class DrawPanel extends JPanel {
	private LinkedList<Shape> myShapes; // dynamic stack of shapes // dynamic stack of shapes

	// current Shape variables
	private int currentShapeType; // 0 for line, 1 for rect, 2 for oval, 3 for triangle, 4 for text
	private Shape currentShapeObject; // stores the current shape object
	private Color currentShapeColor; // current shape color

	// Variables required for movement
	private int shapeMode; // the editing mode
	private int moveType; // the type of shape being moved
	private Shape shapeFound; // the shape located at the mouse click that is being moved

	/*
	 * This constructor initializes myShapes. It sets the current shape variables
	 * and move shape variables to default values. It sets up the panel and adds
	 * event handling for mouse events.
	 */
	public DrawPanel() {

		myShapes = new LinkedList<Shape>(); // initialize myShapes dynamic stack

		// Initialize current Shape variables
		currentShapeType = 0;
		currentShapeObject = null;
		currentShapeColor = Color.BLACK;

		// Initialize variables required for movement
		shapeMode = 0;
		moveType = 0;
		shapeFound = null;

		setLayout(new BorderLayout()); // sets layout to border layout; default is flow layout
		setBackground(Color.WHITE); // sets background color of panel to white
		// add(statusLabel, BorderLayout.SOUTH); // adds a statuslabel to the south
		// border

		// event handling for mouse and mouse motion events
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}

	// Calls the draw method for the existing shapes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw the already existing shapes
		ArrayList<Shape> shapeArray = myShapes.getArray();
		for (int counter = shapeArray.size() - 1; counter >= 0; counter--) {
			shapeArray.get(counter).draw(g);
		}

		// draws the current Shape Object if it is not null
		if (currentShapeObject != null)
			currentShapeObject.draw(g);
	}

	// Mutator methods for currentShapeType, currentShapeColor and shapeMode

	// Sets the currentShapeType to the type passed in
	// (0 for line, 1 for rect, 2 for oval, 3 for triangle or 4 for text)
	public void setCurrentShapeType(int type) {
		currentShapeType = type;
	}

	// Sets the currentShapeColor to the Color object passed in (color for current
	// shape)
	public void setCurrentShapeColor(Color color) {
		currentShapeColor = color;
	}

	// sets the shape mode to the integer passed in (0 for drawing shapes and 1 for
	// editing shapes)
	public void setShapeMode(int md) {
		shapeMode = md;
	}

	// remove all shapes in current drawing
	public void clearDrawing() {
		myShapes.makeEmpty(); // this is in the Linked List Class
		repaint();
	}

	// This function saves the current Paint Canvas when the user clicks
	// the save button. It will write the Shape attributes to a specified file
	public void savePaint() throws IOException {
		// ask user for file to load
		String filename = JOptionPane.showInputDialog("Enter file to save shapes");

		File fout = new File(filename);
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		// get array version of list
		ArrayList<Shape> shapeArray = myShapes.getArray(); // in linked list class

		// Go through the shapes in the array
		for (int counter = shapeArray.size() - 1; counter >= 0; counter--) {
			// write the shape type
			Shape tmp = shapeArray.get(counter);
			bw.write(Integer.toString(tmp.getShapeType()) + " ");
			// write the shape color (as a string)
			Color shapeColor = tmp.getColor();
			bw.write(tmp.getColorString(shapeColor) + " ");
			// if shape is a line, rectangle, or oval -- only write two coordinates
			if (tmp.getShapeType() == 0 || tmp.getShapeType() == 1 || tmp.getShapeType() == 2) {
				bw.write(Integer.toString(tmp.getX1()) + " ");
				bw.write(Integer.toString(tmp.getY1()) + " ");
				bw.write(Integer.toString(tmp.getX2()) + " ");
				bw.write(Integer.toString(tmp.getY2()));
			}

			// if the shape is a triangle, write 3 coordinates
			else if (tmp.getShapeType() == 3) {
				bw.write(Integer.toString(tmp.getX1()) + " ");
				bw.write(Integer.toString(tmp.getY1()) + " ");
				bw.write(Integer.toString(tmp.getX2()) + " ");
				bw.write(Integer.toString(tmp.getY2()) + " ");
				bw.write(Integer.toString(tmp.getX3()) + " ");
				bw.write(Integer.toString(tmp.getY3()));
			}

			// if the shape is text, write 1 coordinate and the message
			else if (tmp.getShapeType() == 4) {
				bw.write(Integer.toString(tmp.getX1()) + " ");
				bw.write(Integer.toString(tmp.getY1()) + " ");
				bw.write(tmp.getMessage());
			}
			bw.newLine(); // write the attributes of each shape on a new line
		}

		bw.close();
	}

	// This function asks the user for a file and adds shapes to the linked list
	// using
	// the information from that file and then it repaints the canvas
	public void loadPaint() {
		// ask user for file to load
		String filename = JOptionPane.showInputDialog("Enter file to load shapes");

		try {
			// open the file
			File f = new File(filename);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			// read and process lines
			String line;
			while ((line = br.readLine()) != null) {
				// calls function to create new object for each line
				dealWithLine(line);
			}
			br.close();

		} catch (Exception e) {
			System.out.println("Error opening file");
		}
		repaint();
	}

	// This function receives a line from the input file and handles it
	// by reading in the shapeKey and then calling the constructor of the
	// appropriate shape
	public void dealWithLine(String line) {
		try {
			// starts StringTokenizer
			StringTokenizer st = new StringTokenizer(line);
			// reads in the shapeKey
			int shapeKey = Integer.parseInt(st.nextToken());
			// calls the constructor of the appropriate shape based on the shapeKey
			if (shapeKey == 0) {
				myShapes.addFront(new Line(st));
			} else if (shapeKey == 1) {
				myShapes.addFront(new Rectangle(st));
			} else if (shapeKey == 2) {
				myShapes.addFront(new Oval(st));
			} else if (shapeKey == 3) {
				myShapes.addFront(new Triangle(st));
			} else if (shapeKey == 4) {
				myShapes.addFront(new Text(st));
			} else {
				System.out.println("Error: must enter a valid shape");
			}
		} catch (NoSuchElementException n) {
		} catch (Exception e) {
			System.out.println("error in this line " + e);
		}
	}

	// Private inner class that implements MouseAdapter
	// and does event handling for mouse events.
	private class MouseHandler extends MouseAdapter {
		// mouse coordinates used to create new shape objects
		int pX1 = 0;
		int pX2 = 0;
		int pX3 = 0;
		int pY1 = 0;
		int pY2 = 0;
		int pY3 = 0;

		// bool to determine whether points of triangle exist
		private Boolean pointExists1 = false;
		private Boolean pointExists2 = false;

		// stores the vertex that is modified (1, 2, or 3)
		int vertexModified;

		// This function deals with the GUI whenever the mouse is pressed
		// Lines, rectangles and ovals are created by pressing, dragging, and releasing
		// so they require
		// the mousePressed function to get a set of coordinates and initialize the
		// shape
		// When editing shapes -- also edit by pressing on key points so the
		// mousePressed function is
		// essential
		public void mousePressed(MouseEvent event) {

			// Only draw the shape if shapeMode is 0 (draw mode)
			// see if the shape to draw is a line, rectangle or oval (created by pressing)
			// if it is, then draw a new shape based on the type and color
			// X1,Y1 & X2,Y2 coordinate for the drawn shape are both set to the same X & Y
			// mouse position.
			if (shapeMode == 0) {
				switch (currentShapeType) // 0 for line, 1 for rect, 2 for oval
				{
				case 0: // line
					currentShapeObject = new Line(event.getX(), event.getY(), event.getX(), event.getY(),
							currentShapeColor);
					break;
				case 1: // rectangle
					currentShapeObject = new Rectangle(event.getX(), event.getY(), event.getX(), event.getY(),
							currentShapeColor);
					break;
				case 2: // oval
					currentShapeObject = new Oval(event.getX(), event.getY(), event.getX(), event.getY(),
							currentShapeColor);
					break;
				} // end switch
			}

			// If shapeMode is 1, then in edit mode
			// Iterate through the shapes and see if the point that was pressed is the
			// vertex of any of the existing shapes
			if (shapeMode == 1) {
				// convert the linked list of shapes to an array
				ArrayList<Shape> shapeArray = myShapes.getArray();
				// iterate through the array
				for (int counter = shapeArray.size() - 1; counter >= 0; counter--) {
					// check to see if the coordinates of any of the shapes
					// matches the mouse coordinates
					shapeFound = zatyou(shapeArray.get(counter), event.getX(), event.getY()); // call to zatyou function
					// if a shape is found then break from the loop
					if (shapeFound != null)
						break;
				}
			}
		}// end of mousePressed

		// Function zatyou takes the current Shape in the list and the current mouse
		// coordinates
		// as parameters and returns a shape if one corresponds to the mouse coordinates
		// or null if no shape corresponds to the mouse coordinates
		public Shape zatyou(Shape currentListShape, int mouseX, int mouseY) {
			// Test to see if the mouse coordinates correspond to X1,Y1 or X2, Y2
			int cType = currentListShape.getShapeType();
			// If the shape is a triangle
			if (cType == 3) 
			{
				// check third vertex on triangle to see if it matches the mouse
				double xdif = mouseX - currentListShape.getX3();
				double ydif = mouseY - currentListShape.getY3();
				double dist = Math.sqrt(xdif * xdif + ydif * ydif);
				if (dist < 50) {
					vertexModified = 3; // keep track of which vertex changed
					return currentListShape;
				}
			}
			// if the shape is a triangle, line, Rectangle or Oval,
			// test the second set of coordinates (X2, Y2) to see if it matches the mouse
			if (cType == 3 || cType == 0 || cType == 1 || cType == 2) {
				// check second vertex on triangle and
				// check other end of line
				double xdif = mouseX - currentListShape.getX2();
				double ydif = mouseY - currentListShape.getY2();
				double dist = Math.sqrt(xdif * xdif + ydif * ydif);
				if (dist < 50) {
					vertexModified = 2; // keep track of which vertex changed
					return currentListShape;
				}

			}

			// For all shapes, check the first set of coordinates (X1, Y1),
			// if the shape has not already been found
			double xdif = mouseX - currentListShape.getX1();
			double ydif = mouseY - currentListShape.getY1();
			double dist = Math.sqrt(xdif * xdif + ydif * ydif);
			if (dist < 50) {
				vertexModified = 1; // keep track of which vertex changed
				return currentListShape;
			}

			// if no shape's vertices correspond to the mouse, return null
			return null;

		} // end of zatyou

		// Use method to handle shapes that are created by clicking:
		// applies to the triangle and text
		public void mouseClicked(MouseEvent event) {

			// Only create triangle or text if in draw mode
			if (shapeMode == 0) {
				switch (currentShapeType) // 3 for triangle and 4 for text
				{
				case 3: // Triangle
					// Triangle requires three points, must test to see if each exist
					// and save the points before creating the triangle
					if (pointExists1 == false) {
						// If no points have been drawn then create first point
						pX1 = event.getX();
						pY1 = event.getY();
						// now the first point has been created
						pointExists1 = true;
					} else if (pointExists2 == false) {
						// If one point has been drawn then create second point
						// with second set of mouse coordinates
						pX2 = event.getX();
						pY2 = event.getY();
						// now the second point has been created
						pointExists2 = true;
					} else {
						// Now that two points exist, use the third set of mouse coordinates
						// to get the third point and create the object
						pX3 = event.getX();
						pY3 = event.getY();
						currentShapeObject = new Triangle(pX1, pY1, pX2, pY2, pX3, pY3, currentShapeColor);
						// addFront currentShapeObject onto myShapes
						myShapes.addFront(currentShapeObject);
						// set currentShapeObject to null (it has been drawn)
						currentShapeObject = null;
						// set booleans back to false to prepare for next triangle
						pointExists1 = pointExists2 = false;
						// repaint to make visible
						repaint();
					}
					break;

				case 4: // Text
					// Ask user for text they would like to display
					String mssge = JOptionPane.showInputDialog("enter text to display");
					// get the coordinates of the mouse click
					pX1 = event.getX();
					pY1 = event.getY();
					// create Text objects using mouse click and message from user
					currentShapeObject = new Text(pX1, pY1, currentShapeColor, mssge);
					// addFront currentShapeObject onto myShapes
					myShapes.addFront(currentShapeObject);
					// set currentShapeObject to null (it has been drawn)
					currentShapeObject = null;
					// repaint to make visible
					repaint();
					break;

				}
			}

		} // end mouseClicked

		// Used with shapes that require dragging -- not used for triangle or text
		// Function gets the released position of mouse and sends it to the shape then
		// it adds the shape to the list and repaints the board
		public void mouseReleased(MouseEvent event) {
			// Must make sure that in draw mode
			if (shapeMode == 0) {
				// make sure that the shape is a line, rectangle, or oval (shapes that require
				// dragging)
				if (currentShapeType == 0 || currentShapeType == 1 || currentShapeType == 2) {
					// sets currentShapeObject x2 & Y2
					currentShapeObject.setX2(event.getX());
					currentShapeObject.setY2(event.getY());
					// addFront currentShapeObject onto myShapes
					myShapes.addFront(currentShapeObject);
					// set currentShapeObject to null (it has been drawn)
					currentShapeObject = null;
					// repaint to make visible
					repaint();
				}
			}
		} // end mouseReleased

		// This function handles the dragging of the mouse
		// Lines, rectangles, and ovals are created by dragging
		// Shapes are also moved by dragging
		public void mouseDragged(MouseEvent event) {
			// Check to make sure in draw mode
			if (shapeMode == 0) {
				// Make sure that the shape is a line, rectangle or oval as those are the shapes
				// that are created by dragging
				if (currentShapeType == 0 || currentShapeType == 1 || currentShapeType == 2) {
					// sets currentShapeObject x2 and Y2
					currentShapeObject.setX2(event.getX());
					currentShapeObject.setY2(event.getY());
					// repaint to make visible
					repaint();
				}
			}

			// Make sure in edit mode and that a shape has been clicked on
			if (shapeMode == 1 && shapeFound != null) {
				// get coordinates from the mouse event
				int newX = event.getX();
				int newY = event.getY();
				// get the shape type
				moveType = shapeFound.getShapeType();
				// If the shape moved is text, can only move the X1,Y1 coordinates
				if (moveType == 4) {
					// set the X1 Y1 coordinates to the mouse coordinates
					shapeFound.setX1(newX);
					shapeFound.setY1(newY);
				}

				// If the shape moved is a line, rectangle, or oval, can move either
				// the X1,Y1 or the X2,Y2 coordinates -- check which vertex is modified
				// NOTE: can move lines from either vertex
				//		 can move rectangles from either vertex where started shape or ended shape (diagonal points)
				//       can move ovals from either vertex where started shape or ended shape, but it is based
				//    			on the way they are drawn, move on the rectangle that would encompasses the oval
				if (moveType == 0 || moveType == 1 || moveType == 2) {
					// check if the first vertex is modified
					if (vertexModified == 1) {
						// set the change in distance based on the new mouse coordinates
						// and the first vertex
						int dx = newX - shapeFound.getX1();
						int dy = newY - shapeFound.getY1();
						// update the first vertex to the mouse coordinates
						shapeFound.setX1(newX);
						shapeFound.setY1(newY);
						// update the second vertex using the change in distance and the
						// original coordinates
						shapeFound.setX2((shapeFound.getX2() + dx));
						shapeFound.setY2((shapeFound.getY2() + dy));

						// check if the second vertex is modified
					} else if (vertexModified == 2) {
						// set the change in distance based on the new mouse coordinates
						// and the second vertex
						int dx = newX - shapeFound.getX2();
						int dy = newY - shapeFound.getY2();
						// update the first vertex using the change in distance and the
						// original coordinates
						shapeFound.setX1((shapeFound.getX1() + dx));
						shapeFound.setY1((shapeFound.getY1() + dy));
						// update the second vertex to the mouse coordinates
						shapeFound.setX2(newX);
						shapeFound.setY2(newY);

					}
				}
				// NOTE: triangles can move from any of the three vertices
				if (moveType == 3) {
					if (vertexModified == 1) {
						// set the change in distance based on the new mouse coordinates
						// and the first vertex
						int dx = newX - shapeFound.getX1();
						int dy = newY - shapeFound.getY1();
						// update the first vertex to the mouse coordinates
						shapeFound.setX1(newX);
						shapeFound.setY1(newY);
						// update the second vertex using the change in distance and the
						// original coordinates
						shapeFound.setX2(shapeFound.getX2() + dx);
						shapeFound.setY2(shapeFound.getY2() + dy);
						// update the third vertex using the change in distance and the
						// original coordinates
						shapeFound.setX3(shapeFound.getX3() + dx);
						shapeFound.setY3(shapeFound.getY3() + dy);

					} else if (vertexModified == 2) {
						// set the change in distance based on the new mouse coordinates
						// and the second vertex
						int dx = newX - shapeFound.getX2();
						int dy = newY - shapeFound.getY2();
						// update the first vertex using the change in distance and the
						// original coordinates
						shapeFound.setX1(shapeFound.getX1() + dx);
						shapeFound.setY1(shapeFound.getY1() + dy);
						// update the second vertex to the mouse coordinates
						shapeFound.setX2(newX);
						shapeFound.setY2(newY);
						// update the third vertex using the change in distance and the
						// original coordinates
						shapeFound.setX3(shapeFound.getX3() + dx);
						shapeFound.setY3(shapeFound.getY3() + dy);

					}
					if (vertexModified == 3) {
						// set the change in distance based on the new mouse coordinates
						// and the third vertex
						int dx = newX - shapeFound.getX3();
						int dy = newY - shapeFound.getY3();
						// update the first vertex using the change in distance and the
						// original coordinates
						shapeFound.setX1(shapeFound.getX1() + dx);
						shapeFound.setY1(shapeFound.getY1() + dy);
						// update the second vertex using the change in distance and the
						// original coordinates
						shapeFound.setX2(shapeFound.getX2() + dx);
						shapeFound.setY2(shapeFound.getY2() + dy);
						// update the third vertex to the mouse coordinates
						shapeFound.setX3(newX);
						shapeFound.setY3(newY);
					}
				}
				// repaint so updates appear
				repaint();

			}

		} // end mouseDragged
	}
}