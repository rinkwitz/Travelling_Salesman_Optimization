import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TSNodeGenerator {
    private static double generateProb = 0.5;
    private int numNodes;
    private String dist;
    private static List<String> allowedDist = Arrays.asList("euclidean", "random");
    private String interconnection;
    private static List<String> allowedInterconnection = Arrays.asList("all", "random");

    public TSNodeGenerator(int numNodes, String dist, String interconnection){
        this.numNodes = numNodes;
        this.dist = dist;
        this.interconnection = interconnection;
        if (!allowedDist.contains(this.dist)) {
            throw new IllegalArgumentException("dist must be \"euclidean\" or \"random\"");
        }
        if (!allowedInterconnection.contains(this.interconnection)) {
            throw new IllegalArgumentException("interconnection must be \"all\" or \"random\"");
        }
    }

    public TSNodeGenerator(int numNodes){
        this(numNodes, "euclidean", "all");
    }

    public ArrayList<TSNode> generate(){
        Random randGen = new Random();
        ArrayList<TSNode> nodeList = new ArrayList<>();
        for (int i = 0; i < this.numNodes; i++) {
            nodeList.add(new TSNode());
        }
        for (TSNode node1:nodeList) {
            for(TSNode node2:nodeList){
                if (node1.getId() == node2.getId()){continue;}
                if (this.interconnection.equals("all")){
                    double dist;
                    if (this.dist.equals("euclidean")){
                        dist = Math.sqrt(Math.pow(node1.getxPos() - node2.getxPos(), 2.0) +
                                Math.pow(node1.getyPos() - node2.getyPos(), 2.0));
                    } else {
                        dist = randGen.nextDouble();
                    }
                    node1.updateDistMap(node2.getId(), dist);
                    node2.updateDistMap(node1.getId(), dist);
                } else {
                    if (randGen.nextDouble() < generateProb){
                        double dist;
                        if (this.dist.equals("euclidean")){
                            dist = Math.sqrt(Math.pow(node1.getxPos() - node2.getxPos(), 2.0) +
                                    Math.pow(node1.getyPos() - node2.getyPos(), 2.0));
                        } else {
                            dist = randGen.nextDouble();
                        }
                        node1.updateDistMap(node2.getId(), dist);
                        node2.updateDistMap(node1.getId(), dist);
                    }
                }
            }
        }
        for (TSNode tsNode:nodeList){tsNode.calcIdxOrder();}
        return nodeList;
    }

}
