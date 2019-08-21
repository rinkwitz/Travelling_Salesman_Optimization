import java.awt.image.AreaAveragingScaleFilter;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TSNode {
    private static Random randGen = new Random(42);
    private static int nodeCount = 0;
    private double xPos;
    private double yPos;
    private int id;
    private HashMap<Integer, Double> distMap;
    private ArrayList<Integer> idxOrder;
    private ArrayList<Double> valOrder;

    public TSNode(){
        this.id = nodeCount;
        this.xPos = randGen.nextDouble();
        this.yPos = randGen.nextDouble();
        this.distMap = new HashMap<>();
        nodeCount++;
    }

    public int getId() {
        return this.id;
    }

    public double getxPos() {
        return this.xPos;
    }

    public double getyPos() {
        return this.yPos;
    }

    public HashMap<Integer, Double> getDistMap(){
        return this.distMap;
    }

    public void updateDistMap(int otherId, double dist) {
        this.distMap.put(otherId, dist);
    }

    public double getDist(int otherId){
        return this.distMap.get(otherId);
    }

    public ArrayList<Integer> getIdxOrder(){return this.idxOrder;}

    public void calcIdxOrder(){
        this.idxOrder = new ArrayList<>();
        for (int i:this.distMap.keySet()) {
            if (this.idxOrder.size() == 0){
                this.idxOrder.add(i);
            } else {
                boolean added = false;
                for (int j = 0; j < this.idxOrder.size(); j++) {
                    if (this.distMap.get(i) < this.distMap.get(this.idxOrder.get(j))){
                        this.idxOrder.add(j, i);
                        added = true;
                        break;
                    }
                }
                if (!added){
                    this.idxOrder.add(i);
                }
            }
        }
        this.valOrder = new ArrayList<>();
        for (int i:this.idxOrder){this.valOrder.add(this.distMap.get(i));}
    }
}
