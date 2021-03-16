package de.ami.team1.preprocessing.pojo;

import java.time.LocalDate;

/**
 * POJO for {@link de.ami.team1.dataacceptance.entities.User}
 */

public class UserPojo {
    private long id;
    private String mail;
    private LocalDate dateOfBirth;
    private char gender;
    private LocalDate lastInfectionReport;

    /**
     * Constructor for the User pojo with the id
     *
     * @param id
     * @param mail
     * @param dateOfBirth
     * @param gender
     */
    public UserPojo(long id, String mail, LocalDate dateOfBirth, char gender, LocalDate lastInfectionReport) {
        this.id = id;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.lastInfectionReport = lastInfectionReport;
    }

    /**
     * Constructor for the User pojo without the id
     *
     * @param mail
     * @param dateOfBirth
     * @param gender
     */
    public UserPojo(String mail, LocalDate dateOfBirth, char gender) {
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    /**
     * Default Constructor for UserPojo
     */
    public UserPojo() {
    }

    //getter and setter


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public LocalDate getLastInfectionReport() {
        return lastInfectionReport;
    }

    public void setLastInfectionReport(LocalDate lastInfectionReport) {
        this.lastInfectionReport = lastInfectionReport;
    }
}
