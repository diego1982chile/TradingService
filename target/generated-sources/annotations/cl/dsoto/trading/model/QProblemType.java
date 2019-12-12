package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QProblemType extends PersistableExpressionImpl<ProblemType> implements PersistableExpression<ProblemType>
{
    public static final QProblemType jdoCandidate = candidate("this");

    public static QProblemType candidate(String name)
    {
        return new QProblemType(null, name, 5);
    }

    public static QProblemType candidate()
    {
        return jdoCandidate;
    }

    public static QProblemType parameter(String name)
    {
        return new QProblemType(ProblemType.class, name, ExpressionType.PARAMETER);
    }

    public static QProblemType variable(String name)
    {
        return new QProblemType(ProblemType.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;

    public QProblemType(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
    }

    public QProblemType(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
    }
}
