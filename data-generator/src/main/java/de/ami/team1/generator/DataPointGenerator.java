package de.ami.team1.generator;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import de.ami.team1.pojo.RawMovementPojo;
import de.ami.team1.util.BigCity;
import de.ami.team1.util.RandomHelper;

import java.time.LocalDateTime;

/**
 * Generates a random Movement point, which may be used for simulating infectious events
 */
public class DataPointGenerator {

    //this is roughly lower saxony and surroundings

    private final static double topLeftLat = 53.6612;
    private final static double topLeftLong = 7.30505;

    private final static double botRightLat = 51.58678;
    private final static double botRightLong = 11.19027;
    //max distance in coordinates
    private final static double maxDistance = 0.002;


    /**
     * Generates a Movementpoint, which is in the relative distance of previous movementpoint, if this creates
     * @param id id of the User for whom the datapoint will be created
     * @param prevMov nullable previous movementpoint
     * @return RawMovementPojo with random data
     */
    public static RawMovementPojo generateRandomPointAndTime(@NotNull Long id, @Nullable RawMovementPojo prevMov) {
        if (prevMov == null) {
            //first movement of the user
            double lat, lon;

            // 70% percent probability that the first movement starts in a city
            if (RandomHelper.hitPercentChance(70d)){
                BigCity city = RandomHelper.getCityBasedOnPopulationProbability();
                lat = RandomHelper.getDouble(city.getLatitude() - (maxDistance * 4), city.getLatitude() + (maxDistance * 4));
                lon = RandomHelper.getDouble(city.getLongitude() - (maxDistance * 4), city.getLongitude() + (maxDistance * 4));
            }else {
                // not a city -> totally random in roughly lower saxony
                lat = RandomHelper.getDouble(topLeftLat, botRightLat);
                lon = RandomHelper.getDouble(topLeftLong, botRightLong);
            }

            LocalDateTime timestamp = LocalDateTime.now().minusDays(365);
            timestamp = timestamp.plusSeconds(RandomHelper.getLong(0, 31536000));
            return new RawMovementPojo(id, lat, lon, timestamp);

        } else {
            //generate data depending on a prev movement
            double lat = RandomHelper.getDouble(prevMov.getLatitude() - maxDistance, prevMov.getLatitude() + maxDistance);
            double lon = RandomHelper.getDouble(prevMov.getLongitude() - maxDistance, prevMov.getLongitude() + maxDistance);
            LocalDateTime timestamp = prevMov.getTimestamp().plusSeconds(RandomHelper.getLong(60, 86400));
            return new RawMovementPojo(id, lat, lon, timestamp);
        }
    }

}
