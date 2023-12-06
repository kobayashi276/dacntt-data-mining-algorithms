import java.util.HashSet;
import java.util.Set;

public class C {
    private Set<String> set = new HashSet<>();
    private double E;
    private double Var;
    private int j;

    public C(Set<String> set, double E, double Var, int j){
        this.set = set;
        this.E = E;
        this.Var = Var;
        this.j = j;
    }

    public Set<String> getSet(){
        return this.set;
    }

    public double getE(){
        return this.E;
    }

    public double getVar(){
        return this.Var;
    }

    public int getJ(){
        return this.j;
    }

    public String toString(){
        return this.set.toString() + ", " + this.E + ", " + this.Var + ", " + this.j ;
    }
}
