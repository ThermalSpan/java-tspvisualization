import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.DoubleStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TSPApp {
	JFrame frame;
	JPanel mainPanel;
	JPanel controlPanel;
	DrawPanel drawPanel;
	JButton resetButton;
	JSlider speedSlider;
	JSlider countSlider;
	JLabel countLabel;
	JLabel speedLabel;
	Color mstColor;
	
	int timeDelay;
	int pointCount;
	
	Random rand;
	AStar aStar;
	Thread aStarThread;
	ArrayList<Point> points;
	ArrayList<Line> linesToDraw;
	
	public TSPApp() {
	  	rand = new Random();
    	points = new ArrayList<Point>();
    	linesToDraw = new ArrayList<Line>();
    	
    	mstColor = Color.GREEN;
    	
    	timeDelay = 1000;
    	pointCount = 20;
    	buildGUI();
	}
	
	public void reset() {
		if(aStarThread != null && aStarThread.isAlive()) {
			aStarThread.interrupt();
		}
		else finishreset();
	}
	
	public void finishreset() {
		points.clear();
		linesToDraw.clear();
	
		
		for(int i = 0; i < pointCount; i++) {
			double x = rand.nextDouble();
			double y = rand.nextDouble();
			points.add(new Point(x,y));
		}
		
		drawPanel.repaint();
		
		aStarThread = new Thread(new AStar(this, points, mstColor));
		aStarThread.start();
	}
	
	public void updateLines(ArrayList<Line> lineList) {
		linesToDraw.clear();
		linesToDraw.addAll(lineList);
		drawPanel.repaint();
	}
	
	public void buildGUI() {
		//Setup Frame
		frame = new JFrame("TSP Visualization");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Setup Main Panel, root container
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		frame.getContentPane().add(mainPanel);
		
		//Setup drawPanel
		drawPanel = new DrawPanel(points,linesToDraw);
    	drawPanel.setPreferredSize(new Dimension(1000,1000));
    	mainPanel.add(drawPanel);
    	
    	//Setup controlPanel
    	controlPanel = new JPanel();
    	controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
    	mainPanel.add(controlPanel);
    	
    	//Setup resetButton
    	resetButton = new JButton("Reset");
    	resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}	
    	});
    	controlPanel.add(resetButton);
    	
    	//Setup countPanel
    	JPanel countPanel = new JPanel();
    	countPanel.setLayout(new FlowLayout());
    	countSlider = new JSlider(JSlider.VERTICAL,4,500, pointCount);
    	countSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				pointCount = countSlider.getValue();
				countLabel.setText(Integer.toString(pointCount));
			}
    	});
    	JPanel countLabelPanel = new JPanel();
    	countLabel = new JLabel(Integer.toString(pointCount));
    	countLabelPanel.setLayout(new BoxLayout(countLabelPanel, BoxLayout.PAGE_AXIS));
    	countLabelPanel.add(countLabel);
    	countLabelPanel.add(new JLabel("Points"));
    	countPanel.add(countSlider);
    	countPanel.add(countLabelPanel);
    	controlPanel.add(countPanel);
    	
    	//Setup speedPanel
    	JPanel speedPanel = new JPanel();
    	speedPanel.setLayout(new FlowLayout());
    	speedSlider = new JSlider(JSlider.VERTICAL,0,1000, timeDelay);
    	speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				timeDelay = speedSlider.getValue();
				speedLabel.setText(Integer.toString(timeDelay));
			}
    	});
    	JPanel speedLabelPanel = new JPanel();
    	speedLabel = new JLabel(Integer.toString(timeDelay));
    	speedLabelPanel.setLayout(new BoxLayout(speedLabelPanel, BoxLayout.PAGE_AXIS));
    	speedLabelPanel.add(speedLabel);
    	speedLabelPanel.add(new JLabel("Delay"));
    	speedPanel.add(speedSlider);
    	speedPanel.add(speedLabelPanel);
    	controlPanel.add(speedPanel);
        
    	//Finish and display
    	frame.pack();
    	frame.setVisible(true);
	}

	public int getDelay() {return timeDelay;}
	
	public static void main(String[] args) {
		new TSPApp();
	}
}
