package cl.dsoto.trading.model;

import org.ta4j.core.BaseBar;
import org.ta4j.core.Decimal;
import org.ta4j.core.TimeSeries;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 29-03-19.
 */
@PersistenceCapable
@DatastoreIdentity(strategy= IdGeneratorStrategy.SEQUENCE, sequence="seq_bar_period")
public class PeriodBar implements Serializable {

    @Column(name="id_period")
    Period period;

    @Column(name="end_time")
    ZonedDateTime endTime;

    @Column(name="open")
    double openPrice;

    @Column(name="high")
    double highPrice;

    @Column(name="low")
    double lowPrice;

    @Column(name="close")
    double closePrice;

    double volume;

    public PeriodBar(Period period, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
        this.period = period;
        this.endTime = endTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    BaseBar map() {
        return new BaseBar(this.endTime, this.openPrice, this.highPrice, this.lowPrice, this.closePrice, this.volume);
    }
}
