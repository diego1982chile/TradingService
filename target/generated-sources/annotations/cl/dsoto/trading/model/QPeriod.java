package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QPeriod extends PersistableExpressionImpl<Period> implements PersistableExpression<Period>
{
    public static final QPeriod jdoCandidate = candidate("this");

    public static QPeriod candidate(String name)
    {
        return new QPeriod(null, name, 5);
    }

    public static QPeriod candidate()
    {
        return jdoCandidate;
    }

    public static QPeriod parameter(String name)
    {
        return new QPeriod(Period.class, name, ExpressionType.PARAMETER);
    }

    public static QPeriod variable(String name)
    {
        return new QPeriod(Period.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final ObjectExpression<java.sql.Timestamp> timestamp;
    public final DateExpression start;
    public final DateExpression end;
    public final EnumExpression timeFrame;
    public final ListExpression optimizations;
    public final ListExpression bars;

    public QPeriod(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.timestamp = new ObjectExpressionImpl<java.sql.Timestamp>(this, "timestamp");
        this.start = new DateExpressionImpl(this, "start");
        this.end = new DateExpressionImpl(this, "end");
        this.timeFrame = new EnumExpressionImpl(this, "timeFrame");
        this.optimizations = new ListExpressionImpl(this, "optimizations");
        this.bars = new ListExpressionImpl(this, "bars");
    }

    public QPeriod(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.timestamp = new ObjectExpressionImpl<java.sql.Timestamp>(this, "timestamp");
        this.start = new DateExpressionImpl(this, "start");
        this.end = new DateExpressionImpl(this, "end");
        this.timeFrame = new EnumExpressionImpl(this, "timeFrame");
        this.optimizations = new ListExpressionImpl(this, "optimizations");
        this.bars = new ListExpressionImpl(this, "bars");
    }
}
