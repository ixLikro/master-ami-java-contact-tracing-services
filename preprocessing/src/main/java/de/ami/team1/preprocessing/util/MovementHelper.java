package de.ami.team1.preprocessing.util;

import de.ami.team1.preprocessing.entities.Movement;
import de.ami.team1.preprocessing.pojo.RawMovementPojo;
import de.ami.team1.preprocessing.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * A Helper Class for Converting MovementData
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class MovementHelper {

    @Inject
    private UserService userService;

    /**
     * Creates a Movement-Object from a raw Movement
     *
     * @param rawMovement Raw Movement Object
     * @return Movement Object
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Movement upcycle(RawMovementPojo rawMovement) {
        int xgrid = (int) (rawMovement.getLatitude() * 1000);
        int ygrid = (int) (rawMovement.getLongitude() * 1000);
        Movement movement = new Movement();
        movement.setGridX(xgrid);
        movement.setGridY(ygrid);
        movement.setLatitude(rawMovement.getLatitude());
        movement.setLongitude(rawMovement.getLongitude());
        movement.setTimestamp(rawMovement.getTimestamp());
        movement.setUser(userService.read((int) rawMovement.getUserId()));
        return movement;


    }

}
