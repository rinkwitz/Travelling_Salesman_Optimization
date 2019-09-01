import java.util.*;

public class Crossover {
    public static Random randGen = new Random();

    private static int getMappedPosition(ArrayList<Integer> offspring, HashMap<Integer, Integer> mapping2to1, int pos){
        int mappedPosition = mapping2to1.get(pos);
        if (!offspring.contains(mappedPosition)){return mappedPosition;}
        else {return Crossover.getMappedPosition(offspring, mapping2to1, mappedPosition);}
    }

    private static int getCyclePosition (){
        return 0;
    }

    public static ArrayList<Integer> PartiallyMappedCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        int idx1 = 1 + randGen.nextInt(repr1.size()-2);
        int idx2 = 1 + randGen.nextInt(repr1.size()-2);
        ArrayList<Integer> subList1 = new ArrayList<>(repr1.subList(Math.min(idx1, idx2), Math.max(idx1, idx2)));
        ArrayList<Integer> subList2 = new ArrayList<>(repr2.subList(Math.min(idx1, idx2), Math.max(idx1, idx2)));
        ArrayList<Integer> offspring = new ArrayList<>();
        HashMap<Integer, Integer> mapping2to1 = new HashMap<>();
        for (int i = 0; i < subList1.size(); i++) {
            mapping2to1.put(subList2.get(i), subList1.get(i));
        }
        for (int i = 0; i < repr1.size(); i++) {
            if (i < Math.min(idx1, idx2) || Math.max(idx1, idx2) <= i) {offspring.add(-1);}
            else {offspring.add(subList2.get(i - Math.min(idx1, idx2)));}
        }
        for (int i = 0; i < offspring.size(); i++) {
            if ((i == 0) || (i + 1 == repr1.size())){offspring.set(i, repr1.get(i));}
            else if ((i < Math.min(idx1, idx2) || Math.max(idx1, idx2) <= i) && !offspring.contains(repr1.get(i))) {
                offspring.set(i, repr1.get(i));
            }
            else if ((i < Math.min(idx1, idx2) || Math.max(idx1, idx2) <= i) && offspring.contains(repr1.get(i))) {
                offspring.set(i, Crossover.getMappedPosition(offspring, mapping2to1, repr1.get(i)));
            }
        }
        return offspring;
    }

    public static ArrayList<Integer> CycleCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        ArrayList<Integer> cycles = new ArrayList<>();
        for (int i = 0; i < repr1.size(); i++) {cycles.add(-1);}
        int idCycle = 0;
        while (cycles.contains(-1)){
            int startIdx = cycles.indexOf(-1);
            int startNum = repr1.get(startIdx);
            int currentIdx = startIdx;
            cycles.set(startIdx, idCycle);
            while (repr2.get(currentIdx) != startNum){
                currentIdx = repr1.indexOf(repr2.get(currentIdx));
                cycles.set(currentIdx, idCycle);
            }
            idCycle++;
        }
        ArrayList<Integer> offspring = new ArrayList<>();
        for (int i = 0; i < repr1.size(); i++) {offspring.add(-1);}
        for (int id = 0; id < idCycle; id++) {
            if ((id % 2) == 0){
                for (int j = 0; j < cycles.size(); j++) {
                    if (cycles.get(j) == id){offspring.set(j, repr1.get(j));}
                }
            } else {
                for (int j = 0; j < cycles.size(); j++) {
                    if (cycles.get(j) == id){offspring.set(j, repr2.get(j));}
                }
            }
        }
        return offspring;
    }

    public static ArrayList<Integer> OrderCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        int idx1 = 1 + randGen.nextInt(repr1.size()-2);
        int idx2 = 1 + randGen.nextInt(repr1.size()-2);
        ArrayList<Integer> subList = new ArrayList<>(repr1.subList(Math.min(idx1, idx2), Math.max(idx1, idx2)));
        ArrayList<Integer> numsRepr1Outer = new ArrayList<>(repr1.subList(0, Math.min(idx1, idx2)));
        numsRepr1Outer.addAll(repr1.subList(Math.max(idx1, idx2), repr1.size()));
        ArrayList<Integer> prefix = new ArrayList<>();
        ArrayList<Integer> suffix = new ArrayList<>();
        for (int num2:repr2){
            if (numsRepr1Outer.contains(num2)){
                if (prefix.size() < Math.min(idx1, idx2)){
                    prefix.add(num2);
                } else {
                    suffix.add(num2);
                }
            }
        }
        ArrayList<Integer> offspring = new ArrayList<>(prefix);
        offspring.addAll(subList);
        offspring.addAll(suffix);
        return offspring;
    }

    private static ArrayList<HashSet<Integer>> getEdgeMap (ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        ArrayList<HashSet<Integer>> edgeMap = new ArrayList<>();
        for (int i = 0; i < repr1.size(); i++) {
            edgeMap.add(new HashSet<>());
            int id = repr1.indexOf(i);
            for (int j: new int[] {-1, 1}) {
                if (i != repr1.get(Utils.intMod(id + j, repr1.size()))){
                    edgeMap.get(i).add(repr1.get(Utils.intMod(id + j, repr1.size())));
                }
            }
            id = repr2.indexOf(i);
            for (int j: new int[] {-1, 1}) {
                if (i != repr2.get(Utils.intMod(id + j, repr2.size()))){
                    edgeMap.get(i).add(repr2.get(Utils.intMod(id + j, repr2.size())));
                }
            }
        }
        return edgeMap;
    }

    private static int getFewestNeighbour(ArrayList<HashSet<Integer>> edgeMap, int currentCity){
        HashSet<Integer> neighbours = edgeMap.get(currentCity);
        HashMap<Integer, Integer> neighbourListSizeMap = new HashMap<>();
        for (int neighbourId:neighbours) {neighbourListSizeMap.put(neighbourId, edgeMap.get(neighbourId).size());}
        int min = Collections.min(neighbourListSizeMap.values());
        ArrayList<Integer> possiblePos = new ArrayList<>();
        for (int neighbour:neighbours) {
            if (neighbourListSizeMap.get(neighbour) == min){possiblePos.add(neighbour);}
        }
        Collections.shuffle(possiblePos);
        return possiblePos.get(0);
    }

    public static ArrayList<Integer> EdgeRecombinationCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        ArrayList<Integer> offspring = new ArrayList<>();
        ArrayList<HashSet<Integer>> edgeMap = Crossover.getEdgeMap(repr1, repr2);
        int currentCity = repr1.get(0);
        offspring.add(currentCity);
        while (!offspring.containsAll(repr1)){
            for (int i = 0; i < edgeMap.size(); i++) {edgeMap.get(i).remove(currentCity);}
            if (edgeMap.get(currentCity).size() == 0){
                ArrayList<Integer> possiblePos = new ArrayList<>(repr1);
                possiblePos.removeAll(offspring);
                Collections.shuffle(possiblePos);
                currentCity = possiblePos.get(0);
            } else {
                currentCity = Crossover.getFewestNeighbour(edgeMap, currentCity);
            }
            offspring.add(currentCity);
        }
        offspring.add(repr1.get(0));
        return offspring;
    }

}
