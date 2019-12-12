package cl.dsoto.trading.managers;

import cl.dsoto.trading.daos.StrategyDAO;
import cl.dsoto.trading.model.ProblemType;
import cl.dsoto.trading.model.Strategy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class StrategyManager {

    Map<Integer, Strategy> strategyMap = new ConcurrentHashMap<>();
    Map<String, Strategy> strategiesByName = new ConcurrentHashMap<>();

    @EJB
    private StrategyDAO strategyDAO;

    public Strategy findBy(String name, int variables) {
        if(strategyMap.isEmpty()) {
            try {
                for (Strategy strategy : strategyDAO.getStrategies()) {
                    String strategyName = strategy.getName();
                    String strategyVars = String.valueOf(strategy.getVariables());
                    int strategyKey = strategyName.concat(strategyVars).hashCode();
                    strategyMap.put(strategyKey, strategy);
                }
            }
             catch (Exception e) {
                e.printStackTrace();
            }
        }
        int key = (name + String.valueOf(variables)).hashCode();

        return strategyMap.get(key);
    }

    public List<Strategy> getAll() throws Exception {
        return strategyDAO.getStrategies();
    }

    public List<Strategy> getIntegerProblemTypeStrategies() throws Exception {

        List<Strategy> strategies = new ArrayList<>();

        if(strategyMap.isEmpty()) {
            try {
                for (Strategy strategy : strategyDAO.getStrategies()) {
                    String strategyName = strategy.getName();
                    String strategyVars = String.valueOf(strategy.getVariables());
                    int strategyKey = strategyName.concat(strategyVars).hashCode();
                    strategyMap.put(strategyKey, strategy);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (Strategy strategy : strategyMap.values()) {
            if(strategy.getType().equals(ProblemType.INTEGER)) {
                strategies.add(strategy);
            }
        }

        return strategies;
    }

    public List<Strategy> getBinaryProblemTypeStrategies() throws Exception {
        List<Strategy> strategies = new ArrayList<>();

        if(strategyMap.isEmpty()) {
            try {
                for (Strategy strategy : strategyDAO.getStrategies()) {
                    String strategyName = strategy.getName();
                    String strategyVars = String.valueOf(strategy.getVariables());
                    int strategyKey = strategyName.concat(strategyVars).hashCode();
                    strategyMap.put(strategyKey, strategy);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Strategy strategy : strategyMap.values()) {
            if(strategy.getType().equals(ProblemType.BINARY)) {
                strategies.add(strategy);
            }
        }

        return strategies;
    }

    public Strategy getByName(String name) {
        if(strategiesByName.isEmpty()) {
            try {
                for (Strategy strategy : strategyDAO.getStrategies()) {
                    strategiesByName.put(strategy.getName(), strategy);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strategiesByName.get(name);
    }
}
