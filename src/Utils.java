import java.util.ArrayList;
import java.util.Random;

public class Utils {
    public static double BIG_CONST = 10.0;
    public static double TINY_CONST = 0.000001;
    public static Random randGen = new Random();

    public static double calcDistv1(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            if (!nodeList.get(TravelRoute.get(i-1)).getDistMap().containsKey(TravelRoute.get(i))) {
                dist += BIG_CONST;
                continue;
            }
            dist += nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i));
        }
        return dist;
    }

    public static double calcDistv2(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            if (!nodeList.get(TravelRoute.get(i-1)).getDistMap().containsKey(TravelRoute.get(i))) {
                dist += BIG_CONST;
                continue;
            }
            dist += Math.pow(nodeList.get(TravelRoute.get(i-1)).getDist(TravelRoute.get(i)), 2.0);
        }
        return dist;
    }

    public static double calcDistv3(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        double dist = 0.0;
        for (int i = 1; i < TravelRoute.size(); i++) {
            if (!nodeList.get(TravelRoute.get(i-1)).getDistMap().containsKey(TravelRoute.get(i))) {
                dist += BIG_CONST;
                continue;
            }
            dist += Math.pow(nodeList.get(TravelRoute.get(i-1)).getDistMap().get(TravelRoute.get(i)) *
                    (nodeList.get(TravelRoute.get(i-1)).getIdxOrder().indexOf(TravelRoute.get(i)) + 1), 2.0);
        }
        return dist;
    }

    public static double calcNoise(int numIteration, int numIterations, double startSigma){
        return (Utils.randGen.nextGaussian() - 0.5) * startSigma * numIteration / numIterations;
    }

    public static boolean isTravelable(ArrayList<TSNode> nodeList, ArrayList<Integer> TravelRoute){
        boolean travelable = true;
        for (int i = 1; i < TravelRoute.size(); i++) {
            if (!nodeList.get(TravelRoute.get(i-1)).getDistMap().containsKey(TravelRoute.get(i))) {
                travelable = false;
                break;
            }
        }
        return travelable;
    }
}
