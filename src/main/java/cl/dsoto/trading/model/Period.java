package cl.dsoto.trading.model;

import org.ta4j.core.*;
import ta4jexamples.strategies.*;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 29-03-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_period")
public class Period implements Serializable {

    String name;
    Timestamp timestamp;
    Date start;
    Date end;

    @Column(name = "id_time_frame")
    TimeFrame timeFrame;

    @Persistent(mappedBy="period")
    List<Optimization> optimizations = new ArrayList<>();

    @Persistent(mappedBy="period")
    List<PeriodBar> bars = new ArrayList<>();

    public List<PeriodBar> getBars() {
        return bars;
    }

    public void setBars(List<PeriodBar> bars) {
        this.bars = bars;
    }

    public Period(String name, Timestamp timestamp, Date start, Date end, TimeFrame timeFrame) {
        this.name = name;
        this.timestamp = timestamp;
        this.start = start;
        this.end = end;
        this.timeFrame = timeFrame;
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

    public List<Optimization> getOptimizations() {
        return optimizations;
    }

    public void setOptimizations(List<Optimization> optimizations) {
        this.optimizations = optimizations;
    }

    public List<Optimization> getOptimizationsOfType(ProblemType problemType) {
        List<Optimization> optimizations = new ArrayList<>();

        for (Optimization optimization : this.optimizations) {
            if(optimization.getStrategy().getType().equals(problemType)) {
                optimizations.add(optimization);
            }
        }

        return optimizations;
    }

    public static List<org.ta4j.core.Strategy> extractStrategy(Period period) throws Exception {

        List<org.ta4j.core.Strategy> strategies = new ArrayList<>();

        TimeSeries series = new BaseTimeSeries(period.getName());

        for (PeriodBar periodBar : period.getBars()) {
            series.addBar(periodBar.map());
        }

        for (Optimization optimization : period.getOptimizationsOfType(ProblemType.INTEGER)) {
            switch (optimization.getStrategy().getName()) {
                case Strategies.GlobalExtremaStrategy:
                    GlobalExtremaStrategy.mapFrom(optimization);
                    break;
                case Strategies.TunnelStrategy:
                    TunnelStrategy.mapFrom(optimization);
                    break;
                case Strategies.CCICorrectionStrategy:
                    CCICorrectionStrategy.mapFrom(optimization);
                    break;
                case Strategies.BagovinoStrategy:
                    BagovinoStrategy.mapFrom(optimization);
                    break;
                case Strategies.MovingAveragesStrategy:
                    MovingAveragesStrategy.mapFrom(optimization);
                    break;
                case Strategies.RSI2Strategy:
                    RSI2Strategy.mapFrom(optimization);
                    break;
                case Strategies.ParabolicSARStrategy:
                    ParabolicSARStrategy.mapFrom(optimization);
                    break;
                case Strategies.MovingMomentumStrategy:
                    MovingMomentumStrategy.mapFrom(optimization);
                    break;
                case Strategies.StochasticStrategy:
                    StochasticStrategy.mapFrom(optimization);
                    break;
                case Strategies.MACDStrategy:
                    MACDStrategy.mapFrom(optimization);
                    break;
                case Strategies.FXBootCampStrategy:
                    FXBootCampStrategy.mapFrom(optimization);
                    break;
                case Strategies.WinslowStrategy:
                    WinslowStrategy.mapFrom(optimization);
                    break;
            }
        }

        for (Optimization optimization : period.getOptimizationsOfType(ProblemType.BINARY)) {
            for (Solution solution : optimization.getSolutions()) {

                for (int i = 0; i < solution.getValues().size(); i++) {
                    boolean value = (Boolean) solution.getValues().get(i).getValue();

                    if (value) {

                        switch (i) {
                            case Strategies.CCICorrectionStrategyId:
                                strategies.add(CCICorrectionStrategy.buildStrategy(series));
                                break;
                            case Strategies.GlobalExtremaStrategyId:
                                strategies.add(GlobalExtremaStrategy.buildStrategy(series));
                                break;
                            case Strategies.MovingMomentumStrategyId:
                                strategies.add(MovingMomentumStrategy.buildStrategy(series));
                                break;
                            case Strategies.RSI2StrategyId:
                                strategies.add(RSI2Strategy.buildStrategy(series));
                                break;
                            case Strategies.MACDStrategyId:
                                strategies.add(MACDStrategy.buildStrategy(series));
                                break;
                            case Strategies.StochasticStrategyId:
                                strategies.add(StochasticStrategy.buildStrategy(series));
                                break;
                            case Strategies.ParabolicSARStrategyId:
                                strategies.add(ParabolicSARStrategy.buildStrategy(series));
                                break;
                            case Strategies.MovingAveragesStrategyId:
                                strategies.add(MovingAveragesStrategy.buildStrategy(series));
                                break;
                            case Strategies.BagovinoStrategyId:
                                strategies.add(BagovinoStrategy.buildStrategy(series));
                                break;
                            case Strategies.FXBootCampStrategyId:
                                strategies.add(FXBootCampStrategy.buildStrategy(series));
                                break;
                            case Strategies.TunnelStrategyId:
                                strategies.add(TunnelStrategy.buildStrategy(series));
                                break;
                            case Strategies.WinslowStrategyId:
                                strategies.add(WinslowStrategy.buildStrategy(series));
                                break;
                        }
                    }
                }
            }
        }

        return strategies;
    }

    @Override
    public String toString() {
        return name;
    }
}
