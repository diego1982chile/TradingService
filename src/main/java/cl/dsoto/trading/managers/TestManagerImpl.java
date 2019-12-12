package cl.dsoto.trading.managers;

import cl.dsoto.trading.daos.PeriodDAO;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by des01c7 on 12-12-19.
 */
//@Named
//@Eager
//@ApplicationScoped
@Singleton
@Startup
public class TestManagerImpl implements TestManager {

    @Inject
    PeriodDAO periodDAO;

    static private final Logger logger = Logger.getLogger(TestManagerImpl.class.getName());

    @PostConstruct
    public void test() {
        try {
            /*
            List periods = periodDAO.getLast(10);
            for (Object period : periods) {
                System.out.println(period.toString());
            }
            */
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
