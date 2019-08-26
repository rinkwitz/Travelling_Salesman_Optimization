import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ant {
    private ArrayList<TSNode> nodeList;
    private int startId;
    private Random randGen = new Random();
    private ArrayList<Integer> allowedNodes;
    private ArrayList<Integer> TravelRoute;

    public Ant(ArrayList<TSNode> nodeList){
        this.nodeList = nodeList;
        this.startId = Utils.randGen.nextInt(this.nodeList.size());
    }

    private int getKeyMax(HashMap<Integer, Double> HMap){
        if (HMap.keySet().size() == 0){
            throw new IllegalArgumentException("Empty HashMap!");
        } else {
            int KeyMax = 0;
            double ValMax = -1.0;
            boolean first = true;
            for(int i:HMap.keySet()) {
                if (first){
                    KeyMax = i;
                    ValMax = HMap.get(i);
                    first = false;
                } else {
                    if (HMap.get(i) > ValMax){
                        KeyMax = i;
                        ValMax = HMap.get(i);
                    }
                }
            }
            return KeyMax;
        }
    }

    private int getNewProbPos(HashMap<Integer, Double> posProbs){
        double q = this.randGen.nextDouble();
        double sumProbs = 0.0;
        for (int pos:posProbs.keySet()){
            sumProbs += posProbs.get(pos);
            if (q < sumProbs){return pos;}
        }
        return posProbs.keySet().iterator().next();
    }

    private int stateTransition(double[][] pheromone, double q0, double beta, int pos){
        int newPos;
        if (this.randGen.nextDouble() < q0){
            HashMap<Integer, Double> posValues = new HashMap<>();
            for (int i:this.allowedNodes){posValues.put(i, pheromone[pos][i] *
                    Math.pow(1 / this.nodeList.get(pos).getDist(i), beta));}
            newPos = this.getKeyMax(posValues);
        } else {
            HashMap<Integer, Double> posProbs = new HashMap<>();
            double norm = 0.0;
            for (int i:this.allowedNodes){
                double val = pheromone[pos][i] * Math.pow(1 / this.nodeList.get(pos).getDist(i), beta);
                norm += val;
                posProbs.put(i, val);
            }
            for (int i:this.allowedNodes){
                double prob = posProbs.get(i) / norm;
                posProbs.replace(i, prob);
            }
            newPos = this.getNewProbPos(posProbs);
        }
        this.allowedNodes.remove(this.allowedNodes.indexOf(newPos));
        return newPos;
    }

    public void localPhermoneUpdate(double[][] pheromone, int oldPos, int pos, double rho, double tau0){
        pheromone[oldPos][pos] = (1 - rho) * pheromone[oldPos][pos] + rho * tau0;
    }

    public ArrayList<Integer> getTravelRoute(){
        return this.TravelRoute;
    }

    public void runTour(double[][] pheromone, double q0, double beta, double rho, double tau0){
        this.allowedNodes = new ArrayList<>();
        for (int i = 0; i < this.nodeList.size(); i++) {this.allowedNodes.add(i);}
        this.TravelRoute = new ArrayList<>();
        int pos = this.startId;
        this.TravelRoute.add(this.startId);
        this.allowedNodes.remove(this.allowedNodes.indexOf(this.startId));
        for (int step = 0; step < this.nodeList.size() - 1; step++) {
            int oldPos = pos;
            pos = this.stateTransition(pheromone, q0, beta, pos);
            this.TravelRoute.add(pos);
            this.localPhermoneUpdate(pheromone, oldPos, pos, rho, tau0);
        }
        this.TravelRoute.add(this.startId);
    }

}
