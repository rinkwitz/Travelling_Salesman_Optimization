import java.util.ArrayList;

public class TravellingSalesman {
    public static void main(String[] args) {
        int numNodes = 100;
        boolean showVisualization = true;
        TSNodeGenerator TSGen = new TSNodeGenerator(numNodes, "euclidean");
        ArrayList<TSNode> nodeList = TSGen.generate();

        // Simulated Annealing:
        int numIterations = 2000000;
        double startTemperature = 50.0;
        SimulatedAnnealingOptimizer optimizer1 = new SimulatedAnnealingOptimizer(numIterations, startTemperature,
                nodeList, showVisualization);
        ArrayList<Integer> result = optimizer1.solve();
        Utils.showResult(result, nodeList);

        // Ant Colony:
        int numAnts = 200;
        numIterations = 1000;
        double beta = 2.0;
        double q0 = 0.9;
        double alpha = 0.1;
        double rho = 0.1;
        double tau0 = 1 / (numNodes * 13.0);
        AntColonyOptimizer optimizer2 = new AntColonyOptimizer(numAnts, numIterations, beta, q0, alpha, rho, tau0,
                nodeList, showVisualization);
        result = optimizer2.solve();
        Utils.showResult(result, nodeList);

    }
}
