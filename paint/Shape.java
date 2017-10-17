package paint;

import java.awt.Color;
import java.awt.Graphics;

abstract class Shape {
	// Color object of each shape
	private Color color;
	// the string name that corresponds to the color object
	private String colorString;

	// use to keep track of type of shape when
	// reading from and writing to files
	private int shapeType; 
	private int x1, y1, x2, y2; // coordinates of shape
	private int x3, y3; // unused unless triangle
	private String message; // only used for text
	private int mode; //the mode that the shape is in
	
	// 0 for draw, 1 for edit
	public int getMode() {return mode;}
	public void setMode(int md) {mode = md;}

	// setter and getter for Color Object
	public Color getColor() {return color;}
	public void setColor(Color c) {color = c;}

	// only used for text
	public String getMessage() {return message;}

	// converts a Color object to a string representing that color
	// used when writing objects to a file
	public String getColorString(Color c) {
		String color = null;
		if (c == Color.BLACK) {
			color = "black";
		} else if (c == Color.BLUE) {
			color = "blue";
		} else if (c == Color.GREEN) {
			color = "green";
		} else if (c == Color.MAGENTA) {
			color = "magneta";
		} else if (c == Color.ORANGE) {
			color = "orange";
		} else if (c == Color.PINK) {
			color = "pink";
		} else if (c == Color.RED) {
			color = "red";
		} else if (c == Color.YELLOW) {
			color = "yellow";
		}
		return color;
	}

	// Returns the corresponding Color java object
	// for a string color -- used with loadPaint
	public Color getColorfromString(String s) {
		Color col = null;
		if (s.equals("black")) {
			col = Color.BLACK;
		} else if (s.equals("blue")) {
			col = Color.BLUE;
		} else if (s.equals("green")) {
			col = Color.GREEN;
		} else if (s.equals("magenta")) {
			col = Color.MAGENTA;
		} else if (s.equals("orange")) {
			col = Color.ORANGE;
		} else if (s.equals("pink")) {
			col = Color.PINK;
		} else if (s.equals("red")) {
			col = Color.RED;
		} else if (s.equals("yellow")) {
			col = Color.YELLOW;
		}
		return col;
	}

	// getter for shapeType
	public int getShapeType() {
		return shapeType;
		// 0 for Line, 1 for Rectangle, 2 for Oval
		// 3 for triangle and 4 for text
	}

	//Setter for the shapeType
	public void setShapeType(int s) {shapeType = s;}

	// setters for coordinates of shape
	public void setX1(int X1) {x1 = X1;}

	public void setY1(int Y1) {y1 = Y1;}

	public void setX2(int X2) {x2 = X2;}

	public void setY2(int Y2) {y2 = Y2;}
	
	public void setX3(int X3) {x3 = X3;}

	public void setY3(int Y3) {y3 = Y3;}

	// Getters for coordinates of shape
	public int getX1() {return x1;}
	
	public int getY1() {return y1;}
	
	public int getX2() {return x2;}
	
	public int getY2() {return y2;}
	
	public int getX3() {return x3;}
	
	public int getY3() {return y3;}

	// abstract function to be overridden in inherited classes
	// used by each shape to draw that specific shape
	abstract public void draw(Graphics g);

}
