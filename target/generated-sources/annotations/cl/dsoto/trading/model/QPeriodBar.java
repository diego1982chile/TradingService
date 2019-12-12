package cl.dsoto.trading.model;

import javax.annotation.Generated;
import javax.jdo.query.*;
import org.datanucleus.api.jdo.query.*;

@Generated(value="org.datanucleus.jdo.query.JDOQueryProcessor")
public class QPeriodBar extends PersistableExpressionImpl<PeriodBar> implements PersistableExpression<PeriodBar>
{
    public static final QPeriodBar jdoCandidate = candidate("this");

    public static QPeriodBar candidate(String name)
    {
        return new QPeriodBar(null, name, 5);
    }

    public static QPeriodBar candidate()
    {
        return jdoCandidate;
    }

    public static QPeriodBar parameter(String name)
    {
        return new QPeriodBar(PeriodBar.class, name, ExpressionType.PARAMETER);
    }

    public static QPeriodBar variable(String name)
    {
        return new QPeriodBar(PeriodBar.class, name, ExpressionType.VARIABLE);
    }

    public final cl.dsoto.trading.model.QPeriod period;
    public final ObjectExpression<java.time.ZonedDateTime> endTime;
    public final NumericExpression<Double> openPrice;
    public final NumericExpression<Double> highPrice;
    public final NumericExpression<Double> lowPrice;
    public final NumericExpression<Double> closePrice;
    public final NumericExpression<Double> volume;

    public QPeriodBar(PersistableExpression parent, String name, int depth)
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
        this.endTime = new ObjectExpressionImpl<java.time.ZonedDateTime>(this, "endTime");
        this.openPrice = new NumericExpressionImpl<Double>(this, "openPrice");
        this.highPrice = new NumericExpressionImpl<Double>(this, "highPrice");
        this.lowPrice = new NumericExpressionImpl<Double>(this, "lowPrice");
        this.closePrice = new NumericExpressionImpl<Double>(this, "closePrice");
        this.volume = new NumericExpressionImpl<Double>(this, "volume");
    }

    public QPeriodBar(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.period = new cl.dsoto.trading.model.QPeriod(this, "period", 5);
        this.endTime = new ObjectExpressionImpl<java.time.ZonedDateTime>(this, "endTime");
        this.openPrice = new NumericExpressionImpl<Double>(this, "openPrice");
        this.highPrice = new NumericExpressionImpl<Double>(this, "highPrice");
        this.lowPrice = new NumericExpressionImpl<Double>(this, "lowPrice");
        this.closePrice = new NumericExpressionImpl<Double>(this, "closePrice");
        this.volume = new NumericExpressionImpl<Double>(this, "volume");
    }
}
