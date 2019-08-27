import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class GeneticAlgorithmOptimizer {
    private ArrayList<TSNode> nodeList;
    private int startId;
    private boolean showVisualization;
    private Random randGen = new Random();
    private String status;
    private int numIterations;
    private int populationSize;
    private int k;
    private int selectionSize;
    private ArrayList<ArrayList<Integer>> population;
    private ArrayList<Integer> allIds;
    private ArrayList<Integer> globalBestIndividual;
    private double globalBestIndividualFitness;

    public GeneticAlgorithmOptimizer(int numIterations, int populationSize, int k, int selectionSize, ArrayList<TSNode> nodeList, boolean showVisualization){
        this.numIterations = numIterations;
        this.populationSize = populationSize;
        this.k = k;
        this.selectionSize = selectionSize;
        this.nodeList = nodeList;
        this.startId = nodeList.get(0).getId();
        this.showVisualization = showVisualization;
        this.allIds = new ArrayList<>();
        for (int i = 0; i < this.populationSize; i++) {this.allIds.add(i);}
        this.globalBestIndividualFitness = Utils.NEGATIVE_BIG_CONST;
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

    private ArrayList<ArrayList<Integer>> getInitialPopulation(){
        ArrayList<ArrayList<Integer>> initPopulation = new ArrayList<>();
        for (int i = 0; i < this.populationSize; i++) {initPopulation.add(this.getInitialRoute());}
        return initPopulation;
    }

    private ArrayList<Integer> getRandomIds(ArrayList<ArrayList<Integer>> populationGroup, int k){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < populationGroup.size(); i++) {result.add(i);}
        Collections.shuffle(result);
        return new ArrayList<>(result.subList(0, k));
    }

    private int getChampionId(ArrayList<Integer> contestants){
        int bestId = 0;
        double bestFitness = Utils.NEGATIVE_BIG_CONST;
        for (int id:contestants){
            if (Utils.calcFitnessAntiproportionalSums(this.nodeList, this.population.get(id)) > bestFitness){
                bestId = id;
                bestFitness = Utils.calcFitnessAntiproportionalSums(this.nodeList, this.population.get(id));
            }
        }
        return bestId;
    }

    private ArrayList<ArrayList<Integer>> getTournamentSelection(){
        ArrayList<ArrayList<Integer>> selection = new ArrayList<>();
        for (int i = 0; i < this.selectionSize; i++) {
            ArrayList<Integer> contestants = this.getRandomIds(this.population, this.k);
            selection.add(this.population.get(this.getChampionId(contestants)));
        }
        return selection;
    }

    private int calcDiversity(){
        int diversity = 0;
        for (int i = 0; i < this.populationSize; i++) {
            for (int j = 0; j < this.populationSize; j++) {
                diversity += Utils.calcHammingDistance(this.population.get(i), this.population.get(j));
            }
        }
        return 0;
    }

    public ArrayList<Integer> solve(){
        System.out.println("----------------------------------------------");
        System.out.println("--- Genetic Algorithm:\n");
        Visualization vis = new Visualization("Genetic Algorithm", this.nodeList, 500, this.showVisualization);

        // Initialize Population:
        this.population = this.getInitialPopulation();

        for (int numIteration = 0; numIteration < this.numIterations; numIteration++) {
            // Evaluation:
            HashMap<Integer, Double> idxFitness = new HashMap<>();
            for (int id = 0; id < this.populationSize; id++) {
                idxFitness.put(id, Utils.calcFitnessAntiproportionalSums(this.nodeList, this.population.get(id)));
            }

            int iterationBestId = this.getChampionId(this.allIds);
            double iterationBestFitness = Utils.calcFitnessAntiproportionalSums(this.nodeList, this.population.get(iterationBestId));

            if (iterationBestFitness > this.globalBestIndividualFitness){
                this.globalBestIndividual = new ArrayList<>(this.population.get(iterationBestId));
                this.globalBestIndividualFitness = iterationBestFitness;
            }

            // optional visualization:
            if (numIteration + 1 == this.numIterations){
                this.status = String.format("Iteration: %d    Global Best Fitness: %f",
                        numIteration, this.globalBestIndividualFitness);
                System.out.println(this.status);
                vis.updateVisualizationGeneticAlgorithm(this.globalBestIndividual, this.globalBestIndividual,
                        this.status);
            } else if (numIteration % 10 == 0) {
                this.status = String.format("Iteration: %d    Iteration Best Fitness: %f",
                        numIteration, iterationBestFitness);
                System.out.println(this.status);
                vis.updateVisualizationGeneticAlgorithm(this.population.get(iterationBestId), this.globalBestIndividual,
                        this.status);
            }

            // Selection:
            ArrayList<ArrayList<Integer>> selection = this.getTournamentSelection();

            // Reproduction:
            ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<>();
            for (int numOffspring = 0; numOffspring < this.populationSize; numOffspring++) {
                ArrayList<Integer> parentIds = this.getRandomIds(selection, 2);
                //int crossoverMethod = this.randGen.nextInt(2);   // !!!!!  change to 4
                int crossoverMethod = 1;
                switch (crossoverMethod){
                    case 0:
                        // not working !!!
                        // to be fixed ...
                        newPopulation.add(Crossover.PartiallyMappedCrossover(selection.get(parentIds.get(0)),
                                selection.get(parentIds.get(1))));
                        break;
                    case 1:
                       newPopulation.add(Crossover.OrderCrossover(selection.get(parentIds.get(0)),
                                selection.get(parentIds.get(1))));
                        break;
                    case 2:
                        newPopulation.add(Crossover.CycleCrossover(selection.get(parentIds.get(0)),
                                selection.get(parentIds.get(1))));
                        break;
                    case 3:
                        newPopulation.add(Crossover.EdgeRecombinationCrossover(selection.get(parentIds.get(0)),
                                selection.get(parentIds.get(1))));
                        break;
                }
            }
            this.population = newPopulation;

            // Mutation:
            ArrayList<ArrayList<Integer>> mutatedPopulation = new ArrayList<>();
            for (int id = 0; id < this.populationSize; id++) {
                int mutationMethod = this.randGen.nextInt(5);
                switch (mutationMethod){
                    case 0:
                        mutatedPopulation.add(new ArrayList<>(this.population.get(id)));
                        break;
                    case 1:
                        mutatedPopulation.add(Mutation.ReciprocalExchange(this.population.get(id)));
                        break;
                    case 2:
                        mutatedPopulation.add(Mutation.Insertion(this.population.get(id)));
                        break;
                    case 3:
                        mutatedPopulation.add(Mutation.Inversion(this.population.get(id)));
                        break;
                    case 4:
                        mutatedPopulation.add(Mutation.Displacement(this.population.get(id)));
                        break;
                }
            }
            this.population = mutatedPopulation;
        }
        return this.globalBestIndividual;
    }
}
