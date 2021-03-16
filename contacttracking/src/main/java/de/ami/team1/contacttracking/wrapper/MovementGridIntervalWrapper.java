package de.ami.team1.contacttracking.wrapper;

import de.ami.team1.contacttracking.pojo.UserPojo;

import java.time.LocalDateTime;

public class MovementGridIntervalWrapper {
    private int gridX;
    private int gridY;
    private LocalDateTime start;
    private LocalDateTime end;
    private UserPojo user;

    public MovementGridIntervalWrapper(int gridX, int gridY, LocalDateTime start, LocalDateTime end, UserPojo user) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.start = start;
        this.end = end;
        this.user = user;
    }

    public MovementGridIntervalWrapper(){

    }

    //getter and setter

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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public UserPojo getUser() {
        return user;
    }

    public void setUser(UserPojo user) {
        this.user = user;
    }
}
