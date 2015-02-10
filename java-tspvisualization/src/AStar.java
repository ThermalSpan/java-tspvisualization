import java.awt.Color;
import java.util.ArrayList;


public class AStar implements Runnable {
	TSPApp app;
	ArrayList<Point> tour;
	ArrayList<Point> toAddList;
	ArrayList<Line> tourLines;
	Color mstColor;
	
	public AStar(TSPApp app, ArrayList<Point> pointList, Color mstColor) {
		this.app = app;
		tour = new ArrayList<Point>();
		toAddList = (ArrayList<Point>) pointList.clone();
		tourLines = new ArrayList<Line>();
		this.mstColor = mstColor;
		tour.add(toAddList.remove(0));
	}

	
	public void run() {
		sleep();
		
		double minCost, cost;
		Point fromPoint, nextPoint, minPoint;
		ArrayList<Point> mstList;
		MST graph;
		
		nextPoint = null;
		minPoint = null;
		//Here we implement A* search
		// -Starting at some point (first one in this case) we iteratively add a point to the tour
		// -The cost of all possible nodes is evaluated by h(n)+g(n) where:
		//		-- h(n) is distance to node
		// 		-- g(n) is the minimum spanning tree of all remaining nodes. 
		// -Each iteration the node with the lowest cose is added. 
		while(!toAddList.isEmpty()) {
			if (Thread.currentThread().isInterrupted()) break;
			//Max number of points is 500, max distance is 1, thus max traverse is 500
			minCost = 500;
			fromPoint = tour.get(tour.size()-1);
			for(Point p: toAddList) {
				//A* stuff, build mst, calculate cost, compare
				nextPoint = p;
				mstList = (ArrayList<Point>) toAddList.clone();
				mstList.remove(p);
				graph = new MST(mstList, mstColor);
				cost = fromPoint.Distance(nextPoint) + graph.weight;
				if(cost < minCost) {
					minCost = cost;
					minPoint = nextPoint;
				}
				
				//Animation Stuff
				if (Thread.currentThread().isInterrupted()) break;
				sleep();
				graph.getLines().add(new Line(fromPoint, nextPoint, Color.BLUE));
				draw(graph);
			}
			tour.add(minPoint);
			tourLines.add(new Line(fromPoint, minPoint, Color.GRAY));
			toAddList.remove(minPoint);
		}
		
		if (Thread.currentThread().isInterrupted()) app.finishreset();
	}
	
	public void draw(MST graph) {
		ArrayList<Line> result = (ArrayList<Line>) tourLines.clone();
		result.addAll(graph.getLines());
		app.updateLines(result);
	}
	
	public void sleep() {
		try {
			Thread.sleep(app.getDelay());
		} catch (InterruptedException e) {
			System.out.println("Failed to Sleep?");
		}
	}
}
