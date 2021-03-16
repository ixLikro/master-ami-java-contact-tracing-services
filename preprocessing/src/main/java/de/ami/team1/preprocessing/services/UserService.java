package de.ami.team1.preprocessing.services;

import de.ami.team1.preprocessing.crud.CrudService;
import de.ami.team1.preprocessing.entities.User;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserService extends CrudService<User> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
