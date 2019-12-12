package cl.dsoto.trading.daos;

import cl.dsoto.trading.model.*;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.EMPTY_LIST;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class OptimizationDAO {

    static private final Logger logger = Logger.getLogger(OptimizationDAO.class.getName());

    @Inject
    private ObjectiveDAO objectiveDAO;

    @Inject
    private SolutionDAO solutionDAO;

    @Inject
    private StrategyDAO strategyDAO;

    @Resource(lookup = "java:global/TradingDS")
    private DataSource dataSource;

    public Optimization persist(Optimization optimization) throws Exception {

        String sql = "{call trd.create_optimization(?,?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql);
        ) {

            call.setLong(1, optimization.getPeriod().getId());
            call.setLong(2, optimization.getStrategy().getId());
            call.setTimestamp(3, optimization.getTimestamp());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                optimization.setId(rs.getLong(1));

                for (Objective objective : optimization.getObjectives()) {
                    objective.setOptimization(optimization);
                    objectiveDAO.persist(objective);
                }

                for (Solution solution : optimization.getSolutions()) {
                    solution.setOptimization(optimization);
                    solutionDAO.persist(solution);
                }

            } else {
                connect.rollback();
                String errorMsg = "El registro no fue creado. Contacte a Desarrollo";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }
            //rs.close();
            //connect.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

        return optimization;
    }

    public List<Optimization> getOptimizationsByPeriod(Period period) throws Exception {

        List<Optimization> optimizationList = new ArrayList<>();

        String sql = "{call trd.get_optimizations_by_period(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, period.getId());

            call.execute();

            logger.log(Level.INFO, "Registros recuperadas:");

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                optimizationList.add(createOptimizationFromResultSet(rs, period));
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        } catch (Exception ex) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, ex.getMessage());
            throw new Exception(ex.getMessage());
        }

        return optimizationList;
    }

    private Optimization createOptimizationFromResultSet(ResultSet resultSet, Period period) throws Exception {

        Optimization optimization = null;

        try {

            long id = resultSet.getLong("id");
            long idStrategy = resultSet.getLong("id_strategy");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");

            Strategy strategy = strategyDAO.getStrategyById(idStrategy);

            optimization = new Optimization(id, period, strategy, timestamp, EMPTY_LIST, EMPTY_LIST);

            List<Objective> objectives = objectiveDAO.getObjectivesByOptimization(optimization);
            List<Solution> solutions = solutionDAO.getSolutionsByOptimization(optimization);

            optimization.setObjectives(objectives);
            optimization.setSolutions(solutions);

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

        return optimization;

    }

}
