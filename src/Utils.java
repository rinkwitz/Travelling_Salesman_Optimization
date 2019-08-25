import java.util.ArrayList;
import java.util.Random;

public class Utils {
    public static double BIG_CONST = 10.0;
    public static double TINY_CONST = 0.000001;
    public static Random randGen = new Random();

    public static double calcDistSums(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            dist += nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i));
        }
        return dist;
    }

    public static double calcDistSquaredSums(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            dist += Math.pow(nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i)), 2.0);
        }
        return dist;
    }

    public static void showResults(ArrayList<TSNode> nodeList, ArrayList<Integer> result){
        System.out.println(
                "\nDistance: " + Utils.calcDistSums(nodeList, result) + "\nRoute: " + result.toString());
        System.out.println("\n\n\n");
        //System.out.println("----------------------------------------------\n\n\n");
    }
}
