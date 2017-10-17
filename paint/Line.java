package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.util.StringTokenizer;


// Class for the Line
// it extends the class Shape
// it has two sets of coordinates and a Color
// it is identified by it's shapeType of 0
public class Line extends Shape {

	//value for line's shapeType
	private int lShapeType = 0;

	public Line(int xa, int ya, int xb, int yb, Color col) {
		setShapeType(lShapeType); //calls the setShapeType in Shape
		// Sets the coordinates and Color in Shape
		setX1(xa);
		setY1(ya);
		setX2(xb);
		setY2(yb);
		setColor(col);

	}

	//Constructor with StringTokenizer, used with loadPaint
	public Line(StringTokenizer st) {
		String cmd = st.nextToken();
		// gets the corresponding Color object from the 
		// string color in line and set the color
		Color currentColor = getColorfromString(cmd);
		setColor(currentColor);
		// set shapeType for new shape
		setShapeType(lShapeType);
		// set the integers of the shape according to 
		// the integers stored in the file
		setX1(Integer.parseInt(st.nextToken()));
		setY1(Integer.parseInt(st.nextToken()));
		setX2(Integer.parseInt(st.nextToken()));
		setY2(Integer.parseInt(st.nextToken()));
	}
	
	
	// Overrides the abstract draw function in Shape
	// Draws the line based on the color and the coordinates
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor()); // sets the color
		g.drawLine(getX1(), getY1(), getX2(), getY2()); // draws the line

	}

}
