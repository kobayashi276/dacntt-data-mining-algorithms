import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


//This class is used for storing an uncerntain database
public class UD {
    private List<Item> UD = new ArrayList<>();
    private Map<String, Double> uniqueProbability = new HashMap<>();

    public UD(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                String[] tempItem = line.split(" ");

                this.UD.add(new Item(tempItem[0]));
            }

            List<Set<String>> tempDataset = removeProbFromUD();
            Set<String> tempUnique = new HashSet<>();
            for (Set<String> transaction : tempDataset) {
                for (String item : transaction) {
                    tempUnique.add(item);
                }
            }

            for (String t: tempUnique){
                this.uniqueProbability.put(t, probRandom());
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double probRandom(){
        Random random = new Random();
        return Double.parseDouble(String.format("%.2f", random.nextDouble()));
    }

    //Seperate the prob from the UD
    public List<Set<String>> removeProbFromUD(){
        List<Set<String>> res = new ArrayList<>();
        
        for (Item u : this.UD){
            res.add(u.getItem());
        }

        return res;
    }

    public Map<String, Double> getProbability(){
        return this.uniqueProbability;
    }

    public List<Item> getUD(){
        return this.UD;
    }

    public String toString(){
        for (Item t : UD){
            System.out.println(t.toString());
        }
        System.out.println("Probability: " + this.uniqueProbability);
        return "";
    }
}
