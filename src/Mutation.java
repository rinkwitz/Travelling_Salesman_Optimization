import java.util.*;

public class Mutation {
    public static Random randGen = new Random();

    public static ArrayList<Integer> ReciprocalExchange(ArrayList<Integer> repr){
        ArrayList<Integer> result = new ArrayList<>(repr);
        boolean idxFound = false;
        int idx1 = 1 + randGen.nextInt(result.size()-2);
        int idx2 = 1 + randGen.nextInt(result.size()-2);
        int mem = result.get(idx1);
        result.set(idx1, result.get(idx2));
        result.set(idx2, mem);
        return result;
    }

    public static ArrayList<Integer> Insertion(ArrayList<Integer> repr){
        ArrayList<Integer> result = new ArrayList<>(repr);
        boolean idxFound = false;
        int idx1 = 1 + randGen.nextInt(result.size()-2);
        int mem = result.remove(idx1);
        int idx2 = 1 + randGen.nextInt(result.size()-2);
        result.add(idx2, mem);
        return result;
    }

    public static ArrayList<Integer> Inversion(ArrayList<Integer> repr){
        ArrayList<Integer> copy = new ArrayList<>(repr);
        int idx1 = 1 + randGen.nextInt(copy.size()-2);
        int idx2 = 1 + randGen.nextInt(copy.size()-2);
        List<Integer> subTour = copy.subList(Math.min(idx1, idx2), Math.max(idx1, idx2));
        Collections.reverse(subTour);
        ArrayList<Integer> result = new ArrayList<>(copy.subList(0, Math.min(idx1, idx2)));
        result.addAll(subTour);
        result.addAll(copy.subList(Math.max(idx1, idx2), copy.size()));
        return result;
    }

    public static ArrayList<Integer> Displacement(ArrayList<Integer> repr){
        ArrayList<Integer> copy = new ArrayList<>(repr);
        int idx1 = 1 + randGen.nextInt(copy.size()-2);
        int idx2 = 1 + randGen.nextInt(copy.size()-2);
        List<Integer> subTour = copy.subList(Math.min(idx1, idx2), Math.max(idx1, idx2));
        ArrayList<Integer> copy2 = new ArrayList<>(copy.subList(0, Math.min(idx1, idx2)));
        copy2.addAll(copy.subList(Math.max(idx1, idx2), copy.size()));
        int idxInsert = 1 + randGen.nextInt(copy2.size() - 2);
        ArrayList<Integer> result = new ArrayList<>(copy2.subList(0, idxInsert));
        result.addAll(subTour);
        result.addAll(copy2.subList(idxInsert, copy2.size()));
        return result;
    }
}
