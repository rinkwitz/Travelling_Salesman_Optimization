import java.util.ArrayList;

public class TravellingSalesman {
    public static void main(String[] args) {
        int numNodes = 300;
        boolean showVisualization = true;
        TSNodeGenerator TSGen = new TSNodeGenerator(numNodes, "euclidean");
        ArrayList<TSNode> nodeList = TSGen.generate();

        // Particle Swarm Optimization:
        /*ParticleSwarmOptimizer optimizer1 = new ParticleSwarmOptimizer(nodeList, showVisualization);
        ArrayList<Integer> result = optimizer1.solve();
        Utils.showResult(result, nodeList);*/

        // Simulated Annealing:
        int numIterations = 2000000;
        double startTemperature = 50.0;
        SimulatedAnnealingOptimizer optimizer2 = new SimulatedAnnealingOptimizer(numIterations, startTemperature,
                nodeList, showVisualization);
        ArrayList<Integer> result = optimizer2.solve();
        Utils.showResult(result, nodeList);

        // Ant Colony:
        /*AntColonyOptimizer optimizer3 = new AntColonyOptimizer(nodeList, showVisualization);
        result = optimizer3.solve();
        Utils.showResult(result, nodeList);*/

    }
}
