import java.awt.Color;


public class Line {
	Point p1;
	Point p2;
	Color c;
	
	public Line(Point p1, Point p2, Color c) {
		this.p1 = p1;
		this.p2 = p2;
		this.c = c;
	}
	
	public Point getP1() {return p1;}
	public Point getP2() {return p2;}
	public Color getC() {return c;}
}
