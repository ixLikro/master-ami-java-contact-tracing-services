package de.ami.team1.analyse.util;

import de.ami.team1.analyse.entities.User;
import de.ami.team1.analyse.pojo.MapPointPojo;
import de.ami.team1.analyse.pojo.MovementPojo;
import de.ami.team1.analyse.entities.Movement;
import de.ami.team1.analyse.pojo.UserPojo;

import java.util.ArrayList;
import java.util.List;

public class  MovementConverter {
    public static Movement toDA(MovementPojo movementPojo){
        Movement retMovement = new Movement();
        retMovement.setLatitude(movementPojo.getMapPoint().getLatitude());
        retMovement.setLongitude(movementPojo.getMapPoint().getLongitude());
        retMovement.setTimestamp(movementPojo.getTimestamp());
        return retMovement;
    }

    public static MovementPojo toV(Movement movement){
        MovementPojo retMovementPojo = new MovementPojo();
        retMovementPojo.setMapPoint(new MapPointPojo(movement.getLatitude(), movement.getLongitude()));
        retMovementPojo.setUserId(movement.getUser().getId());
        retMovementPojo.setTimestamp(movement.getTimestamp());
        return retMovementPojo;
    }

    public static List<Movement> toDA(List<MovementPojo> movementPojos){
        List<Movement> retMovements = new ArrayList<>();
        for (MovementPojo movement: movementPojos){
            retMovements.add(toDA(movement));
        }
        return retMovements;
    }

    public static List<MovementPojo> toV(List<Movement> movements){
        List<MovementPojo> retMovements = new ArrayList<>();
        for (Movement movement: movements){
            retMovements.add(toV(movement));
        }
        return retMovements;
    }
}
