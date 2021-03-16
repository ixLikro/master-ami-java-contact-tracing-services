package de.ami.team1.dataacceptance.util;

import de.ami.team1.dataacceptance.entities.RawMovement;
import de.ami.team1.dataacceptance.pojo.RawMovementPojo;

import java.util.LinkedList;
import java.util.List;

public class MovementDowncycler {

    public static RawMovementPojo createPojo(RawMovement movement) {
        RawMovementPojo pojo = new RawMovementPojo();
        pojo.setLatitude(movement.getLatitude());
        pojo.setLatitude(movement.getLongitude());
        pojo.setTimestamp(movement.getTimestamp());
        pojo.setUserId(movement.getUserId());
        return pojo;
    }


    public static List<RawMovementPojo> createPojos(List<RawMovement> movements) {
        LinkedList<RawMovementPojo> rawMovementPojos = new LinkedList<>();
        for (RawMovement mov : movements) {
            rawMovementPojos.add(createPojo(mov));
        }
        return rawMovementPojos;
    }
}
