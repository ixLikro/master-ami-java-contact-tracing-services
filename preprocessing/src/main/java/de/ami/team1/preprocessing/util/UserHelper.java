package de.ami.team1.preprocessing.util;


import de.ami.team1.preprocessing.entities.User;
import de.ami.team1.preprocessing.pojo.UserPojo;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

/**
 * A Helper Class for Converting User Entities
 */

@Transactional
@RequestScoped
public class UserHelper {

    public User upcycle(UserPojo userPojo) {
        User user = new User();
        user.setMail(userPojo.getMail());
        user.setDateOfBirth(userPojo.getDateOfBirth());
        user.setGender(userPojo.getGender());
        user.setDateOfInfection(null);
        return user;
    }
}
