package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QValue extends PersistableExpressionImpl<Value> implements PersistableExpression<Value>
{
    public static final QValue jdoCandidate = candidate("this");

    public static QValue candidate(String name)
    {
        return new QValue(null, name, 5);
    }

    public static QValue candidate()
    {
        return jdoCandidate;
    }

    public static QValue parameter(String name)
    {
        return new QValue(Value.class, name, ExpressionType.PARAMETER);
    }

    public static QValue variable(String name)
    {
        return new QValue(Value.class, name, ExpressionType.VARIABLE);
    }

    public final cl.dsoto.trading.model.QSolution solution;
    public final ObjectExpression<java.lang.Comparable> value;

    public QValue(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        if (depth > 0)
        {
            this.solution = new cl.dsoto.trading.model.QSolution(this, "solution", depth-1);
        }
        else
        {
            this.solution = null;
        }
        this.value = new ObjectExpressionImpl<java.lang.Comparable>(this, "value");
    }

    public QValue(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.solution = new cl.dsoto.trading.model.QSolution(this, "solution", 5);
        this.value = new ObjectExpressionImpl<java.lang.Comparable>(this, "value");
    }
}
