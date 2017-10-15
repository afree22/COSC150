package shapes;

import java.io.*;
import java.util.*;


public class ShapesList 

{
	   LinkedList<Shapes> shapes; // attribute, once class is created it exists, can be 
	                                 // used later
	   
	   public static void main( String[] args )
	   {
		   new ShapesList();
	   }
	   
	   // constructor, reads a file of shapes and loads them onto the list
	   // and does a report() to check list contents
	   public ShapesList()
	   {
		   String filename;
		   Scanner in = new Scanner(System.in);
		   
		   System.out.print("enter file name to load : ");
		   filename = in.nextLine();
		   
		   
		   shapes = new LinkedList<Shapes> (); // initialize the list
		   try
		   {
			   // open the file
			   File f = new File(filename);
			   FileReader fr = new FileReader( f );
			   BufferedReader br = new BufferedReader( fr );
			   
			   // read and process lines
			   String line;
			   while ( (line = br.readLine()) != null )
			   {
				   dealWithLine( line );
			   }
			   br.close();
		   }
		   catch ( Exception e )
		   { System.out.println("file thingy: "+e); }
		   
		   in.close();
		   
		   report();
	   }
	   
	   // Process this line from the file, expecting shapes
	   // definitions ...
	   // Note: catch errors in line (so you can keep reading the file)
	   public void dealWithLine( String line )
	   {
		   try
		   {
			   System.out.println("about to process ... "+line);
			   StringTokenizer st = new StringTokenizer( line );
			   String cmd = st.nextToken();
			   if      ( cmd.equals("circle") )	{ shapes.add( new Circle(st) ); }
			   else if ( cmd.equals("triangle"   ) ) { shapes.add( new Triangle(st) ); }
			   else if ( cmd.equals("square"   ) ) { shapes.add( new Square(st) ); }
			   else if ( cmd.equals("rectangle"   ) ) { shapes.add( new Rectangle(st) ); }
			   else {System.out.println("Error: must enter a valid shape");}
		   }
		   catch (NoSuchElementException n) {}
		   catch (Exception e )
		   { System.out.println("error in this line " +e);
		   }
	   }
	   
	   // print out about all of the shapes on the list
	   public void report()
	   {
		  Iterator<Shapes> it = shapes.iterator();
		  double totalArea = 0;
		  double totalPerimeter = 0;
		  while ( it.hasNext() )
		  {
			 Shapes s = it.next();
			 totalArea += s.getArea();
			 totalPerimeter += s.getPerimeter();
			 s.getReport();
		  }
		  System.out.println("totalArea="+totalArea);
		  System.out.println("totalPerimeter="+totalPerimeter);
	   }
	}
