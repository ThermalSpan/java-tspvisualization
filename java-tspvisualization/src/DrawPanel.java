import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class DrawPanel extends JPanel{
	ArrayList<Point> pointList;
	ArrayList<Line> lineList;
	
	public DrawPanel(ArrayList<Point> pointList, ArrayList<Line> lineList) {
		this.pointList = pointList;
		this.lineList = lineList;
	}
	
	public void paint(Graphics g) {
		double dwidth = (double) getWidth();
		double dheight = (double) getHeight();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Draw Lines
		g2d.setStroke(new BasicStroke(4));
		for(Line l: lineList) {
			g2d.setColor(l.getC());
			double x1 = dwidth*l.getP1().getX();
			double y1 = dwidth*l.getP1().getY();
			double x2 = dwidth*l.getP2().getX();
			double y2 = dwidth*l.getP2().getY();
			g2d.draw(new Line2D.Double(x1,y1,x2,y2));
		}
		
		//Draw Points
		g.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(5));
		for(Point p: pointList) {
			double x = dwidth * p.getX();
			double y = dheight * p.getY();
			//Best practice for drawing point?
			g2d.draw(new Line2D.Double(x,y,x,y));
		}	
	}
}
