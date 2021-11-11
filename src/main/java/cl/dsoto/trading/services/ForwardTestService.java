package cl.dsoto.trading.services;

import cl.dsoto.trading.managers.ForwardTestManager;
import cl.dsoto.trading.mappers.ForwardTestMapper;
import cl.dsoto.trading.mappers.PeriodMapper;
import cl.dsoto.trading.model.ForwardTest;
import cl.dsoto.trading.model.views.ForwardTestView;
import cl.dsoto.trading.model.views.PeriodView;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by des01c7 on 12-12-19.
 */
@RequestScoped
@Produces(APPLICATION_JSON)
@Path("forwardTests")
public class ForwardTestService {

    @Inject
    ForwardTestManager forwardTestManager;

    static private final Logger logger = Logger.getLogger(ForwardTestService.class.getName());

    @GET
    @Path("{forwardTestId}")
    public Response getForwardTestById(@PathParam("forwardTestId") long forwardTestId) {
        try {
            ForwardTest forwardTest = forwardTestManager.getForweardTestById(forwardTestId);
            ForwardTestView forwardTestView = ForwardTestMapper.mapForwardTest(forwardTest);
            return Response.ok(forwardTestView).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return Response.serverError().build();
    }
}
