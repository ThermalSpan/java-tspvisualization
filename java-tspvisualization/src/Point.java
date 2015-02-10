
public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double Distance(Point p) {
		double difx = x - p.getX();
		double dify = y - p.getY();
		return Math.sqrt(Math.pow(difx, 2) + Math.pow(dify, 2));
	}
	
	public double getX() {return x;}
	public double getY() {return y;}
}

