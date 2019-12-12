package cl.dsoto.trading.model;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by des01c7 on 22-03-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_optimization")
public class Optimization implements Serializable {


    @Column(name="id_period")
    private Period period;

    @Column(name="id_strategy")
    private Strategy strategy;

    private Timestamp timestamp;

    @Persistent(mappedBy="optimization")
    private List<Objective> objectives;

    @Persistent(mappedBy="optimization")
    private List<Solution> solutions;

    public Optimization(Period period, Strategy strategy, Timestamp timestamp, List<Objective> objectives, List<Solution> solutions) {
        this.period = period;
        this.strategy = strategy;
        this.timestamp = timestamp;
        this.objectives = objectives;
        this.solutions = solutions;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }
}
