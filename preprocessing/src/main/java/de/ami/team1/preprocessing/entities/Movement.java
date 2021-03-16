package de.ami.team1.preprocessing.entities;

import de.ami.team1.preprocessing.crud.CrudEntity;
import de.ami.team1.preprocessing.util.DirectionStruct;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Class for persisting Movement information of users
 */

@Table(name = "movement")
@NamedQueries({
        @NamedQuery(name = "Movement.readLatestByUser", query = "SELECT m from Movement m where m.user = :uid ORDER BY m.timestamp desc"),
        @NamedQuery(name = "Movement.readCountEntriesByUser", query = "SELECT count(m) FROM Movement m where  m.user = :uid")
})
@Entity
public class Movement implements CrudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    //using Decimal degrees (DD): 41.40338, 2.17403
    @Column(name = "latitude")
    @NotNull
    private double latitude;

    @Column(name = "longitude")
    @NotNull
    private double longitude;

    @Column(name = "time_stamp")
    @NotNull
    private LocalDateTime timestamp;

    @Column(name = "grid_x")
    @NotNull
    private int gridX;

    @Column(name = "grid_y")
    @NotNull
    private int gridY;

    @ManyToOne(targetEntity = Movement.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "next_movement_id")
    private Movement nextMovment;

    @Column(name = "heading")
    private double heading;

    @Column(name = "distance")
    private double distance;

    /**
     * Constructor for Movement
     *
     * @param user        userid
     * @param latitude    latitude
     * @param longitude   longitude
     * @param timestamp   timestamp as datetime
     * @param gridX       x grid
     * @param gridY       ygrid
     * @param nextMovment nextmovment point
     * @param heading     heading to next point
     * @param distance    distance to next point
     */
    public Movement(@NotNull User user, @NotNull double latitude, @NotNull double longitude, @NotNull LocalDateTime timestamp, @NotNull int gridX, @NotNull int gridY, Movement nextMovment, double heading, double distance) {
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.gridX = gridX;
        this.gridY = gridY;
        this.nextMovment = nextMovment;
        this.heading = heading;
        this.distance = distance;
    }

    /**
     * Default Constructor
     */
    public Movement() {

    }

    /**
     * Setting the nextMovementpoint and calculating distance and heading
     *
     * @param next next Movement
     */
    public void setNextMovementPoinnt(Movement next) {
        this.nextMovment = next;
        DirectionStruct struct = calcDirection(this.latitude, this.longitude, next.latitude, next.latitude);
        this.heading = struct.heading;
        this.distance = struct.length;

    }

    /**
     * Returns true, if a next movement is existing, false if not
     *
     * @return boolean
     */
    public boolean hasNextMovement() {
        return nextMovment != null;
    }


    private DirectionStruct calcDirection(double lat1, double lon1, double lat2, double lon2) {
        int R = 6373000;
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


    @Override
    public boolean isPersistent() {
        return false;
    }

    //getter and setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public Movement getNextMovment() {
        return nextMovment;
    }

    public void setNextMovment(Movement nextMovment) {
        this.nextMovment = nextMovment;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
