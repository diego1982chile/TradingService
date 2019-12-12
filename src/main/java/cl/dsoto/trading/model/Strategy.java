package cl.dsoto.trading.model;

import javax.jdo.annotations.*;
import java.io.Serializable;

/**
 * Created by des01c7 on 22-03-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_strategy")
public class Strategy implements Serializable {

    private String name;
    private int variables;

    @Extension(vendorName="datanucleus", key="enum-value-getter", value="getValue")
    private ProblemType type;

    public Strategy(String name, int variables, ProblemType type) {
        this.name = name;
        this.variables = variables;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVariables() {
        return variables;
    }

    public void setVariables(int variables) {
        this.variables = variables;
    }

    public ProblemType getType() {
        return type;
    }

    public void setType(ProblemType type) {
        this.type = type;
    }
}
