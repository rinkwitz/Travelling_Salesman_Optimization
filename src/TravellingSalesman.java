import java.util.ArrayList;

public class TravellingSalesman {
    public static void main(String[] args) {
        int numNodes = 100;
        boolean showVisualization = true;
        TSNodeGenerator TSGen = new TSNodeGenerator(numNodes, "euclidean");
        ArrayList<TSNode> nodeList = TSGen.generate();

        // Simulated Annealing:
        int numIterations1 = 2000000;
        double startTemperature = 50.0;
        SimulatedAnnealingOptimizer optimizer1 = new SimulatedAnnealingOptimizer(numIterations1, startTemperature,
                nodeList, showVisualization);
        ArrayList<Integer> result1 = optimizer1.solve();
        Utils.showResult(result1, nodeList);

        // Ant Colony:
        int numAnts = 200;
        int numIterations2 = 1000;
        double beta = 2.0;
        double q0 = 0.9;
        double alpha = 0.1;
        double rho = 0.1;
        double tau0 = 1 / (numNodes * 13.0);
        AntColonyOptimizer optimizer2 = new AntColonyOptimizer(numAnts, numIterations2, beta, q0, alpha, rho, tau0,
                nodeList, showVisualization);
        ArrayList<Integer> result2 = optimizer2.solve();
        Utils.showResult(result2, nodeList);

        // Genetic Algorithm:
        int numIterations3 = 20000;
        int populationSize = 100;
        int k = 8;
        int selectionSize = 20;
        GeneticAlgorithmOptimizer optimizer3 = new GeneticAlgorithmOptimizer(numIterations3, populationSize, k,
                selectionSize, nodeList, showVisualization);
        ArrayList<Integer> result3 = optimizer3.solve();
        Utils.showResult(result3, nodeList);
    }
}
