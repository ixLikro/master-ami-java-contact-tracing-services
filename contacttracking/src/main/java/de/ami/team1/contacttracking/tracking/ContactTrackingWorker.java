package de.ami.team1.contacttracking.tracking;

import de.ami.team1.contacttracking.pojo.MovementPojo;
import de.ami.team1.contacttracking.pojo.UserPojo;
import de.ami.team1.contacttracking.util.Line;
import de.ami.team1.contacttracking.util.Point;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class ContactTrackingWorker {
    @Inject
    TrackingDataProvider trackingDataProvider;

    @Inject
    ContactNotifierService contactNotifierService;

    public void findContacts(UserPojo user) {
        if (user.getLastInfectionReport() != null) {
            List<MovementPojo> movementList = trackingDataProvider.getListOfUserMovemntsSinceDate(user, user.getLastInfectionReport().minusDays(14));
            if (!movementList.isEmpty()) {
                List<MovementPojo> sameGridMovement = new LinkedList<>();
                sameGridMovement.add(movementList.get(0));
                MovementPojo compMovement = movementList.remove(0);
                while (!movementList.isEmpty()) {
                    MovementPojo nextMovement = movementList.remove(0);
                    if (isInSameGrid(compMovement, nextMovement)) {
                        sameGridMovement.add(nextMovement);
                    } else {
                        gridTracking(sameGridMovement, user);
                        sameGridMovement.clear();
                        sameGridMovement.add(nextMovement);
                    }
                    compMovement = nextMovement;
                }
            }
        }
    }

    /**
     * Find contacts in grids and starts notification
     *
     * @param gridMovement
     */
    private void gridTracking(List<MovementPojo> gridMovement, UserPojo user) {
        int gridX = gridMovement.get(0).getGridX();
        int gridY = gridMovement.get(0).getGridY();
        LocalDateTime start = gridMovement.get(0).getTimestamp();
        LocalDateTime end = gridMovement.get(gridMovement.size() - 1).getTimestamp();
        List<MovementPojo> trackingMovement = trackingDataProvider.getListOfMovementsInGridAndTimeIntervallWithoutUser(gridX, gridY, start, end, user);
        List<Line> trackingLines = new LinkedList<>();
        for (MovementPojo t : trackingMovement) {
            if (t.getNextMovement() == null) {
                trackingLines.add(calculateLineFromMovement(t, t));
            } else {
                trackingLines.add(calculateLineFromMovement(t, trackingDataProvider.getMovement(t.getNextMovement())));
            }
        }

        for (MovementPojo v : gridMovement) {
            Line line;
            if (v.getNextMovement() != null) {
                MovementPojo nextV = trackingDataProvider.getMovement(v.getNextMovement());
                line = calculateLineFromMovement(v, nextV);

                for (int i = 0; i < trackingLines.size(); i++) {
                    //check if time is in between movements
                    MovementPojo otherMovement = trackingMovement.get(i);
                    boolean between = v.getTimestamp().isBefore(otherMovement.getTimestamp()) && nextV.getTimestamp().isAfter(otherMovement.getTimestamp());
                    if (between) {
                        //check if lines intersect
                        Optional<Point> intersect = isIntersecting(line, trackingLines.get(i));
                        intersect.ifPresent(point -> contactNotifierService.notifyViaRESTCall(user.getMail(), point, v.getTimestamp(),v.getUserId()));
                    }
                }
            }


        }


    }

    /**
     * Calulates a line formular out of the points of two movements
     *
     * @param one Movement one
     * @param two Movement two
     * @return formular in style of y = m*x+b
     */
    private Line calculateLineFromMovement(MovementPojo one, MovementPojo two) {
        double m = (one.getMapPoint().getLatitude() - two.getMapPoint().getLatitude()) / (one.getMapPoint().getLongitude() - two.getMapPoint().getLongitude());
        double b = one.getMapPoint().getLatitude() - (m) * one.getMapPoint().getLongitude();
        return new Line(one.getMapPoint().getLongitude(), one.getMapPoint().getLatitude(), two.getMapPoint().getLongitude(), two.getMapPoint().getLatitude(), m, b);
    }


    /**
     * Checks if two lines intersect and if yes, if the intersection is on the segment given on line one
     *
     * @param one Line
     * @param two Line
     * @return boolean
     */
    public Optional<Point> isIntersecting(Line one, Line two) {
        Optional<Point> optionalPoint;
        if (one.getM() == two.getM()) {
            return Optional.empty();
        }
        double x = (two.getB() - one.getB()) / (one.getM() - two.getM());
        double y = one.getM() * x + one.getB();

        //check if point is on the line segement
        if (one.getX1() <= x && x <= one.getX2() && one.getY1() <= y && y <= one.getY2()) {
            Point point = new Point(x, y);
            return Optional.of(point);
        } else return Optional.empty();

    }

    /**
     * Check if two Movements are in the same grid
     *
     * @param movementOne Movement
     * @param movementTwo Movement
     * @return boolean
     */
    private boolean isInSameGrid(MovementPojo movementOne, MovementPojo movementTwo) {
        return (movementOne.getGridX() == movementTwo.getGridX()) && (movementOne.getGridY() == movementTwo.getGridY());
    }

}
