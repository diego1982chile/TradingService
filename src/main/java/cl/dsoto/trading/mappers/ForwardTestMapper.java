package cl.dsoto.trading.mappers;

import cl.dsoto.trading.model.ForwardTest;
import cl.dsoto.trading.model.ForwardTestBar;
import cl.dsoto.trading.model.Period;
import cl.dsoto.trading.model.PeriodBar;
import cl.dsoto.trading.model.views.ForwardTestBarView;
import cl.dsoto.trading.model.views.ForwardTestView;
import cl.dsoto.trading.model.views.PeriodBarView;
import cl.dsoto.trading.model.views.PeriodView;
import org.ta4j.core.*;
import org.ta4j.core.analysis.CashFlow;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.RewardRiskRatioCriterion;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import ta4jexamples.analysis.BuyAndSellSignalsToChart;
import ta4jexamples.research.MultipleStrategy;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 06-10-20.
 */
public class ForwardTestMapper {

    static String pattern = "###.#####";
    static DecimalFormat decimalFormat = new DecimalFormat(pattern);

    /** Close price of the last bar */
    private static Decimal LAST_BAR_CLOSE_PRICE;

    private static TimeSeries live;

    static ForwardTestView selected;

    public static ForwardTestView mapForwardTest(ForwardTest forwardTest) {
        selected = new ForwardTestView(forwardTest);
        computeResults(200);
        return selected;
    }

    /**
     * Builds a moving time series (i.e. keeping only the maxBarCount last bars)
     * @param maxBarCount the number of bars to keep in the time series (at maximum)
     * @return a moving time series
     */
    private static TimeSeries initMovingTimeSeries(int maxBarCount) {
        //TimeSeries series = CsvTradesLoader.loadBitstampSeries();
        //TimeSeries series = CsvTicksLoader.load("EURUSD_Daily_201701020000_201712290000.csv");
        //TimeSeries series = CsvTicksLoader.load("2019_D.csv");

        TimeSeries series = new BaseTimeSeries(selected.getPeriod().getName());

        for (PeriodBar periodBar : selected.getPeriod().getBars()) {
            ZonedDateTime time = periodBar.getEndTime();
            double open = periodBar.getOpenPrice().doubleValue();
            double close = periodBar.getClosePrice().doubleValue();
            double max = periodBar.getMaxPrice().doubleValue();
            double min = periodBar.getMinPrice().doubleValue();
            double vol = periodBar.getVolume().doubleValue();

            series.addBar(new BaseBar(time, open, max, min, close, vol));
        }

        System.out.print("Initial bar count: " + series.getBarCount());
        // Limitating the number of bars to maxBarCount
        series.setMaximumBarCount(maxBarCount);
        LAST_BAR_CLOSE_PRICE = series.getBar(series.getEndIndex()).getClosePrice();
        System.out.println(" (limited to " + maxBarCount + "), close price = " + LAST_BAR_CLOSE_PRICE);

        //live = CsvTicksLoader.load("2020_D.csv");
        //live = CsvTicksLoader.load("2020_D.csv");

        live = new BaseTimeSeries(selected.getName());

        for (ForwardTestBarView forwardTestBar : selected.getBars()) {
            ZonedDateTime time = forwardTestBar.getEndTime();
            double open = forwardTestBar.getOpenPrice().doubleValue();
            double close = forwardTestBar.getClosePrice().doubleValue();
            double max = forwardTestBar.getMaxPrice().doubleValue();
            double min = forwardTestBar.getMinPrice().doubleValue();
            double vol = forwardTestBar.getVolume().doubleValue();

            live.addBar(new BaseBar(time, open, max, min, close, vol));
        }

        return series;
    }


