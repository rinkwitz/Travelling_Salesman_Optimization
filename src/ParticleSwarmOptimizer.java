import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ParticleSwarmOptimizer {
    private ArrayList<TSNode> nodeList;
    private int startId;
    private boolean showVisualization;
    private Random randGen = new Random();
    private String status;

    public ParticleSwarmOptimizer(ArrayList<TSNode> nodeList, boolean showVisualization){
        this.nodeList = nodeList;
        this.startId = nodeList.get(0).getId();
        this.showVisualization = showVisualization;
        this.status = "";
    }

    private ArrayList<Integer> getInitialRoute(){
        ArrayList<Integer> TravelRoute = new ArrayList<>();
        for (int i = 0; i < this.nodeList.size(); i++) {
            if (i != this.startId){
                TravelRoute.add(i);
            }
        }
        Collections.shuffle(TravelRoute);
        TravelRoute.add(0, this.startId);
        TravelRoute.add(this.startId);
        return TravelRoute;
    }

    public ArrayList<Integer> solve(){
        System.out.println("----------------------------------------------");
        System.out.println("--- Particle Swarm Optimization:\n");
        ArrayList<Integer> TravelRoute = this.getInitialRoute();
        Visualization vis = new Visualization("Particle Swarm Optimization", this.nodeList, 500, this.showVisualization);
        vis.updateVisualization(TravelRoute, this.status);

        return TravelRoute;
    }
}
