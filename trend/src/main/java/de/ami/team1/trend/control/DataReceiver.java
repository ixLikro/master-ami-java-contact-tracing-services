package de.ami.team1.trend.control;

import de.ami.team1.trend.model.MapPoint;
import de.ami.team1.util.ConfigService;
import de.ami.team1.util.RestClientInterceptor;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Stateless
public class DataReceiver {

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

    public List<MapPoint> getCoordinatesOfInfectedUsersByMonth(int i) {
        return client
                .target(URL)
                .path("/getCoordinatesOfInfectedUsersByMonth")
                .queryParam("minusMonth", i)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<MapPoint>>() {});
    }

    public List<List<MapPoint>> getTrendRelevantMovements() {
        return client
                .target(URL)
                .path("/getTrendRelevantMovements")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<List<MapPoint>>>() {});
    }

}
