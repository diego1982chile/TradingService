package cl.dsoto.trading.model.views;

import cl.dsoto.trading.model.Period;
import org.ta4j.core.BaseBar;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static cl.dsoto.trading.model.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 29-03-19.
 */
public class PeriodBarView extends BaseBar implements Serializable {

    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    private long group;

    @JsonbTransient
    PeriodView period;

    public PeriodBarView(long id, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume, long group, PeriodView period) {
        super(endTime, openPrice, highPrice, lowPrice, closePrice, volume);
        this.id = id;
        this.group = group;
        this.period = period;
    }

    public PeriodBarView(ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume, long group, PeriodView period) {
        super(endTime, openPrice, highPrice, lowPrice, closePrice, volume);
        this.group = group;
        this.period = period;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PeriodView getPeriod() {
        return period;
    }

    public void setPeriod(PeriodView period) {
        this.period = period;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }
}
