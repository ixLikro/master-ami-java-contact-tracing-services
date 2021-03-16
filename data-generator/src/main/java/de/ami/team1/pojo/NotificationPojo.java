package de.ami.team1.pojo;



import de.ami.team1.util.Point;

import java.time.LocalDateTime;

/**
 * Pojo for a representation of a notification message for a possible infected user
 */

public class NotificationPojo {

    public String email;
    public LocalDateTime timeOfContact;
    public Point contactPoint;
    public long userId;

    /**
     * Constructor for NotificationPojo
     * @param email email of the possible infected user
     * @param timeOfContact timestamp of the possible contact
     * @param contactPoint location of the possible contact
     */
    public NotificationPojo(String email, LocalDateTime timeOfContact, Point contactPoint, long userId) {
        this.email = email;
        this.timeOfContact = timeOfContact;
        this.contactPoint = contactPoint;
        this.userId = userId;
    }

    /**
     * Default Constructor
     */
    public NotificationPojo(){

    }

    //getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimeOfContact() {
        return timeOfContact;
    }

    public void setTimeOfContact(LocalDateTime timeOfContact) {
        this.timeOfContact = timeOfContact;
    }

    public Point getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(Point contactPoint) {
        this.contactPoint = contactPoint;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
