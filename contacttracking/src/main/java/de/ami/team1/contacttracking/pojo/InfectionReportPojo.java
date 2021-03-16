package de.ami.team1.contacttracking.pojo;

import java.time.LocalDate;

public class InfectionReportPojo {
    private long userId;
    private LocalDate dateOfInfection;

    public InfectionReportPojo(long userId, LocalDate dateOfInfection) {
        this.userId = userId;
        this.dateOfInfection = dateOfInfection;
    }

    public InfectionReportPojo(){

    }

    //getter and setter

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfInfection() {
        return dateOfInfection;
    }

    public void setDateOfInfection(LocalDate dateOfInfection) {
        this.dateOfInfection = dateOfInfection;
    }
}
