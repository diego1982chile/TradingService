package cl.dsoto.trading.model.views;

import cl.dsoto.trading.model.ForwardTest;
import org.ta4j.core.BaseBar;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static cl.dsoto.trading.model.DAO.NON_PERSISTED_ID;

/**
 * Created by des01c7 on 29-03-19.
 */
public class ForwardTestBarView extends BaseBar implements Serializable {

    /** El identificador único de la entidad, inicialmente fijado en <code>NON_PERSISTED_ID</code>. */
    private long id = NON_PERSISTED_ID;

    @JsonbTransient
    ForwardTestView forwardTestView;

    private double group;

    public ForwardTestBarView(long id, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume, double group, ForwardTestView forwardTest) {
        super(endTime, openPrice, highPrice, lowPrice, closePrice, volume);
        this.id = id;
        this.group = group;
        //this.forwardTestView = forwardTest;
    }

    public ForwardTestBarView(ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume, double group, ForwardTestView forwardTestView) {
        super(endTime, openPrice, highPrice, lowPrice, closePrice, volume);
        this.group = group;
        this.forwardTestView = forwardTestView;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ForwardTestView getForwardTest() {
        return forwardTestView;
    }

    public void setForwardTest(ForwardTestView forwardTestView) {
        this.forwardTestView = forwardTestView;
    }

    public double getGroup() {
        return group;
    }

    public void setGroup(double group) {
        this.group = group;
    }
}
