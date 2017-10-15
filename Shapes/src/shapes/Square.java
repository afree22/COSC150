package shapes;

import java.util.*;


public class Square implements Shapes
{
	protected double side;
	
	public Square(StringTokenizer st)
	{
		side = Integer.parseInt( st.nextToken() );
	}
	
	public void setSide(int s) {side = s;}
	public double getSide() {return side;}
	
	@Override
	public double getArea() {
		double area = side*side;
		return area;
	}

	@Override
	public double getPerimeter() {
		double perimeter = 4*side;
		return perimeter;
	}

	@Override
	public void getReport() {
		System.out.println("Square " +side);
		System.out.println("\tArea of Square= "+getArea());
		System.out.println("\tPerimeter of Square= "+getPerimeter());
	}

}
