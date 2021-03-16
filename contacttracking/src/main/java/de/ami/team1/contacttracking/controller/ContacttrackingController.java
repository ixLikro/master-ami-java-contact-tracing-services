package de.ami.team1.contacttracking.controller;

import de.ami.team1.contacttracking.pojo.UserPojo;
import de.ami.team1.contacttracking.tracking.ContactTrackingWorker;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Path("")
public class ContacttrackingController {
    @Inject
    ContactTrackingWorker contactTrackingWorker;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/contacttracking")
    public Response trackInfection(UserPojo userPojo) {
        contactTrackingWorker.findContacts(userPojo);
        return Response.status(200).build();
    }
}