    /**
     * @param series a time series
     * @return a dummy strategy
     */
    private static Strategy buildStrategy(TimeSeries series) {

        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        List<Strategy> strategies = new ArrayList<>();

        try {
            strategies = StrategyHelper.mapFrom(selected.getPeriod(), series);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleStrategy multipleStrategy = new MultipleStrategy(strategies);

        return multipleStrategy.buildStrategy(series);
    }


    /**
     * Generates a random bar.
     * @return a random bar
     */
    private static Bar generateRandomBar(int i) {
        /*
        if(live == null || live.isEmpty()) {
            //live = CsvTicksLoader.load("EURUSD_Daily_201801020000_201812310000.csv");
            live = CsvTicksLoader.load("2020_D.csv");
        }
        */
        LAST_BAR_CLOSE_PRICE = live.getBar(i).getClosePrice();
        return live.getBar(i);
    }


    public static void computeResults(int maxBarCount) {

        try {
            System.out.println("********************** Initialization **********************");
            // Getting the time series
            TimeSeries series = initMovingTimeSeries(maxBarCount);

            // Building the trading strategy
            Strategy strategy = buildStrategy(series);

            // Initializing the trading history
            TradingRecord tradingRecord = new BaseTradingRecord();
            System.out.println("************************************************************");

            int STEP = 13;
            int OFFSET = 20;

            boolean flag = false;

            Bar newBar = null;

        /*
          We run the strategy for the 50 next bars.
         */
            for (int i = 0; i < live.getBarCount(); i++) {

                while(!flag) {
                    try {
                        // New bar
                        Thread.sleep(30); // I know...
                        newBar = generateRandomBar(i);
                        System.out.println("------------------------------------------------------\n"
                                + "Bar "+i+" added, close price = " + newBar.getClosePrice().doubleValue());
                        series.addBar(newBar);
                        flag = true;
                    }
                    catch(IllegalArgumentException e) {
                        i++;
                    }
                }

                flag = false;

                int endIndex = series.getEndIndex();

                if (strategy.shouldEnter(endIndex)) {
                    // Our strategy should enter
                    System.out.println("Strategy should ENTER on " + endIndex);
                    boolean entered = tradingRecord.enter(endIndex, newBar.getClosePrice(), Decimal.TEN);
                    if (entered) {
                        Order entry = tradingRecord.getLastEntry();
                        System.out.println("Entered on " + entry.getIndex()
                                + " (price=" + entry.getPrice().doubleValue()
                                + ", amount=" + entry.getAmount().doubleValue() + ")");
                    }
                } else if (strategy.shouldExit(endIndex)) {
                    // Our strategy should exit
                    System.out.println("Strategy should EXIT on " + endIndex);
                    boolean exited = tradingRecord.exit(endIndex, newBar.getClosePrice(), Decimal.TEN);
                    if (exited) {
                        Order exit = tradingRecord.getLastExit();
                        System.out.println("Exited on " + exit.getIndex()
                                + " (price=" + exit.getPrice().doubleValue()
                                + ", amount=" + exit.getAmount().doubleValue() + ")");
                    }
                }
            }

            System.out.println("Number of trades for our strategy: " + tradingRecord.getTradeCount());

            selected.setNumberOfTrades(tradingRecord.getTradeCount());

            // Analysis

            // Getting the profitable trades ratio
            AnalysisCriterion profitTradesRatio = new AverageProfitableTradesCriterion();
            selected.setProfitableTradesRatio(profitTradesRatio.calculate(series, tradingRecord));

            // Getting the reward-risk ratio
            AnalysisCriterion rewardRiskRatio = new RewardRiskRatioCriterion();
            selected.setRewardRiskRatio(rewardRiskRatio.calculate(series, tradingRecord));

            // Total profit of our strategy
            // vs total profit of a buy-and-hold strategy
            AnalysisCriterion vsBuyAndHold = new VersusBuyAndHoldCriterion(new TotalProfitCriterion());
            selected.setVsBuyAndHoldRatio(rewardRiskRatio.calculate(series, tradingRecord));

            for (int i = 0; i < tradingRecord.getTrades().size(); ++i) {
                System.out.println("Trade["+ i +"]: " + tradingRecord.getTrades().get(i).toString());
            }

            // Getting the cash flow of the resulting trades
            CashFlow cashFlow = new CashFlow(series, tradingRecord);

            for (int i = 0; i < 10000; ++i) {
                try {
                    System.out.println("CashFlow["+ i +"]: " + cashFlow.getValue(i));
                }
                catch (IndexOutOfBoundsException e) {
                    selected.setCashFlow(Double.valueOf(String.valueOf(cashFlow.getValue(i-1))));
                    break;
                }
            }

            selected.setBars(selected.getBars());

            //Chart
            /*
            JFreeChart jfreechart = BuyAndSellSignalsToChart.buildCandleStickChart(series2, buildStrategy(series2));
            ChartPanel panel = new ChartPanel(jfreechart);
            panel.setFillZoomRectangle(true);
            panel.setMouseWheelEnabled(true);
            panel.setPreferredSize(new Dimension(800, 200));

            getPlotView().setLayout(new BorderLayout());
            getPlotView().removeAll();
            getPlotView().add(panel);
            getPlotView().validate();
            */

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


}
