package de.ami.team1.visualization.control;

import de.ami.team1.util.ConfigService;
import de.ami.team1.util.RestClientInterceptor;
import de.ami.team1.visualization.model.HeatMapValue;
import de.ami.team1.visualization.model.MapPoint;
import de.ami.team1.visualization.model.Movement;
import de.ami.team1.visualization.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
public class DataReceiver implements VisualizationDataProducer {

    @Inject
    private ConfigService config;

    private Client client;
    private String URL;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        URL = config.getANALYSE_URL() + "/analyse";
    }

    @Override
    public List<HeatMapValue> getHeatmapData(MapPoint p1, MapPoint p2) {
        return client
                .target(URL)
                .path("/getHeatmapData")
                .queryParam("p1Lat", p1.getLatitude())
                .queryParam("p1Lon", p1.getLongitude())
                .queryParam("p2Lat", p2.getLatitude())
                .queryParam("p2Lon", p2.getLongitude())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<HeatMapValue>>() {
                });
    }

    @Override
    public List<Movement> getMovementFromUser(User user) {
        return client
                .target(URL)
                .path("getMovementFromUser")
                .queryParam("userId", user.getId())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Movement>>() {
                });
    }

    @Override
    public int countUserWithFilter(String emailFilter) {
        return client
                .target(URL)
                .path("countUserWithFilter")
                .queryParam("filter", emailFilter)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public List<User> getUsersWithFilter(String filter, int page, int pageSize) {
        return client
                .target(URL)
                .path("getUsersWithFilter")
                .queryParam("filter", filter)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<User>>() {
                });
    }
}
