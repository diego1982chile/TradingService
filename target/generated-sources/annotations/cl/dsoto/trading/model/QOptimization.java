package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QOptimization extends PersistableExpressionImpl<Optimization> implements PersistableExpression<Optimization>
{
    public static final QOptimization jdoCandidate = candidate("this");

    public static QOptimization candidate(String name)
    {
        return new QOptimization(null, name, 5);
    }

    public static QOptimization candidate()
    {
        return jdoCandidate;
    }

    public static QOptimization parameter(String name)
    {
        return new QOptimization(Optimization.class, name, ExpressionType.PARAMETER);
    }

    public static QOptimization variable(String name)
    {
        return new QOptimization(Optimization.class, name, ExpressionType.VARIABLE);
    }

    public final cl.dsoto.trading.model.QPeriod period;
    public final cl.dsoto.trading.model.QStrategy strategy;
    public final ObjectExpression<java.sql.Timestamp> timestamp;
    public final ListExpression objectives;
    public final ListExpression solutions;

    public QOptimization(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        if (depth > 0)
        {
            this.period = new cl.dsoto.trading.model.QPeriod(this, "period", depth-1);
        }
        else
        {
            this.period = null;
        }
        if (depth > 0)
        {
            this.strategy = new cl.dsoto.trading.model.QStrategy(this, "strategy", depth-1);
        }
        else
        {
            this.strategy = null;
        }
        this.timestamp = new ObjectExpressionImpl<java.sql.Timestamp>(this, "timestamp");
        this.objectives = new ListExpressionImpl(this, "objectives");
        this.solutions = new ListExpressionImpl(this, "solutions");
    }

    public QOptimization(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.period = new cl.dsoto.trading.model.QPeriod(this, "period", 5);
        this.strategy = new cl.dsoto.trading.model.QStrategy(this, "strategy", 5);
        this.timestamp = new ObjectExpressionImpl<java.sql.Timestamp>(this, "timestamp");
        this.objectives = new ListExpressionImpl(this, "objectives");
        this.solutions = new ListExpressionImpl(this, "solutions");
    }
}
