package de.ami.team1.analyse.control;

import de.ami.team1.analyse.pojo.HeatMapValuePojo;
import de.ami.team1.analyse.pojo.MapPointPojo;
import de.ami.team1.analyse.pojo.MovementPojo;
import de.ami.team1.analyse.pojo.UserPojo;
import javax.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public interface VisualizationDataProducer {

    /**
     * @param p1 boundaries {@link MapPointPojo} 1
     * @param p2 boundaries {@link MapPointPojo} 2
     * @return all {@link HeatMapValuePojo}s between the given points
     */
    List<HeatMapValuePojo> getHeatmapData(MapPointPojo p1, MapPointPojo p2);

    /**
     * @param user the {@link UserPojo} that {@link MovementPojo}s should be loaded
     * @return all {@link MovementPojo}-Objects from the given user
     */
    List<MovementPojo> getMovementFromUser(UserPojo user);

    /**
     * @param emailFilter the filter string (search string) that should be applied.
     *                    If null or empty the count of total {@link UserPojo}s should be returned!
     * @return the count of {@link UserPojo}s, assuming the given filter is applied
     */
    int countUserWithFilter(String emailFilter);

    /**
     * @param filter   the filter string (search string) that should be applied.
     *                 If null or empty a page of all {@link UserPojo}s should be returned!
     * @param page     the given page index
     * @param pageSize the count of {@link UserPojo}s per page
     * @return a page of {@link UserPojo}s
     */
    List<UserPojo> getUsersWithFilter(String filter, int page, int pageSize);
}
