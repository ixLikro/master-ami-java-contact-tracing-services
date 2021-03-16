package de.ami.team1.analyse.control;

import de.ami.team1.analyse.entities.Movement;
import de.ami.team1.analyse.entities.User;
import de.ami.team1.analyse.pojo.HeatMapValuePojo;
import de.ami.team1.analyse.pojo.MapPointPojo;
import de.ami.team1.analyse.pojo.MovementPojo;
import de.ami.team1.analyse.pojo.UserPojo;
import de.ami.team1.analyse.services.AnalyseMovementService;
import de.ami.team1.analyse.services.UserService;
import de.ami.team1.analyse.util.MovementConverter;
import de.ami.team1.analyse.util.UserConverter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/analyse")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AnalyseController {

    @Inject
    VisualizationDataAnalyzer analyzer;
    @Inject
    AnalyseMovementService movementService;
    @Inject
    UserService userService;


    //ANALYSER

    @GET
    @Path("/getHeatmapData")
    public List<HeatMapValuePojo> getHeatmapData(@QueryParam("p1Lat") double p1Lat,
                                                 @QueryParam("p1Lon") double p1Lon,
                                                 @QueryParam("p2Lat") double p2Lat,
                                                 @QueryParam("p2Lon") double p2Lon) {
        return analyzer.getHeatmapData(new MapPointPojo(p1Lat, p1Lon), new MapPointPojo(p2Lat, p2Lon));
    }

    @GET
    @Path("/getMovementFromUser")
    public List<MovementPojo> getMovementFromUser(@QueryParam("userId") long userId) {
        return analyzer.getMovementFromUser(UserConverter.toV(userService.read(userId)));
    }

    @GET
    @Path("/countUserWithFilter")
    public int countUserWithFilter(@QueryParam("filter") String filter) {
        return analyzer.countUserWithFilter(filter);
    }

    @GET
    @Transactional
    @Path("/getUsersWithFilter")
    public List<UserPojo> getUsersWithFilter(@QueryParam("filter") String filter,
                                             @QueryParam("page") int page,
                                             @QueryParam("pageSize") int pageSize) {
        return analyzer.getUsersWithFilter(filter, page, pageSize);
    }

    @GET
    @Path("/listUserIds")
    public List<Long> getHeatmapData() {
        List<Long> ids = new ArrayList<>();
        List<User> users = userService.readAll();
        users.forEach(user -> ids.add(user.getId()));
        return ids;
    }

    @GET
    @Path("/getLatestCoordinatesByInfectedUsers")
    public List<MapPointPojo> getLatestCoordinatesByInfectedUsers() {
        return analyzer.getLatestCoordinatesByInfectedUsers();
    }

    @GET
    @Path("/getFirstCoordinatesOfInfectedUsers")
    public List<MapPointPojo> getFirstCoordinatesOfInfectedUsers() {
        return analyzer.getLatestCoordinatesByInfectedUsers();
    }

    @GET
    @Path("/getCoordinatesOfInfectedUsersByMonth")
    public List<MapPointPojo> getCoordinatesOfInfectedUsersByMonth(@QueryParam("minusMonth") int minusMonth) {
        return analyzer.getCoordinatesOfInfectedUsersByMonth(minusMonth);
    }

    @GET
    @Path("/getTrendRelevantMovements")
    public List<List<MapPointPojo>> readTrendRelevantMovements() {
        return analyzer.readTrendRelevantMovements();
    }

    //MOVEMENT SERVICE

    @GET
    @Transactional
    @Path("/readLatestMovementByUserID")
    public Movement readLatestMovementByUserID(@QueryParam("userId") long userId) {
        return movementService.readLatestMovementByUserID(userService.read(userId));
    }

    @GET
    @Path("/readAmountOfEntries")
    public Long readAmountOfEntries(@QueryParam("userId") long userId) {
        return movementService.readAmountOfEntries(userService.read(userId));
    }

    @GET
    @Transactional
    @Path("/getListOfUserMovemntsSinceDate")
    public List<Movement> getListOfUserMovemntsSinceDate(@QueryParam("userId") long userId,
                                                         @QueryParam("minusDays") String minusDays) {
        return movementService.getListOfUserMovemntsSinceDate(userService.read(userId), LocalDate.parse(minusDays));
    }

    @GET
    @Transactional
    @Path("/getListOfMovementsInGridAndTimeIntervallWithoutUser")
    public List<Movement> getListOfMovementsInGridAndTimeIntervallWithoutUser(@QueryParam("gridX") int gridX,
                                                                              @QueryParam("gridY") int gridY,
                                                                              @QueryParam("start") String start,
                                                                              @QueryParam("end") String end,
                                                                              @QueryParam("userId") long userId) {
        return movementService.getListOfMovementsInGridAndTimeIntervallWithoutUser(gridX, gridY, LocalDateTime.parse(start), LocalDateTime.parse(end), userService.read(userId));
    }

    @GET
    @Transactional
    @Path("/movement")
    public MovementPojo getMovement(@QueryParam("id") long movementId){
        return MovementConverter.toV(movementService.read(movementId));
    }

}
