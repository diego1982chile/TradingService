package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QStrategy extends PersistableExpressionImpl<Strategy> implements PersistableExpression<Strategy>
{
    public static final QStrategy jdoCandidate = candidate("this");

    public static QStrategy candidate(String name)
    {
        return new QStrategy(null, name, 5);
    }

    public static QStrategy candidate()
    {
        return jdoCandidate;
    }

    public static QStrategy parameter(String name)
    {
        return new QStrategy(Strategy.class, name, ExpressionType.PARAMETER);
    }

    public static QStrategy variable(String name)
    {
        return new QStrategy(Strategy.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final NumericExpression<Integer> variables;
    public final cl.dsoto.trading.model.QProblemType type;

    public QStrategy(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.variables = new NumericExpressionImpl<Integer>(this, "variables");
        if (depth > 0)
        {
            this.type = new cl.dsoto.trading.model.QProblemType(this, "type", depth-1);
        }
        else
        {
            this.type = null;
        }
    }

    public QStrategy(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.variables = new NumericExpressionImpl<Integer>(this, "variables");
        this.type = new cl.dsoto.trading.model.QProblemType(this, "type", 5);
    }
}
