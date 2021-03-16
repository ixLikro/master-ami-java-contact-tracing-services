package de.ami.team1.analyse.util;

import de.ami.team1.analyse.entities.User;
import de.ami.team1.analyse.pojo.UserPojo;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    public static User toDA(UserPojo user){
        User retUser = new User();
        retUser.setId(user.getId());
        retUser.setGender(user.getGender());
        retUser.setMail(user.getMail());
        retUser.setDateOfBirth(user.getDateOfBirth());
        retUser.setDateOfInfection(user.getLastInfectionReport());
        return retUser;
    }

    public static UserPojo toV(User user){
        UserPojo retUser = new UserPojo();
        retUser.setId(user.getId());
        retUser.setGender(user.getGender());
        retUser.setMail(user.getMail());
        retUser.setDateOfBirth(user.getDateOfBirth());
        retUser.setLastInfectionReport(user.getDateOfInfection());
        return retUser;
    }

    public static List<User> toDAList(List<UserPojo> users){
        List<User> retUsers = new ArrayList<>();
        for (UserPojo user: users){
            retUsers.add(toDA(user));
        }
        return retUsers;
    }

    public static List<UserPojo> toV(List<User> users){
        List<UserPojo> retUsers = new ArrayList<>();
        for (User user: users){
            retUsers.add(toV(user));
        }
        return retUsers;
    }
}
