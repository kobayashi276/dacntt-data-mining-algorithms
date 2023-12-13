import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    // CGEB algorithms
    public static List<C> CGEBFucntion(UD UD, int minsup, double minpro) {
        List<Set<String>> F = new ArrayList<>();
        Set<String> elements = new HashSet<>();

        // Seperate the prob out of UD
        List<Set<String>> D = UD.removeProbFromUD();

        // Get unique data
        for (Set<String> transaction : D) {
            for (String item : transaction) {
                elements.add(item);
            }
        }

        // Init the first set from the unique data (F)
        for (String e : elements) {
            Set<String> itemSet = new HashSet<>();
            itemSet.add(e);
            F.add(itemSet);
        }

        List<C> result = new ArrayList<>();
        Set<String> varList = new HashSet<>();

        // Start checking (F) if it contains in database
        while (true) {
            List<Set<String>> L = new ArrayList<>();
            for (Set<String> f : F) {
                int count = 0;
                double prob = 0;
                int temp = 0;
                double var = 0;
                for (Set<String> transaction : D) {
                    if (transaction.containsAll(f)) {
                        prob += UD.getProb(temp);
                        var += UD.getProb(temp) * (1 - UD.getProb(temp));
                        count++;
                    }
                    temp++;
                    // If meet requirements, add this set to result (minsup and lb(E(f)))
                    if (count >= minsup && prob >= lb(minsup, minpro)) {
                        // Set<String> ff = f;
                        // String tempS = String.format("%.2f", prob*(1-prob));
                        // System.out.println(tempS);
                        // ff.add(tempS);
                        result.add(new C(f, Double.parseDouble(String.format("%.2f", prob / D.size())), var, temp));
                        varList.add(String.format("%.5f", var));
                        break;
                    }
                }
                L.add(f);
            }

            // If L not empty, start union each other between L and unique set
            if (L.isEmpty()) {
                return result;
            } else {
                F = generateSet(L, elements);
            }
        }
        // result.add(varList);

    }

    // APFI-MAX
    private static List<C> APFI_MAX(UD UD, int minsup, double minpro) {
        List<C> C = CGEBFucntion(UD, minsup, minpro);

        for (int i = C.size() - 1; i >= 0; i--) {
            Set<String> subC = C.get(i).getSet();
            for (String string : subC) {
                continue;
            }
        }

        return C;
    }

    // Generation the union set from 2 set
    private static List<Set<String>> generateSet(List<Set<String>> A, Set<String> B) {
        List<Set<String>> result = new ArrayList<>();
        List<String> bList = new ArrayList<>(B);

        for (Set<String> a : A) {
            List<String> aList = new ArrayList<>(a);
            int index = bList.indexOf(aList.get(aList.size() - 1));
            for (int j = index + 1; j < bList.size(); j++) {
                Set<String> newSet = new HashSet<>(a);
                newSet.add(bList.get(j));
                result.add(newSet);
            }
        }
        return result;
    }

    private static double lb(int T, double minpro) {
        return (2 * T - Math.log(minpro) - Math.sqrt(Math.pow(Math.log(minpro), 2) - 8 * Math.log(minpro))) / 2;
    }

    public static void main(String[] args) {
        // Read data
        UD UD = new UD("data.txt");

        System.out.println(UD.removeProbFromUD());
        List<C> res = APFI_MAX(UD, 1, 0.3);
        for (C set : res) {
            System.out.println(set);
        }
    }
}
