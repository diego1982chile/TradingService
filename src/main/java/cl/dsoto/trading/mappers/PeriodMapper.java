package cl.dsoto.trading.mappers;

import cl.dsoto.trading.model.Period;
import cl.dsoto.trading.model.PeriodBar;
import cl.dsoto.trading.model.views.PeriodBarView;
import cl.dsoto.trading.model.views.PeriodView;
import org.ta4j.core.*;
import org.ta4j.core.analysis.CashFlow;
import org.ta4j.core.analysis.criteria.AverageProfitableTradesCriterion;
import org.ta4j.core.analysis.criteria.RewardRiskRatioCriterion;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.analysis.criteria.VersusBuyAndHoldCriterion;
import ta4jexamples.research.MultipleStrategy;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 06-10-20.
 */
public class PeriodMapper {

    String pattern = "###.#####";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

    public static PeriodView mapPeriod(Period period) {

        List<PeriodBarView> periodBarViews = new ArrayList<>();

        try {
            TimeSeries series = new BaseTimeSeries(period.getName());

            for (PeriodBar periodBar : period.getBars()) {
                ZonedDateTime time = periodBar.getEndTime();
                double open = periodBar.getOpenPrice().doubleValue();
                double close = periodBar.getClosePrice().doubleValue();
                double max = periodBar.getMaxPrice().doubleValue();
                double min = periodBar.getMinPrice().doubleValue();
                double vol = periodBar.getVolume().doubleValue();

                series.addBar(new BaseBar(time, open, max, min, close, vol));

                long group = periodBar.getBeginTime().toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                periodBarViews.add(new PeriodBarView(periodBar.getId(), periodBar.getEndTime(), periodBar.getOpenPrice().doubleValue(), periodBar.getMaxPrice().doubleValue(), periodBar.getMinPrice().doubleValue(), periodBar.getClosePrice().doubleValue(), periodBar.getVolume().doubleValue(), group, null ));
            }

            TimeSeriesManager seriesManager = new TimeSeriesManager(series);

            List<Strategy> strategies = StrategyHelper.mapFrom(period);

            MultipleStrategy multipleStrategy = new MultipleStrategy(strategies);

            TradingRecord tradingRecord = seriesManager.run(multipleStrategy.buildStrategy(series), Order.OrderType.BUY);

            // Analysis

            // Getting the cash flow of the resulting trades
            CashFlow cashFlow = new CashFlow(series, tradingRecord);

            // Getting the profitable trades ratio
            AnalysisCriterion profitTradesRatio = new AverageProfitableTradesCriterion();
            System.out.println("Profitable trades ratio: " + profitTradesRatio.calculate(series, tradingRecord));

            // Getting the reward-risk ratio
            AnalysisCriterion rewardRiskRatio = new RewardRiskRatioCriterion();
            System.out.println("Reward-risk ratio: " + rewardRiskRatio.calculate(series, tradingRecord));

            // Total profit of our strategy
            // vs total profit of a buy-and-hold strategy
            AnalysisCriterion vsBuyAndHold = new VersusBuyAndHoldCriterion(new TotalProfitCriterion());
            System.out.println("Our profit vs buy-and-hold profit: " + vsBuyAndHold.calculate(series, tradingRecord));

            for (int i = 0; i < tradingRecord.getTrades().size(); ++i) {
                System.out.println("Trade[" + i + "]: " + tradingRecord.getTrades().get(i).toString());
            }

            for (int i = 0; i < cashFlow.getSize(); ++i) {
                System.out.println("CashFlow[" + i + "]: " + cashFlow.getValue(i));
            }

            PeriodView periodView = new PeriodView(period.getId(), period.getName(), period.getTimestamp(), period.getStart(), period.getEnd(), tradingRecord.getTradeCount(), profitTradesRatio.calculate(series, tradingRecord), rewardRiskRatio.calculate(series, tradingRecord), vsBuyAndHold.calculate(series, tradingRecord), cashFlow.getValue(cashFlow.getSize()-1).doubleValue());

            for (PeriodBarView periodBarView : periodBarViews) {
                periodBarView.setPeriod(periodView);
            }

            periodView.setBars(periodBarViews);

            return periodView;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
