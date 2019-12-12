package cl.dsoto.trading.model;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import java.io.Serializable;

/**
 * @author Andrés Farías on 8/23/16.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_problem_type")
public class ProblemType implements Serializable {

    //BINARY("Codificacón binaria"),
    //INTEGER("Codificación entera"),
    //REAL(3, "Codificación real");

    static ProblemType INTEGER = new ProblemType("Codificación entera");
    static ProblemType BINARY = new ProblemType("Codificacón binaria");

    /** Nombre o descripción del cambio */
    private String name;

    ProblemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

