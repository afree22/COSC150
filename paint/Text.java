package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.util.StringTokenizer;


//Class for Text
//it extends the class Shape
//it has one sets of coordinates, a String message and a Color
//it is identified by it's shapeType of 4
public class Text extends Shape {

	// has a message to display
	private String message;
	//value for Text's shapeType
	private int txtShapeType = 4;

	// setter for the message
	public void setMessage(String m) {message = m;}

	// getter for the message
	public String getMessage() {return message;}

	public Text(int xa, int ya, Color col, String m) {
		// calls the setShapeType in Shape, passes value of Text's shapeType
		setShapeType(txtShapeType);
		// calls the setters for the coordinates, message and Color 
		// in Shape, passing the values from Texts's constructor
		setX1(xa);
		setY1(ya);
		setColor(col);
		message = m;
	}
	
	//Constructor with StringTokenizer, used with loadPaint
	public Text(StringTokenizer st) {
		String cmd = st.nextToken(); 
		// gets the corresponding Color object from the 
		// string color in line and set the color
		Color currentColor = getColorfromString(cmd); 
		setColor(currentColor); //set color
		// set shapeType for new shape
		setShapeType(txtShapeType);
		// set the integers of the shape according to 
		// the integers stored in the file
		setX1(Integer.parseInt( st.nextToken()));  
		setY1(Integer.parseInt( st.nextToken())); 
		// set the message of the shape according to 
		// the String stored in the file
		cmd = st.nextToken(); //get the string for message to display
		setMessage(cmd); //sets message
	}

	// Overrides the abstract draw function in Shape
	// Draws message at coordinates in specific color
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor()); // sets the color
		g.drawString(message, getX1(), getY1());
	}
}
