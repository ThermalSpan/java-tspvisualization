import java.awt.Color;
import java.util.ArrayList;


public class MST {
	ArrayList<Line> edgeList;
	Color mstColor;
	double weight;
	
	public MST(ArrayList<Point> pointList, Color mstColor) {
		ArrayList<Point> points = (ArrayList<Point>) pointList.clone();
		this.mstColor = mstColor;
		
		ArrayList<Point> mst = new ArrayList<Point>();
		edgeList = new ArrayList<Line>();
		weight = 0;
		
		
		
		//An implementation of Prim's algorithm for a fully connected graph. 
		// -Start with one point, "root", see first line.
		// -Find the shortest edge that adds a point not yet in the graph and use to add said point
		// -Iterate until all points added, see the while block
		// -Here we need to check all distances since this is a fully connected graph, hence double for loops.
		// -Maintain a known min for each iteration to avoid using a priority queue or heap
		if(!points.isEmpty()) mst.add(points.remove(0));
		double min, dist;
		Point nextPoint,fromPoint;
		while(!points.isEmpty()) {
			min = 1;
			nextPoint = null;
			fromPoint = null;
			for(Point p0: mst) 
				for(Point p1: points) {
					dist = p0.Distance(p1);
					if(dist < min) {
						min = dist;
						nextPoint = p1;
						fromPoint = p0;
					}
				}
			//Once shortest edge is found, we:
			// - add point to graph
			// - add edge to edge list
			// - update total weight of the graph for A*
			mst.add(points.remove(points.indexOf(nextPoint)));
			edgeList.add(new Line(fromPoint,nextPoint,mstColor));
			weight += min;
		}
	}
	
	public ArrayList<Line> getLines() {return edgeList;}
	public double getWeight() {return weight;}
}
