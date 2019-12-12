package cl.dsoto.trading.managers;

import cl.dsoto.trading.daos.OptimizationDAO;
import cl.dsoto.trading.model.Optimization;
import cl.dsoto.trading.model.Period;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class OptimizationManager {

    @Inject
    private OptimizationDAO optimizationDAO;

    public Optimization persist(Optimization optimization) throws Exception {
        optimization = optimizationDAO.persist(optimization);
        return optimization;
    }

    public List<Optimization> getOptimizationsByPeriod(Period period) throws Exception {
        List<Optimization> optimizationList = optimizationDAO.getOptimizationsByPeriod(period);
        return optimizationList;
    }
}
