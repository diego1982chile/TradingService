package cl.dsoto.trading.services;

import cl.dsoto.trading.managers.PeriodManager;
import cl.dsoto.trading.model.Period;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by des01c7 on 12-12-19.
 */
@RequestScoped
@Produces(APPLICATION_JSON)
@Path("periods")
public class PeriodService {

    @Inject
    PeriodManager periodManager;

    static private final Logger logger = Logger.getLogger(PeriodService.class.getName());

    @GET
    public Response getLast(@QueryParam("periods") @DefaultValue("10") int periods) {
        try {
            List<Period> periodList = periodManager.getLast(periods);
            return Response.ok(periodList).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }
}