package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QSolution extends PersistableExpressionImpl<Solution> implements PersistableExpression<Solution>
{
    public static final QSolution jdoCandidate = candidate("this");

    public static QSolution candidate(String name)
    {
        return new QSolution(null, name, 5);
    }

    public static QSolution candidate()
    {
        return jdoCandidate;
    }

    public static QSolution parameter(String name)
    {
        return new QSolution(Solution.class, name, ExpressionType.PARAMETER);
    }

    public static QSolution variable(String name)
    {
        return new QSolution(Solution.class, name, ExpressionType.VARIABLE);
    }

    public final cl.dsoto.trading.model.QOptimization optimization;
    public final ListExpression values;

    public QSolution(PersistableExpression parent, String name, int depth)
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
        this.values = new ListExpressionImpl(this, "values");
    }

    public QSolution(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.optimization = new cl.dsoto.trading.model.QOptimization(this, "optimization", 5);
        this.values = new ListExpressionImpl(this, "values");
    }
}
