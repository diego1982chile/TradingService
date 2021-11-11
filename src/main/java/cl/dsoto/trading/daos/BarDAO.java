package cl.dsoto.trading.daos;

import cl.dsoto.trading.model.*;
import cl.dsoto.trading.model.Period;
import org.ta4j.core.Bar;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by des01c7 on 25-03-19.
 */
@RequestScoped
public class BarDAO {

    static private final Logger logger = Logger.getLogger(BarDAO.class.getName());

    @Resource(lookup = "java:global/TradingDS")
    private DataSource dataSource;


    public PeriodBar persist(PeriodBar bar) throws Exception {

        String sql = "{call trd.create_bar_period(?,?,?,?,?,?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            ZonedDateTime time = bar.getEndTime();

            double open = bar.getOpenPrice().doubleValue();
            double high = bar.getMaxPrice().doubleValue();
            double low = bar.getMinPrice().doubleValue();
            double close = bar.getClosePrice().doubleValue();
            double volume = bar.getVolume().doubleValue();

            call.setLong(1, bar.getPeriod().getId());
            call.setTimestamp(2, Timestamp.valueOf(time.toLocalDateTime()));
            call.setDouble(3, open);
            call.setDouble(4, high);
            call.setDouble(5, low);
            call.setDouble(6, close);
            call.setDouble(7, volume);

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                bar.setId(rs.getLong(1));
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

        return bar;
    }


    public ForwardTestBar persist(ForwardTestBar bar) throws Exception {

        String sql = "{call trd.create_forward_test_bar(?,?,?,?,?,?,?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            ZonedDateTime time = bar.getEndTime();

            double open = bar.getOpenPrice().doubleValue();
            double high = bar.getMaxPrice().doubleValue();
            double low = bar.getMinPrice().doubleValue();
            double close = bar.getClosePrice().doubleValue();
            double volume = bar.getVolume().doubleValue();

            call.setLong(1, bar.getForwardTest().getId());
            call.setTimestamp(2, Timestamp.valueOf(time.toLocalDateTime()));
            call.setDouble(3, open);
            call.setDouble(4, high);
            call.setDouble(5, low);
            call.setDouble(6, close);
            call.setDouble(7, volume);

            call.execute();

            ResultSet rs = call.getResultSet();

            if (rs.next()) {
                bar.setId(rs.getLong(1));
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

        return bar;
    }


    public List<PeriodBar> getBars(Period period) throws Exception {
        List<PeriodBar> periodBars = new ArrayList<>();

        String sql = "{call trd.get_bars_by_period(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, period.getId());

            call.execute();

            logger.log(Level.INFO, "Registros recuperadas:");

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                periodBars.add(createPeriodBarFromResultSet(rs, period));
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return periodBars;
    }


    public List<ForwardTestBar> getBars(ForwardTest forwardTest) throws Exception {
        List<ForwardTestBar> periodBars = new ArrayList<>();

        String sql = "{call trd.get_bars_by_forward_test(?)}";

        try (Connection connect = dataSource.getConnection();
             CallableStatement call = connect.prepareCall(sql)) {

            call.setLong(1, forwardTest.getId());

            call.execute();

            logger.log(Level.INFO, "Registros recuperadas:");

            ResultSet rs = call.getResultSet();

            while (rs.next()) {
                periodBars.add(createForwardTestBarFromResultSet(rs, forwardTest));
            }

        } catch (SQLException e) {
            String errorMsg = "Error al recuperar la descripción de la BDD.";
            logger.log(Level.SEVERE, e.getMessage());
            throw new Exception(e.getMessage());
        }

        return periodBars;
    }

    private PeriodBar createPeriodBarFromResultSet(ResultSet resultSet, Period period) throws Exception {

        long id = resultSet.getLong("id");

        ZonedDateTime date = resultSet.getTimestamp("end_time").toLocalDateTime().atZone(ZoneId.systemDefault());

        double open = resultSet.getDouble("open");
        double high = resultSet.getDouble("high");
        double low = resultSet.getDouble("low");
        double close = resultSet.getDouble("close");
        double volume = resultSet.getDouble("volume");

        PeriodBar bar = new PeriodBar(id, date, open, high, low, close, volume, period);

        return bar;
    }

    private ForwardTestBar createForwardTestBarFromResultSet(ResultSet resultSet, ForwardTest forwardTest) throws Exception {

        long id = resultSet.getLong("id");

        ZonedDateTime date = resultSet.getTimestamp("end_time").toLocalDateTime().atZone(ZoneId.systemDefault());

        double open = resultSet.getDouble("open");
        double high = resultSet.getDouble("high");
        double low = resultSet.getDouble("low");
        double close = resultSet.getDouble("close");
        double volume = resultSet.getDouble("volume");

        ForwardTestBar bar = new ForwardTestBar(id, date, open, high, low, close, volume, forwardTest);

        return bar;
    }


}

