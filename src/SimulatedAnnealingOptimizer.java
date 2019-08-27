import java.util.*;
import java.util.Collections;

public class SimulatedAnnealingOptimizer {
    private int numIterations;
    private double startTemperature;
    private ArrayList<TSNode> nodeList;
    private int startId;
    private boolean showVisualization;
    private Random randGen = new Random();
    private String status;

    public SimulatedAnnealingOptimizer(int numIterations, double startTemperature, ArrayList<TSNode> nodeList,
                                       boolean showVisualization){
        this.numIterations = numIterations;
        this.startTemperature = startTemperature;
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

    private double getCost(ArrayList<Integer> TravelRoute, int numIteration){
        //return Utils.calcDistNegativeAntiproportionalSums(this.nodeList, TravelRoute);
        return Utils.calcDistSumsSquared(this.nodeList, TravelRoute);
    }

    private double getTemperature(int numIteration){
        // linear:
        //return this.startTemperature -  numIteration * this.startTemperature / this.numIterations;

        // exponential:
        return this.startTemperature * Math.exp(Math.log(Utils.TINY_CONST / this.startTemperature)
                / this.numIterations * numIteration);
    }

    private ArrayList<Integer> getNeighbour(ArrayList<Integer> TravelRoute){
        int caseNum = randGen.nextInt(4);
        switch (caseNum){
            case 0:
                return Mutation.ReciprocalExchange(TravelRoute);
            case 1:
                return Mutation.Insertion(TravelRoute);
            case 2:
                return Mutation.Inversion(TravelRoute);
            case 3:
                return Mutation.Displacement(TravelRoute);
            default:
                return Mutation.ReciprocalExchange(TravelRoute);
        }
    }

    public ArrayList<Integer> solve(){
        System.out.println("----------------------------------------------");
        System.out.println("--- Simulated Annealing:\n");
        ArrayList<Integer> TravelRoute = this.getInitialRoute();
        Visualization vis = new Visualization("Simulated Annealing", this.nodeList, 500, this.showVisualization);
        for (int numIteration = 0; numIteration < this.numIterations; numIteration++) {
            ArrayList<Integer> neighbour = this.getNeighbour(TravelRoute);
            double prob = Math.min(1.0,
                    Math.exp(-(this.getCost(neighbour, numIteration)-this.getCost(TravelRoute, numIteration))/this.getTemperature(numIteration)));
            if (randGen.nextDouble() < prob){
                TravelRoute = neighbour;
            }

            // optional output:
            if (numIteration % 1000 == 0 || (numIteration + 1) == this.numIterations){
                this.status = String.format("Iteration: %d    Cost: %f    Temp: %f%n",
                        numIteration, Utils.calcDistSumsSquared(this.nodeList, TravelRoute), this.getTemperature(numIteration));
                vis.updateVisualizationSimulatedAnnealing(TravelRoute, this.status);
                System.out.format(this.status);
            }

        }
        return TravelRoute;
    }

}
