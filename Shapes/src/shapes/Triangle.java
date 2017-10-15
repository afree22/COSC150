package shapes;
import java.util.*;

public class Triangle implements Shapes
{
	protected double side1;
	protected double side2;
	protected double side3;
	
	public Triangle(StringTokenizer st)
	{
		side1 = Integer.parseInt( st.nextToken() );
		side2 = Integer.parseInt( st.nextToken() );
		side3 = Integer.parseInt( st.nextToken() );
	}
	
	public void setSide1(int s1) {side1 = s1;}
	public void setSide2(int s2) {side2 = s2;}
	public void setSide3(int s3) {side3 = s3;}
	public double getSide1() {return side1;}
	public double getSide2() {return side2;}
	public double getSide3() {return side3;}

	
	@Override
	public double getArea() {
		double p = (side1 + side2 + side3)/2.0;
		double area = Math.sqrt(p*(p-side1)*(p-side2)*(p-side3));
		return area;
	}

	@Override
	public double getPerimeter() {
		double perimeter = side1 + side2 + side3;
		return perimeter;
	}

	@Override
	public void getReport() {
		System.out.println("triangle " +side1+ " " +side2+ " " + side3);
		System.out.println("\tArea of Triangle= "+getArea());
		System.out.println("\tPerimeter of Triangle= "+getPerimeter());
	}

}
