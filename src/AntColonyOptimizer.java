import java.util.ArrayList;
import java.util.Random;

public class AntColonyOptimizer {
    private ArrayList<TSNode> nodeList;
    private boolean showVisualization;
    private Random randGen = new Random();
    private String status;
    private int numAnts;
    private int numIterations;
    public double[][] pheromone;
    private double q0;
    private double alpha;
    private double beta;
    private double rho;
    private double tau0;
    private double globalBestDist;
    private ArrayList<Integer> globalBestRoute;

    public AntColonyOptimizer(int numAnts, int numIterations, double beta, double q0, double alpha, double rho, double tau0,
                              ArrayList<TSNode> nodeList, boolean showVisualization){
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.beta = beta;
        this.q0 = q0;
        this.alpha = alpha;
        this.rho = rho;
        this.tau0 = tau0;
        this.nodeList = nodeList;
        this.showVisualization = showVisualization;
        this.status = "";
        this.globalBestDist = Utils.BIG_CONST;
        this.globalBestRoute = new ArrayList<>();
        this.pheromone = new double[this.nodeList.size()][this.nodeList.size()];
        this.initPheromoneDistribution(this.tau0);
    }

    private void initPheromoneDistribution(double initVal){
        for (int i = 0; i < this.pheromone.length; i++) {
            for (int j = 0; j < this.pheromone[0].length; j++) {
                this.pheromone[i][j] = initVal;
            }
        }
    }

    private int getIdBestAnt(ArrayList<Ant> AntList){
        int bestId = 0;
        boolean first = true;
        double bestDist = Utils.BIG_CONST;
        for (int i = 0; i < AntList.size(); i++) {
            if (first){
                bestDist = Utils.calcDistSums(this.nodeList, AntList.get(i).getTravelRoute());
                first = false;
            } else {
                if (Utils.calcDistSums(this.nodeList, AntList.get(i).getTravelRoute()) < bestDist){
                    bestId = i;
                    bestDist = Utils.calcDistSums(this.nodeList, AntList.get(i).getTravelRoute());
                }
            }
        }
        return bestId;
    }

    public void calcGlobalPheromoneUpdate(){
        double length = Utils.calcDistSums(this.nodeList, this.globalBestRoute);
        for (int i = 0; i < this.globalBestRoute.size() - 1; i++) {
            this.pheromone[this.globalBestRoute.get(i)][this.globalBestRoute.get(i+1)] = (1 - this.alpha) *
                    this.pheromone[this.globalBestRoute.get(i)][this.globalBestRoute.get(i+1)] + this.alpha * (1 / length);
        }
    }

    public ArrayList<Integer> solve(){
        System.out.println("----------------------------------------------");
        System.out.println("--- Ant Colony:\n");
        Visualization vis = new Visualization("Ant Colony", this.nodeList, 500, this.showVisualization);

        for (int numIteration = 0; numIteration < this.numIterations; numIteration++) {
            ArrayList<Ant> AntList = new ArrayList<>();
            for (int i = 0; i < this.numAnts; i++) {AntList.add(new Ant(this.nodeList));}
            for (Ant ant:AntList){
                ant.runTour(this.pheromone, this.q0, this.beta, this.rho, this.tau0);
            }
            int bestId = this.getIdBestAnt(AntList);
            if (Utils.calcDistSums(this.nodeList, AntList.get(bestId).getTravelRoute()) < this.globalBestDist){
                this.globalBestDist = Utils.calcDistSums(this.nodeList, AntList.get(bestId).getTravelRoute());
                this.globalBestRoute = new ArrayList<>(AntList.get(bestId).getTravelRoute());
            }
            this.calcGlobalPheromoneUpdate();

            // optional visualization:
            if (numIteration % 1 == 0){
                this.status = String.format("Iteration: %d    Iteration Best: %f%n", numIteration,
                        Utils.calcDistSums(this.nodeList, AntList.get(bestId).getTravelRoute()));
                System.out.print(this.status);
                vis.updateVisualizationAntColony(AntList.get(bestId).getTravelRoute(), this.globalBestRoute, this.status);      // iteration best
            }
            if ((numIteration + 1) == numIterations){
                this.status = String.format("Iteration: %d    Global Best: %f%n", numIteration, this.globalBestDist);
                System.out.print(this.status);
                vis.updateVisualizationAntColony(this.globalBestRoute, this.globalBestRoute, this.status);                                // global best
            }

        }
        return this.globalBestRoute;
    }
}
