package de.ami.team1.preprocessing.controller;


import de.ami.team1.preprocessing.entities.Movement;
import de.ami.team1.preprocessing.pojo.RawMovementPojo;
import de.ami.team1.preprocessing.services.MovementService;
import de.ami.team1.preprocessing.services.UserService;
import de.ami.team1.preprocessing.util.DirectionStruct;
import de.ami.team1.preprocessing.util.MovementHelper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * REST Controller for Movementdata
 */
@Path("/movement")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class MovementController {

    @Inject
    MovementHelper movementHelper;
    @Inject
    MovementService movementService;
    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/etl")
    public Response includeRawMovements(List<RawMovementPojo> rawMovementPojoList) {
        for (RawMovementPojo raw : rawMovementPojoList) {
            Movement next = movementHelper.upcycle(raw);
            Movement prevMovement = movementService.readLatestMovementByUserID(userService.read((int) raw.getUserId()));
            if (prevMovement != null) {
                if (!discardMovement(prevMovement, next)) {
                    next = movementService.save(next);
                    prevMovement.setNextMovementPoinnt(next);
                    movementService.merge(prevMovement);
                }
            } else {
                movementService.save(next);
            }
        }
        return Response.status(200).build();
    }

    /**
     * Checking if the moved Distance between two Time more than 1 meters or the timeintervall larger than an hour
     *
     * @param prev previous movement
     * @param next next movement
     * @return bool
     */
    public boolean discardMovement(Movement prev, Movement next) {
        DirectionStruct struct = calcDirection(prev.getLatitude(), prev.getLongitude(), next.getLatitude(), next.getLongitude());
        boolean returner = true;
        if (Math.abs(struct.length) > 1) {
            returner = false;
        }
        if (Math.abs(prev.getTimestamp().until(next.getTimestamp(), ChronoUnit.HOURS)) > 1) {
            returner = false;
        }
        return returner;


    }

    int R = 6373000;

    /**
     * Calculating the heading and distance between two coordinatepoints in decimal system
     *
     * @param lat1 latitude 1
     * @param lon1 longitude 1
     * @param lat2 latitude 2
     * @param lon2 longitude 2
     * @return DirectionStruct with heading and distance in meter
     */
    private DirectionStruct calcDirection(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double d = R * c;

        double x = Math.cos(lat2) * Math.sin(dLon);
        double y = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);

        double heading = Math.toDegrees(Math.atan2(x, y));
        DirectionStruct struct = new DirectionStruct();
        struct.heading = heading;
        struct.length = d;

        return struct;

    }


}
