package de.ami.team1.contacttracking.pojo;

import java.time.LocalDateTime;

public class MovementPojo {

    private long id;
    private long userId;
    private MapPointPojo mapPointPojo;
    private LocalDateTime timestamp;
    private int gridX;
    private int gridY;
    private Long nextMovement;

    public MovementPojo() {
    }

    public MovementPojo(long userId, MapPointPojo mapPointPojo, LocalDateTime timestamp) {
        this.userId = userId;
        this.mapPointPojo = mapPointPojo;
        this.timestamp = timestamp;
    }

    public MovementPojo(long id, long userId, MapPointPojo mapPointPojo, LocalDateTime timestamp, int gridX, int gridY, Long nextMovement) {
        this.id = id;
        this.userId = userId;
        this.mapPointPojo = mapPointPojo;
        this.timestamp = timestamp;
        this.gridX = gridX;
        this.gridY = gridY;
        this.nextMovement = nextMovement;
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

    public MapPointPojo getMapPointPojo() {
        return mapPointPojo;
    }

    public void setMapPointPojo(MapPointPojo mapPointPojo) {
        this.mapPointPojo = mapPointPojo;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getNextMovement() {
        return nextMovement;
    }

    public void setNextMovement(Long nextMovement) {
        this.nextMovement = nextMovement;
    }
}
