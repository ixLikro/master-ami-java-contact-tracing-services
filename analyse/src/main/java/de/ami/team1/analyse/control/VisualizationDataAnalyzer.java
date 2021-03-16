package de.ami.team1.analyse.control;

import de.ami.team1.analyse.entities.Movement;
import de.ami.team1.analyse.entities.User;
import de.ami.team1.analyse.services.AnalyseMovementService;
import de.ami.team1.analyse.util.*;
import de.ami.team1.analyse.pojo.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.*;

@ApplicationScoped
public class VisualizationDataAnalyzer implements VisualizationDataProducer {

    @Inject
    AnalyseMovementService movementService;
    UserConverter userConv = new UserConverter();
    MovementConverter moveConv = new MovementConverter();

    /**
     * Retrieve the heatmap of a specific area. The area is defined as a rectangle between the given points.
     * @param p1 Point A
     * @param p2 Point B
     * @return List of HeatMapValue for each subarea between the given points.
     */
    public List<HeatMapValuePojo> getHeatmapData(MapPointPojo p1, MapPointPojo p2) {
        double minLongitude, maxLongitude, minLatitude, maxLatitude;

        //1. convert map part
        if (p1.getLatitude() <= p2.getLatitude()) {
            minLatitude = p1.getLatitude();
            maxLatitude = p2.getLatitude();
        } else {
            minLatitude = p2.getLatitude();
            maxLatitude = p1.getLatitude();
        }
        if (p1.getLongitude() <= p2.getLongitude()) {
            minLongitude = p1.getLongitude();
            maxLongitude = p2.getLongitude();
        } else {
            minLongitude = p2.getLongitude();
            maxLongitude = p1.getLongitude();
        }

        //2. retrieve data from boundaries
        return movementService.readHeatMap(minLongitude, maxLongitude, minLatitude, maxLatitude);

    }

    /**
     * Get the last 1000 movements from given user.
     * @param user User to get data from
     * @return List of Movement Points
     */
    public List<MovementPojo> getMovementFromUser(UserPojo user) {
        List<MovementPojo> ret = new ArrayList<>();
        List<de.ami.team1.analyse.entities.Movement> movements
                = movementService.readMovementByUserID(userConv.toDA(user), 1000);
        for (de.ami.team1.analyse.entities.Movement move : movements) {
            ret.add(moveConv.toV(move));
        }

        return  ret;
    }

    /**
     * Amount of users with matching filter.
     * @param emailFilter email filter
     * @return Amount of matching entries.
     */
    public int countUserWithFilter(String emailFilter) {
        return movementService.countUsersWithEmailFilter(emailFilter);
    }

    /**
     * Get users with a given filter and page.
     * @param filter filter for email address
     * @param page page
     * @param pageSize items per page
     * @return List of users.
     */
    public List<UserPojo> getUsersWithFilter(String filter, int page, int pageSize) {
        return userConv.toV(movementService.getUsersWithMailFilter(filter, page, pageSize));
    }

    /**
     * Get the latest Point of a user.
     * @return List with the last movement of an infected user.
     */
    public List<MapPointPojo> getLatestCoordinatesByInfectedUsers() {
        return movementToMapPointList(movementService.readLatestMovementByInfectedUsers());
    }

    /**
     * Get the coordinates of all infected users of the last x months.
     * @param minusMonth amount of months
     * @return List of Map Point from infected users.
     */
    public List<MapPointPojo> getCoordinatesOfInfectedUsersByMonth(int minusMonth) {
        LocalDate initial = LocalDate.now();
        if (minusMonth != 0) {
            initial = initial.minusMonths(minusMonth);
        }
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        List<Movement> activeCases = new ArrayList<>();
        List<User> infected = movementService.readInfectedUsers(start, end);
        for (User user : infected) {
            activeCases.add(movementService.readLatestMovementByUserID(user));
        }
        return movementToMapPointList(activeCases);
    }


    public List<List<MapPointPojo>> readTrendRelevantMovements() {
        List<User> infected = movementService.readInfectedUsers();
        List<List<MapPointPojo>> trendData = new ArrayList<>();
        List<Movement> activeCases = new ArrayList<>();
        List<Movement> incidences = new ArrayList<>();
        for (User user : infected) {
            activeCases.add(movementService.readLatestMovementByUserID(user));
            incidences.add(movementService.readFirstMovementByInfectedUser(user));
        }
        trendData.add(movementToMapPointList(activeCases));
        trendData.add(movementToMapPointList(incidences));
        return trendData;
    }

    /**
     * Convert Movement List ot MapPoint List
     * @param movements movements to parse
     * @return map point list representation
     */
    private List<MapPointPojo> movementToMapPointList(List<de.ami.team1.analyse.entities.Movement> movements) {
        List<MapPointPojo> mapPointPojos = new ArrayList<>();
        if (movements != null) {
            for (de.ami.team1.analyse.entities.Movement movement : movements) {
                if (movement != null) {
                    mapPointPojos.add(new MapPointPojo(movement.getLatitude(), movement.getLongitude()));
                }
            }
            return mapPointPojos;
        } else {
            return null;
        }
    }
}
