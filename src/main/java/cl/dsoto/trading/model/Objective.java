package cl.dsoto.trading.model;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;

/**
 * Created by des01c7 on 22-03-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_objective")
public class Objective implements Serializable {

    Optimization optimization;
    double objective;

    public Objective(Optimization optimization, double objective) {
        this.optimization = optimization;
        this.objective = objective;
    }

    public Optimization getOptimization() {
        return optimization;
    }

    public void setOptimization(Optimization optimization) {
        this.optimization = optimization;
    }

    public double getObjective() {
        return objective;
    }

    public void setObjective(double objective) {
        this.objective = objective;
    }
}
