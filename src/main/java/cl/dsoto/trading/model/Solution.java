package cl.dsoto.trading.model;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by des01c7 on 22-03-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_solution")
public class Solution implements Serializable {

    @Column(name="id_optimization")
    Optimization optimization;

    @Persistent(mappedBy="solution")
    List<Value> values;

    public Solution(Optimization optimization, List<Value> values) {

        this.optimization = optimization;
        this.values = values;
    }

    public Optimization getOptimization() {
        return optimization;
    }

    public void setOptimization(Optimization optimization) {
        this.optimization = optimization;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setSolution(List<Value> values) {
        this.values = values;
    }
}
