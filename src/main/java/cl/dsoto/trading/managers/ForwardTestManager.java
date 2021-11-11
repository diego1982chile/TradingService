package cl.dsoto.trading.managers;

import cl.dsoto.trading.daos.ForwardTestDAO;
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
public class ForwardTestManager {

    @Inject
    private StrategyManager strategyManager;

    @Inject
    private ForwardTestDAO forwardTestDAO;

    @Inject
    private PeriodDAO periodDAO;

    public ForwardTest getForweardTestById(long id) throws Exception {
        ForwardTest forwardTest = forwardTestDAO.getForwardTestById(id);
        forwardTest.setPeriod(periodDAO.getPeriodByForwardTest(forwardTest));

        return  forwardTest;
    }

}
