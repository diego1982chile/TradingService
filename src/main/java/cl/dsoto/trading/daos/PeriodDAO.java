package cl.dsoto.trading.daos;

import cl.dsoto.trading.model.Period;
import cl.dsoto.trading.model.QPeriod;

import javax.enterprise.context.RequestScoped;
import javax.jdo.*;
import javax.jdo.query.DateExpression;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by des01c7 on 12-12-19.
 */
@RequestScoped
public class PeriodDAO {

    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("PU");

    public List<Period> getLast(int periods) throws Exception {

        PersistenceManager pm = pmf.getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        try
        {
            tx.begin();

            JDOQLTypedQuery<Period> tq = pm.newJDOQLTypedQuery(Period.class);
            //QPeriod cand = QPeriod.candidate();
            List<Period> results = tq
                    //tq.filter(cand.count().eq(periods))
                            //.orderBy(cand.end.desc())
                            .executeList();
            tx.commit();

            return results;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }

            pm.close();
        }
    };
}
