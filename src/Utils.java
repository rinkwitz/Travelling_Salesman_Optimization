import java.util.ArrayList;
import java.util.Random;

public class Utils {
    public static double BIG_CONST = 100000000000.0;
    public static double NEGATIVE_BIG_CONST = -100000000000.0;
    public static double TINY_CONST = 0.000001;
    public static Random randGen = new Random();

    public static double calcDistSums(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            dist += nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i));
        }
        return dist;
    }

    public static double calcDistSumsSquared(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            dist += Math.pow(nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i)), 2.0);
        }
        return dist;
    }

    public static double calcDistNegativeAntiproportionalSums(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            dist += nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i));
        }
        return -1 / dist;
    }

    public static double calcFitnessAntiproportionalSums(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            dist += nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i));
        }
        return 1 / dist;
    }

    public static int calcHammingDistance(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        int dist = 0;
        for (int i = 0; i < repr1.size(); i++) {
            if (repr1.get(i) != repr2.get(i)){
                dist += 1;
            }
        }
        return dist;
    }

    public static void showResult(ArrayList<Integer> result, ArrayList<TSNode> nodeList){
        String resultString = String.format("\nDistance: %f%nRoute: %s", Utils.calcDistSums(nodeList, result), result);
        System.out.println(resultString);
        System.out.println("\n\n\n");
    }
}
