import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Scanner;

public class Visualization extends JComponent {
    //int xPos = 100;
    private int scale;
    private JFrame f;
    private ArrayList<TSNode> nodeList;
    private ArrayList<Ellipse2D> circList = new ArrayList<>();
    private ArrayList<Line2D> lineList;

    public Visualization(ArrayList<TSNode> nodelist, int scale, boolean visible){
        this.nodeList = nodelist;
        this.scale = scale;
        this.f = new JFrame("Travelling Salesman");
        this.f.setSize(this.scale, this.scale);
        this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.f.setVisible(visible);
        for (TSNode tsNode:this.nodeList){
            Ellipse2D circ = new Ellipse2D.Double(this.scale * tsNode.getxPos() - 4.5,
                    this.scale * tsNode.getyPos() - 4.5, 9.0, 9.0);
            this.circList.add(circ);
        }
        this.f.add(this);
    }

    public void updateVisualization(ArrayList<Integer> TravelRoute){
        this.lineList = new ArrayList<>();
        for (int i = 1; i < TravelRoute.size(); i++) {
            Line2D line = new Line2D.Double(this.nodeList.get(TravelRoute.get(i-1)).getxPos() * this.scale,
                    this.nodeList.get(TravelRoute.get(i-1)).getyPos() * this.scale,
                    this.nodeList.get(TravelRoute.get(i)).getxPos() * this.scale,
                    this.nodeList.get(TravelRoute.get(i)).getyPos() * this.scale);
            this.lineList.add(line);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Line2D line:this.lineList){ g2.draw(line); }
        for (Ellipse2D circ:this.circList){ g2.draw(circ); }
    }
}

