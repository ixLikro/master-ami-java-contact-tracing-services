package de.ami.team1.contacttracking.wrapper;

import de.ami.team1.contacttracking.pojo.UserPojo;

import java.time.LocalDate;

public class UserDateWrapper {
    private UserPojo userPojo;
    private LocalDate localDate;

    public UserDateWrapper(UserPojo userPojo, LocalDate localDate) {
        this.userPojo = userPojo;
        this.localDate = localDate;
    }

    public UserDateWrapper(){

    }


    //getter and setter

    public UserPojo getUserPojo() {
        return userPojo;
    }

    public void setUserPojo(UserPojo userPojo) {
        this.userPojo = userPojo;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
