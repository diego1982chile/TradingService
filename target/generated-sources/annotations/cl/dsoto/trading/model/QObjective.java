package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QObjective extends PersistableExpressionImpl<Objective> implements PersistableExpression<Objective>
{
    public static final QObjective jdoCandidate = candidate("this");

    public static QObjective candidate(String name)
    {
        return new QObjective(null, name, 5);
    }

    public static QObjective candidate()
    {
        return jdoCandidate;
    }

    public static QObjective parameter(String name)
    {
        return new QObjective(Objective.class, name, ExpressionType.PARAMETER);
    }

    public static QObjective variable(String name)
    {
        return new QObjective(Objective.class, name, ExpressionType.VARIABLE);
    }

    public final cl.dsoto.trading.model.QOptimization optimization;
    public final NumericExpression<Double> objective;

    public QObjective(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        if (depth > 0)
        {
            this.optimization = new cl.dsoto.trading.model.QOptimization(this, "optimization", depth-1);
        }
        else
        {
            this.optimization = null;
        }
        this.objective = new NumericExpressionImpl<Double>(this, "objective");
    }

    public QObjective(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.optimization = new cl.dsoto.trading.model.QOptimization(this, "optimization", 5);
        this.objective = new NumericExpressionImpl<Double>(this, "objective");
    }
}
