package cl.dsoto.trading.managers;

import cl.dsoto.trading.daos.PeriodDAO;
import cl.dsoto.trading.model.*;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.uma.jmetal.runner.singleobjective.GenerationalGeneticAlgorithmStockMarketIntegerRunner;
import org.uma.jmetal.runner.singleobjective.GenerationalGeneticAlgorithmStockMarketRunner;
import ta4jexamples.loaders.CsvTicksLoader;
import ta4jexamples.strategies.*;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class PeriodManager {

    @Inject
    private StrategyManager strategyManager;

    @Inject
    private PeriodDAO periodDAO;

    public Period getPeriodById(long id) throws Exception {
        return periodDAO.getPeriodById(id);
    }

    public Period persist(Period period) throws Exception {
        return periodDAO.persist(period);
    }

    public List<Period> getLast(int periods) throws Exception {
        return periodDAO.getLast(periods);
    }

    public List<Period> getLast(int timeFrameId, int periods) throws Exception {
        List<Period> periodList = new ArrayList<>();

        for (Period period : periodDAO.getLast(periods)) {
            if(period.getTimeFrame().equals(TimeFrame.valueOf(timeFrameId))) {
                periodList.add(period);
            }
        }

        return periodList;
    }

    public Period createFromFile(String file) throws Exception {
        TimeSeries timeSeries = CsvTicksLoader.load(file);
        String name = file;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Date start = Date.valueOf(timeSeries.getFirstBar().getBeginTime().toLocalDate());
        Date end = Date.valueOf(timeSeries.getLastBar().getBeginTime().toLocalDate());

        //TODO: Dejar esto parametrico
        TimeFrame timeFrame = TimeFrame.DAY;

        Period period = new Period(name, timestamp, start, end, timeFrame);

        for (Bar bar : timeSeries.getBarData()) {
            double open = bar.getOpenPrice().doubleValue();
            double high = bar.getMaxPrice().doubleValue();
            double low = bar.getMinPrice().doubleValue();
            double close = bar.getClosePrice().doubleValue();
            double volume = bar.getVolume().doubleValue();

            period.getBars().add(new PeriodBar(bar.getEndTime(), open, high, low, close, volume, period));
        }

        return period;
    }

    public Period createFromSeries(TimeSeries series) throws Exception {

        String name = series.getName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date start = Date.valueOf(series.getFirstBar().getBeginTime().toLocalDate());
        Date end = Date.valueOf(series.getLastBar().getBeginTime().toLocalDate());

        //TODO: Dejar esto parametrico
        TimeFrame timeFrame = TimeFrame.DAY;

        Period period = new Period(name, timestamp, start, end, timeFrame);

        for (Bar bar : series.getBarData()) {
            double open = bar.getOpenPrice().doubleValue();
            double high = bar.getMaxPrice().doubleValue();
            double low = bar.getMinPrice().doubleValue();
            double close = bar.getClosePrice().doubleValue();
            double volume = bar.getVolume().doubleValue();

            period.getBars().add(new PeriodBar(bar.getEndTime(), open, high, low, close, volume, period));
        }

        return period;
    }

    public List<Strategy> mapFrom(Period period) throws Exception {

        List<Strategy> strategies = new ArrayList<>();

        TimeSeries series = new BaseTimeSeries(period.getName());

        for (PeriodBar periodBar : period.getBars()) {
            series.addBar(periodBar);
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
                    boolean value = (Boolean) solution.getValues().get(i);

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

    @Asynchronous
    public void generateOptimizations(TimeSeries timeSeries) throws Exception {

        Period period = createFromSeries(timeSeries);

        List<cl.dsoto.trading.model.Strategy> strategies = strategyManager.getIntegerProblemTypeStrategies();

        for (cl.dsoto.trading.model.Strategy strategy : strategies) {
            GenerationalGeneticAlgorithmStockMarketIntegerRunner runner =
                    new GenerationalGeneticAlgorithmStockMarketIntegerRunner(strategy.getName(), timeSeries, strategy.getVariables());
            Optimization optimization = runner.run(strategy);
            optimization.setPeriod(period);
            period.getOptimizations().add(optimization);
            updateStrategy(optimization, strategy.getName());
        }

        strategies = strategyManager.getBinaryProblemTypeStrategies();

        for (cl.dsoto.trading.model.Strategy strategy : strategies) {
            GenerationalGeneticAlgorithmStockMarketRunner runner =
                    new GenerationalGeneticAlgorithmStockMarketRunner(strategy.getName(), timeSeries, strategy.getVariables());
            Optimization optimization = runner.run(strategy);
            optimization.setPeriod(period);
            period.getOptimizations().add(optimization);
        }

        persist(period);

    }

    public static void updateStrategy(Optimization optimization, String strategy) throws Exception {
        switch (strategy) {
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

}
