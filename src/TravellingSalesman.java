import java.util.ArrayList;

public class TravellingSalesman {
    public static void main(String[] args) {
        int numNodes = 50;
        boolean showVisualization = true;
        TSNodeGenerator TSGen = new TSNodeGenerator(numNodes, "euclidean");
        ArrayList<TSNode> nodeList = TSGen.generate();

        // Particle Swarm Optimization:

        // Simulated Annealing:
        int numIterations = 1000000;
        double startTemperature = 50.0;
        SimulatedAnnealingOptimizer optimizer = new SimulatedAnnealingOptimizer(numIterations, startTemperature,
                nodeList, showVisualization);
        ArrayList<Integer> result = optimizer.solve();
        Utils.showResults(nodeList, result);

        // Ant Colony:

    }
}
