package de.ami.team1.pojo;


import java.time.LocalDateTime;

/**
 * POJO for Rawmovement
 */
public class RawMovementPojo {

    private long userId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

    /**
     * Default Constructor
     */
    public RawMovementPojo() {

    }

    /**
     * Constructor for RawMovementPojo
     *
     * @param userId    id of user
     * @param latitude  decimal latitude
     * @param longitude decimal longitude
     * @param timestamp client side timestamp
     */
    public RawMovementPojo(long userId, double latitude, double longitude, LocalDateTime timestamp) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }


    //getter and setter

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
