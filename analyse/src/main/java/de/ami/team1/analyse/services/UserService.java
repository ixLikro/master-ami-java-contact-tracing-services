package de.ami.team1.analyse.services;

import de.ami.team1.analyse.crud.CrudService;
import de.ami.team1.analyse.entities.User;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserService extends CrudService<User> {
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
