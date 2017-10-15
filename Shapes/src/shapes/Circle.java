package shapes;

import java.util.*;

public class Circle implements Shapes
{

	protected double radius;
	
	public Circle(StringTokenizer st)
	{
		radius = Integer.parseInt( st.nextToken() );
	}
	
	public void setRadius(int r) {radius = r;}
	public double getRadius() {return radius;}
	
	@Override
	public double getArea()
	{
		double area = Math.PI * radius*radius;
		return area;
	}

	@Override
	public double getPerimeter() 
	{
		double perimeter = Math.PI * 2*radius;
		return perimeter;
	}

	@Override
	public void getReport()
	{
		System.out.println("Circle " +radius);
		System.out.println("Area of Circle= "+getArea());
		System.out.println("Perimeter of Circle="+getPerimeter());
	}

}
