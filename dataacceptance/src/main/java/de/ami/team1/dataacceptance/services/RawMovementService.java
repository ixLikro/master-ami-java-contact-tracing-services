package de.ami.team1.dataacceptance.services;

import de.ami.team1.dataacceptance.crud.CrudService;
import de.ami.team1.dataacceptance.entities.RawMovement;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@RequestScoped
public class RawMovementService extends CrudService<RawMovement> {

    public Long countRawMovements() {
        TypedQuery<Long> q = em.createNamedQuery("RawMovement.countAll", Long.class);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RawMovement> readAllOrderByTimestamp() {
        TypedQuery<RawMovement> q = em.createNamedQuery("RawMovement.readAllOrderByTimestamp", RawMovement.class);
        q.setMaxResults(200);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    protected Class<RawMovement> getEntityClass() {
        return RawMovement.class;
    }
}
