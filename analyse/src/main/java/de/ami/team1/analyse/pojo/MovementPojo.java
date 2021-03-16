package de.ami.team1.analyse.pojo;

import java.time.LocalDateTime;

public class MovementPojo {

    private long userId;
    private MapPointPojo mapPointPojo;
    private LocalDateTime timestamp;

    public MovementPojo() {
    }

    public MovementPojo(long userId, MapPointPojo mapPointPojo, LocalDateTime timestamp) {
        this.userId = userId;
        this.mapPointPojo = mapPointPojo;
        this.timestamp = timestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public MapPointPojo getMapPoint() {
        return mapPointPojo;
    }

    public void setMapPoint(MapPointPojo mapPointPojo) {
        this.mapPointPojo = mapPointPojo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
