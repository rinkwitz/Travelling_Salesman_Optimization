import java.util.ArrayList;

public class TravellingSalesman {
    public static void main(String[] args) {
        int numNodes = 1000;
        boolean showVisualization = true;
        TSNodeGenerator TSGen = new TSNodeGenerator(numNodes, "euclidean", "all");
        ArrayList<TSNode> nodeList = TSGen.generate();
        SimulatedAnnealingOptimizer optimizer = new SimulatedAnnealingOptimizer(1000000, 50.0,
                nodeList, showVisualization);
        ArrayList<Integer> TravelRoute = optimizer.solve();
        System.out.println("\nNumber of Hops: " + (TravelRoute.size()-1) +
                "\nDistance: " + Utils.calcDistL2(nodeList, TravelRoute) +
                "\nRoute: " + TravelRoute.toString() +
                "\nTravelable: " + Utils.isTravelable(nodeList, TravelRoute));
    }
}
