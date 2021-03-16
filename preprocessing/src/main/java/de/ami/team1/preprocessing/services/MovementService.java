package de.ami.team1.preprocessing.services;


import de.ami.team1.preprocessing.crud.CrudService;
import de.ami.team1.preprocessing.entities.Movement;
import de.ami.team1.preprocessing.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;

@RequestScoped
public class MovementService extends CrudService<Movement> {
    @Override
    protected Class<Movement> getEntityClass() {
        return Movement.class;
    }

    /**
     * Reads the lastest Movement with a given user and returns it
     *
     * @param user user entity
     * @return Movement
     */
    public Movement readLatestMovementByUserID(User user) {
        TypedQuery<Movement> q = em.createNamedQuery("Movement.readLatestByUser", Movement.class);
        q.setParameter("uid", user);
        q.setMaxResults(10);
        try {
            return q.getResultList().get(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Long readAmountOfEntries(User user) {
        TypedQuery<Long> q = em.createNamedQuery("Movement.readCountEntriesByUser", Long.class);
        q.setParameter("uid", user);
        q.setMaxResults(1);
        try {
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
