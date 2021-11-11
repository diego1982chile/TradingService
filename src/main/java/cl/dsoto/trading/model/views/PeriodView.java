package cl.dsoto.trading.model.views;

import cl.dsoto.trading.model.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cl.dsoto.trading.model.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 29-03-19.
 */
public class PeriodView implements Serializable {

    private long id = NON_PERSISTED_ID;

    String name;
    Timestamp timestamp;
    Date start;
    Date end;

    double numberOfTrades;
    double profitableTradesRatio;
    double rewardRiskRatio;
    double vsBuyAndHoldRatio;
    double cashFlow;

    List<PeriodBarView> bars = new ArrayList<>();

    public List<PeriodBarView> getBars() {
        return bars;
    }

    public void setBars(List<PeriodBarView> bars) {
        this.bars = bars;
    }

    @Override
    public String toString() {
        return name;
    }

    public PeriodView(long id, String name, Timestamp timestamp, Date start, Date end, double numberOfTrades, double profitableTradesRatio, double rewardRiskRatio, double vsBuyAndHoldRatio, double cashFlow) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.start = start;
        this.end = end;
        this.numberOfTrades = numberOfTrades;
        this.profitableTradesRatio = profitableTradesRatio;
        this.rewardRiskRatio = rewardRiskRatio;
        this.vsBuyAndHoldRatio = vsBuyAndHoldRatio;
        this.cashFlow = cashFlow;
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
}
