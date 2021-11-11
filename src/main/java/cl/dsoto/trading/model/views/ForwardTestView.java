package cl.dsoto.trading.model.views;

import cl.dsoto.trading.model.ForwardTest;
import cl.dsoto.trading.model.ForwardTestBar;
import cl.dsoto.trading.model.Period;
import cl.dsoto.trading.model.TimeFrame;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static cl.dsoto.trading.model.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 29-03-19.
 */
public class ForwardTestView implements Serializable {

    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    String name;
    Timestamp timestamp;
    Date start;
    Date end;

    TimeFrame timeFrame;

    @JsonbTransient
    Period period;

    double numberOfTrades;
    double profitableTradesRatio;
    double rewardRiskRatio;
    double vsBuyAndHoldRatio;
    double cashFlow;


    List<ForwardTestBarView> bars = new ArrayList<>();

    public List<ForwardTestBarView> getBars() {
        return bars;
    }

    public void setBars(List<ForwardTestBarView> bars) {
        this.bars = bars;
    }

    public ForwardTestView(String name, Timestamp timestamp, Date start, Date end, TimeFrame timeFrame) {
        this.name = name;
        this.timestamp = timestamp;
        this.start = start;
        this.end = end;
        this.timeFrame = timeFrame;
    }

    public ForwardTestView(long id, String name, Timestamp timestamp, Date start, Date end, TimeFrame timeFrame) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.start = start;
        this.end = end;
        this.timeFrame = timeFrame;
    }

    public ForwardTestView(ForwardTest forwardTest) {
        this(forwardTest.getId(), forwardTest.getName(), forwardTest.getTimestamp(), forwardTest.getStart(), forwardTest.getEnd(), forwardTest.getTimeFrame());
        setPeriod(forwardTest.getPeriod());
        for (ForwardTestBar forwardTestBar :forwardTest.getBars()) {
            double group = forwardTestBar.getBeginTime().toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
            bars.add(new ForwardTestBarView(forwardTestBar.getId(), forwardTestBar.getEndTime(), forwardTestBar.getOpenPrice().doubleValue(), forwardTestBar.getMaxPrice().doubleValue(), forwardTestBar.getMinPrice().doubleValue(), forwardTestBar.getClosePrice().doubleValue(), forwardTestBar.getVolume().doubleValue(), group, this));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public double getNumberOfTrades() {
        return numberOfTrades;
    }

    public void setNumberOfTrades(double numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    public double getProfitableTradesRatio() {
        return profitableTradesRatio;
    }

    public void setProfitableTradesRatio(double profitableTradesRatio) {
        this.profitableTradesRatio = profitableTradesRatio;
    }

    public double getRewardRiskRatio() {
        return rewardRiskRatio;
    }

    public void setRewardRiskRatio(double rewardRiskRatio) {
        this.rewardRiskRatio = rewardRiskRatio;
    }

    public double getVsBuyAndHoldRatio() {
        return vsBuyAndHoldRatio;
    }

    public void setVsBuyAndHoldRatio(double vsBuyAndHoldRatio) {
        this.vsBuyAndHoldRatio = vsBuyAndHoldRatio;
    }

    public double getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(double cashFlow) {
        this.cashFlow = cashFlow;
    }

    @Override
    public String toString() {
        return name;
    }
}
