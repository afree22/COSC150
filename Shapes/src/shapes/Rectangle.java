package shapes;

import java.util.*;

public class Rectangle implements Shapes
{

	protected double height;
	protected double width;
	
	public Rectangle(StringTokenizer st)
	{
		height = Integer.parseInt( st.nextToken() );
		width = Integer.parseInt( st.nextToken() );
	}
	
	public void setHeight(int h) {height =h;}
	public void setWidth(int w) {width = w;}
	public double getHeight() {return height;}
	public double getWidth() {return width;}
	
	@Override
	public double getArea() {
		double area = height * width;
		return area;
	}

	@Override
	public double getPerimeter() {
		double perimeter = 2*height + 2*width;
		return perimeter;
	}

	@Override
	public void getReport() {
		System.out.println("Rectangle " +height+ " "+width);
		System.out.println("\tArea of Rectangle= "+getArea());
		System.out.println("\tPerimeter of Rectangle=" +getPerimeter());
		
	}

}
