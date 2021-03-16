package de.ami.team1.dataacceptance.controller;

import de.ami.team1.dataacceptance.entities.RawMovement;
import de.ami.team1.dataacceptance.pojo.RawMovementPojo;
import de.ami.team1.dataacceptance.services.RawMovementService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Controller for Movementdata
 */
@Path("/movement")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class RawMovementController {

    @Inject
    private RawMovementService rawMovementService;

    /**
     * Accecpts a RawMovementPojo and persists it in the buffer database.
     *
     * @param rawMovementPojo Pojo
     * @return http response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRawMovement(RawMovementPojo rawMovementPojo) {
        RawMovement rawMovement = rawMovementPojo.createPersitableObject();
        rawMovement = rawMovementService.save(rawMovement);
        return Response.status(200).build();

    }
}
