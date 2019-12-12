package cl.dsoto.trading.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.Properties;

/**
 * Created by des01c7 on 11-12-19.
 */
public class DataSourceFactory {

    static private final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    private static final DataSourceFactory instance = new DataSourceFactory();

    private static Properties properties = new Properties();
    private static PersistenceManagerFactory pmf;


    /**
     * Constructor privado para el Singleton del Factory.
     */
    private DataSourceFactory() {
        //properties.setProperty("datanucleus.ConnectionFactoryName","jdbc/datanucleus");
        properties.setProperty("datanucleus.ConnectionFactoryName","java:global/TradingDS");
        pmf = JDOHelper.getPersistenceManagerFactory(properties);
    }

    public static DataSourceFactory getInstance() {
        return instance;
    }

    public PersistenceManager getConnection() {
        try {
            return pmf.getPersistenceManager();
        } catch (Exception e) {
            logger.error("Error al conectarse a BD", e);
        }
        finally {
        }
        // Si llegamos a este punto, quiere decir que no hay conexiones disponibles en el pool (se deberia lanzar una excepcion )
        return null;
    }

}
