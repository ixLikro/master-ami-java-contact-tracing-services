package de.ami.team1.controller;


import de.ami.team1.pojo.InfectionReportPojo;
import de.ami.team1.pojo.NotificationPojo;
import de.ami.team1.util.EnvProvider;
import de.ami.team1.util.RandomHelper;
import de.ami.team1.util.RestClientInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST-Endpoint for receiving a notification of an possible infected person
 */

@Path("/notify")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class NotifierController {

    @Inject
    EnvProvider envProvider;

    /**
     * Accepts a notificationPojo via http POST
     *
     * @param notificationPojo Notification
     * @return status 200
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notifyInfected(@Valid NotificationPojo notificationPojo) {
        //ignoring the result, in a real world scenario the user would now be notified

        try {
            if (RandomHelper.getInteger(0, 100) < 30) {
                InfectionReportPojo infectionReportPojo = new InfectionReportPojo();
                infectionReportPojo.setDateOfInfection(notificationPojo.timeOfContact.toLocalDate());
                infectionReportPojo.setUserId(notificationPojo.getUserId());
                Client client = ClientBuilder.newBuilder()
                        .register(new RestClientInterceptor())
                        .build();
                Response re = client.target(envProvider.getPREPROCESSING_URL()).path("/user/infected").request().put(Entity.entity(infectionReportPojo, MediaType.APPLICATION_JSON));
            }
        } catch (Exception ignore) {

        }


        return Response.status(200).build();
    }

}
