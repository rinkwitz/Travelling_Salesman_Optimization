import java.util.*;
import java.util.Collections;

public class SimulatedAnnealingOptimizer {
    private int numIterations;
    private double startTemperature;
    private ArrayList<TSNode> nodeList;
    private int startId;
    private boolean showVisualization;
    private Random randGen = new Random();


    public SimulatedAnnealingOptimizer(int numIterations, double startTemperature, ArrayList<TSNode> nodeList,
                                       boolean showVisualization){
        this.numIterations = numIterations;
        this.startTemperature = startTemperature;
        this.nodeList = nodeList;
        this.startId = nodeList.get(0).getId();
        this.showVisualization = showVisualization;
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
        // L2-Norm:
        return Utils.calcDistv2(this.nodeList, TravelRoute);

        //L1-Norm:
        //return Utils.calcDistv1(this.nodeList, TravelRoute);

        // L2-Norm with Noise:
        //double startSigma = 0.0001;
        //return Utils.calcDistv2(this.nodeList, TravelRoute) + Utils.calcNoise(numIteration, this.numIterations, startSigma);

        // per node weighted distance:
        //return Utils.calcDistv3(this.nodeList, TravelRoute);
    }

    private double getTemperature(int numIteration){
        // linear:
        //return this.startTemperature -  numIteration * this.startTemperature / this.numIterations;

        // exponential:
        return this.startTemperature * Math.exp(Math.log(Utils.TINY_CONST / this.startTemperature)
                / this.numIterations * numIteration);

        // exponential waves:
        /*if (numIteration < this.numIterations / 3){
            return this.startTemperature * Math.exp(Math.log(Utils.TINY_CONST / this.startTemperature)
                        / (this.numIterations / 3) * numIteration);
        } else if (this.numIterations / 3 <= numIteration&& numIteration < this.numIterations * 2 / 3){
            return this.startTemperature * Math.exp(Math.log(Utils.TINY_CONST / this.startTemperature)
                    / (this.numIterations / 3) * (numIteration - this.numIterations / 3));
        } else {
            return this.startTemperature * Math.exp(Math.log(Utils.TINY_CONST / this.startTemperature)
                    / (this.numIterations / 3) * (numIteration - this.numIterations * 2 / 3));
        }*/

    }

    private ArrayList<Integer> getNeighbour(ArrayList<Integer> TravelRoute){
        ArrayList<Integer> neighbour = new ArrayList<>();
        neighbour.addAll(TravelRoute);

        // first method using Permutations:
        int numPermutations = 1;
        for (int i = 0; i < numPermutations; i++) {
            boolean idxFound = false;
            int idx1 = 0, idx2 = 0;
            while (!idxFound) {
                idx1 = 1 + randGen.nextInt(neighbour.size()-2);
                idx2 = 1 + randGen.nextInt(neighbour.size()-2);
                if (idx1 != idx2 && this.nodeList.get(neighbour.get(idx1)).getDistMap().containsKey(neighbour.get(idx2))
                && this.nodeList.get(neighbour.get(idx2)).getDistMap().containsKey(neighbour.get(idx1))){
                    idxFound = true;
                }
            }
            int mem = neighbour.get(idx1);
            neighbour.set(idx1, neighbour.get(idx2));
            neighbour.set(idx2, mem);
        }

        // method 2: permutations of neighboring pairs
        /*int numPermutations = 20;
        for (int i = 0; i < numPermutations; i++) {
            boolean idxFound = false;
            int idx = 1 + randGen.nextInt(neighbour.size()-2);
            int mem = neighbour.get(idx);
            neighbour.set(idx, neighbour.get(idx + 1));
            neighbour.set(idx + 1, mem);
        }*/

        // method 3: subsequence shuffling:
        /*int idx1 = randGen.nextInt(neighbour.size() - 2);
        int idx2 = 1 + idx1 + randGen.nextInt(neighbour.size() - 1 - idx1);
        idx1++;
        List<Integer> subList = neighbour.subList(idx1, idx2);
        Collections.shuffle(subList);
        ArrayList<Integer> result = new ArrayList<>();
        result.addAll(neighbour.subList(0, idx1));
        result.addAll(subList);
        result.addAll(neighbour.subList(idx2, neighbour.size()));
        neighbour = result;*/

        // method 4: shuffle all:
        /*int mem = neighbour.get(0);
        neighbour.remove(0);
        neighbour.remove(neighbour.size()-1);
        Collections.shuffle(neighbour);
        neighbour.add(0, mem);
        neighbour.add(mem);*/


        return neighbour;
    }

    public ArrayList<Integer> solve(){
        ArrayList<Integer> TravelRoute = this.getInitialRoute();
        Visualization vis = new Visualization(this.nodeList, 500, this.showVisualization);
        if (this.showVisualization){
            vis.updateVisualization(TravelRoute);
        }
        for (int numIteration = 0; numIteration < this.numIterations; numIteration++) {
            ArrayList<Integer> neighbour = this.getNeighbour(TravelRoute);
            double prob = Math.min(1.0,
                    Math.exp(-(this.getCost(neighbour, numIteration)-this.getCost(TravelRoute, numIteration))/this.getTemperature(numIteration)));
            if (randGen.nextDouble() < prob){
                TravelRoute = neighbour;
            }
            // optional output:
            if (numIteration % 1000 == 0 || (numIteration + 1) == this.numIterations){
                vis.updateVisualization(TravelRoute);
                System.out.format(Locale.CANADA,"%d:    Cost: %f    " +
                            "Temp: %f    Travelable: %b%n",
                    numIteration, Utils.calcDistv2(this.nodeList, TravelRoute), this.getTemperature(numIteration),
                    Utils.isTravelable(this.nodeList, TravelRoute));
            }
        }
        return TravelRoute;
    }

}
