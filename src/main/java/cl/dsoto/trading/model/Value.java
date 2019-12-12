package cl.dsoto.trading.model;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Created by des01c7 on 11-12-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_value")
public class Value {

    @Column(name="id_solution")
    Solution solution;

    Comparable value;

    public Value(Solution solution, Comparable value) {
        this.solution = solution;
        this.value = value;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Comparable getValue() {
        return value;
    }

    public void setValue(Comparable value) {
        this.value = value;
    }
}
