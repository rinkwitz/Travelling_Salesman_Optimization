import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Visualization extends JComponent {
    private int scale;
    private JFrame f;
    private String title;
    private ArrayList<TSNode> nodeList;
    private ArrayList<Ellipse2D> circList = new ArrayList<>();
    private ArrayList<Line2D> lineList;
    private String status;
    private String dist;

    public Visualization(String title, ArrayList<TSNode> nodelist, int scale, boolean visible){
        this.title = title;
        this.nodeList = nodelist;
        this.scale = scale;
        this.f = new JFrame(this.title);
        this.f.setSize(this.scale, this.scale + 40);
        //this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.f.setVisible(visible);
        for (TSNode tsNode:this.nodeList){
            Ellipse2D circ = new Ellipse2D.Double(this.scale * tsNode.getxPos() - 4.5,
                    this.scale * tsNode.getyPos() - 4.5, 9.0, 9.0);
            this.circList.add(circ);
        }
        this.status = "";
        this.f.add(this);
        repaint();
    }

    public void updateVisualization(ArrayList<Integer> TravelRoute, String status){
        this.lineList = new ArrayList<>();
        this.status = status;
        this.dist = String.format("Distance: %f", Utils.calcDistSums(this.nodeList, TravelRoute));
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
        g2.drawString(this.status, 5, 480);
        g2.drawString(this.dist, 5, 500);
    }
}

