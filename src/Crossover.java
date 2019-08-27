import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Crossover {
    public static Random randGen = new Random();

    public static ArrayList<Integer> PartiallyMappedCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        int idx1 = 1 + randGen.nextInt(repr1.size()-2);
        int idx2 = 1 + randGen.nextInt(repr1.size()-2);
        ArrayList<Integer> subList1 = new ArrayList<>(repr1.subList(Math.min(idx1, idx2), Math.max(idx1, idx2)));
        ArrayList<Integer> subList2 = new ArrayList<>(repr2.subList(Math.min(idx1, idx2), Math.max(idx1, idx2)));
        ArrayList<Integer> offspring = new ArrayList<>();
        HashMap<Integer, Integer> mapping1to2 = new HashMap<>();
        HashMap<Integer, Integer> mapping2to1 = new HashMap<>();
        for (int i = 0; i < subList1.size(); i++) {
            mapping1to2.put(subList1.get(i), subList2.get(i));
            mapping2to1.put(subList2.get(i), subList1.get(i));
        }
        for (int i = 0; i < repr1.size(); i++) {
            if (i < Math.min(idx1, idx2) || Math.max(idx1, idx2) <= i) {offspring.add(repr1.get(i));}
            else {offspring.add(subList2.get(i - Math.min(idx1, idx2)));}
        }
        for (int i = 0; i < offspring.size(); i++) {
            if ((i < Math.min(idx1, idx2) || Math.max(idx1, idx2) <= i) && subList2.contains(offspring.get(i))) {
                if (!offspring.contains(mapping2to1.get(offspring.get(i)))){offspring.set(i, mapping2to1.get(offspring.get(i)));}
                else {offspring.set(i, mapping1to2.get(offspring.get(i)));}
            }
        }

        // optional check:
        if (offspring.size() != repr1.size()){
            System.out.println("size off");
        }
        System.out.print("not contains: ");
        for (int i = 0; i < repr1.size(); i++) {
            if (!offspring.contains(repr1.get(i))){
                System.out.print(repr1.get(i) + " id: " + i + " " + idx1 + " " + idx2);
            }
        }
        if ((offspring.get(0) != repr1.get(0)) || (offspring.get(offspring.size()-1) != repr1.get(repr1.size()-1))){
            System.out.println("start end wrong");
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

    public static ArrayList<Integer> CycleCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        ArrayList<Integer> offspring = new ArrayList<>();
        // ...
        return offspring;
    }

    public static ArrayList<Integer> EdgeRecombinationCrossover(ArrayList<Integer> repr1, ArrayList<Integer> repr2){
        ArrayList<Integer> offspring = new ArrayList<>();
        // ...
        return offspring;
    }
}
