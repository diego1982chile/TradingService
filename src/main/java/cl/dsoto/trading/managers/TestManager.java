package cl.dsoto.trading.managers;

import cl.dsoto.trading.daos.PeriodDAO;
import cl.dsoto.trading.model.Period;
import org.omnifaces.cdi.Eager;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by des01c7 on 12-12-19.
 */
@Named
@Eager
@ApplicationScoped
public class TestManager {

    @Inject
    PeriodDAO periodDAO;

    public List<Period> getLast(int periods) throws Exception {
        return periodDAO.getLast(periods);
    }
}
