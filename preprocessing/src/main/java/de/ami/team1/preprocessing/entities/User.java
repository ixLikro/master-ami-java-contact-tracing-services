package de.ami.team1.preprocessing.entities;

import de.ami.team1.preprocessing.crud.CrudEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Entity representation for a user
 */

@Entity
@Table(name = "users")
public class User implements CrudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private char gender;

    @Column(name = "date_of_infection")
    private LocalDate dateOfInfection;

    /**
     * Constructor for creating a new User
     *
     * @param mail
     * @param dateOfBirth
     * @param gender
     */
    public User(String mail, LocalDate dateOfBirth, char gender, LocalDate dateOfInfection) {
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.dateOfInfection = dateOfInfection;
    }

    /**
     * Default Constructor
     */
    public User() {
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

    public LocalDate getDateOfInfection() {
        return dateOfInfection;
    }

    public void setDateOfInfection(LocalDate dateOfInfection) {
        this.dateOfInfection = dateOfInfection;
    }
}
