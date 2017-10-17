package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.util.StringTokenizer;

//Class for the Triangle
//it extends the class Shape
//it has three sets of coordinates and a Color
//it is identified by it's shapeType of 3
public class Triangle extends Shape {

	//value for triangle's shapeType
	public int tShapeType = 3;
	
	//Constructor with integer and Color parameters for triangle
	public Triangle(int xa, int ya, int xb, int yb, int xc, int yc, Color col) {
		// calls the setShapeType in Shape, passes value of Triangle's shapeType
		setShapeType(tShapeType);
		// calls the setters for the coordinates and Color in Shape,
		// passing the values from Triangle's constructor
		setX1(xa);
		setY1(ya);
		setX2(xb);
		setY2(yb);
		setX3(xc);
		setY3(yc);
		setColor(col);
	}
	
	
	//Constructor with StringTokenizer, used with loadPaint
	public Triangle(StringTokenizer st) {
		String cmd = st.nextToken();
		// gets the corresponding Color object from the 
		// string color in line and set the color
		Color currentColor = getColorfromString(cmd);
		setColor(currentColor);
		// set shapeType for new shape
		setShapeType(tShapeType);
		// set the integers of the shape according to 
		// the integers stored in the file
		setX1(Integer.parseInt( st.nextToken()));
		setY1(Integer.parseInt( st.nextToken()));
		setX2(Integer.parseInt( st.nextToken()));
		setY2(Integer.parseInt( st.nextToken()));
		setX3(Integer.parseInt( st.nextToken()));
		setY3(Integer.parseInt( st.nextToken()));
	}

	// Overrides the abstract draw function in Shape
	// Draws the triangle based on the color and the coordinates
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor()); // sets the color
		g.drawPolygon(new int[] { getX1(), getX2(), getX3() }, new int[] { getY1(), getY2(), getY3() }, 3);

	}

}