package cl.dsoto.trading.daos;

import cl.dsoto.trading.model.Optimization;
import cl.dsoto.trading.model.ProblemType;
import cl.dsoto.trading.model.Solution;
import cl.dsoto.trading.model.Strategy;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class SolutionDAO {

    @Inject
    private ValueDAO valueDAO;

    @Resource(lookup = "java:global/TradingDS")
    private DataSource dataSource;

    static private final Logger logger = Logger.getLogger(SolutionDAO.class.getName());

    public Solution persist(Solution solution) throws Exception {

        String sql = "{call trd.create_solution(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, solution.getOptimization().getId());

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                solution.setId(rs.getLong(1));

                Strategy strategy = solution.getOptimization().getStrategy();

                if(strategy.getType().equals(ProblemType.BINARY)) {
                    valueDAO.persistBinaryValues(solution);
                }
                else if(strategy.getType().equals(ProblemType.INTEGER)) {
                    valueDAO.persistIntegerValues(solution);
                }
                else if(strategy.getType().equals(ProblemType.REAL)) {
                    valueDAO.persistRealValues(solution);
                }
                else {
                    throw new Exception("Caso no soportado");
                }
            } else {
                connect.rollback();
                String errorMsg = "El registro no fue creado. Contacte a Desarrollo";
                logger.log(Level.SEVERE, errorMsg);
                throw new Exception(errorMsg);
            }
            //rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

        return solution;
    }

    public List<Solution> getSolutionsByOptimization(Optimization optimization) throws Exception {

        List<Solution> solutions = new ArrayList<>();

        String sql = "{call trd.get_solutions_by_optimization(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, optimization.getId());

            call.execute();

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                solutions.add(createVariableFromResultSet(rs, optimization));
            }

            //rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e);
        }

        return solutions;
    }

    public Solution createVariableFromResultSet(ResultSet rs, Optimization optimization) throws Exception {

        Solution solution = null;

        try {

            long id = rs.getLong("id");

            solution = new Solution(id, optimization);

            Strategy strategy = solution.getOptimization().getStrategy();

            if(strategy.getType().equals(ProblemType.BINARY)) {
                solution.setSolution(valueDAO.getBinaryValues(solution));
            }
            else if(strategy.getType().equals(ProblemType.INTEGER)) {
                solution.setSolution(valueDAO.getIntegerValues(solution));
            }
            else if(strategy.getType().equals(ProblemType.REAL)) {
                solution.setSolution(valueDAO.getRealValues(solution));
            }
            else {
                throw new Exception("Caso no soportado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return solution;
    }

}

