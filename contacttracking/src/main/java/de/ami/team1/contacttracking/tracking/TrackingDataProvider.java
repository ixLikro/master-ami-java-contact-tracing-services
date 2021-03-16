package de.ami.team1.contacttracking.tracking;

import de.ami.team1.contacttracking.pojo.MovementPojo;
import de.ami.team1.contacttracking.pojo.UserPojo;
import de.ami.team1.contacttracking.util.EnvProvider;
import de.ami.team1.contacttracking.util.RestClientInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RequestScoped
public class TrackingDataProvider {
    @Inject
    EnvProvider envProvider;

    public List<MovementPojo> getListOfUserMovemntsSinceDate(UserPojo user, LocalDate minusDays) {
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        Response re = client.target(envProvider.getANALYSE_URL())
                .path("analyse/getListOfUserMovemntsSinceDate")
                .queryParam("userId", user.getId())
                .queryParam("minusDays", minusDays.toString())
                .request()
                .get();
        return new LinkedList<>(Arrays.asList(re.readEntity(MovementPojo[].class)));
    }

    public List<MovementPojo> getListOfMovementsInGridAndTimeIntervallWithoutUser(int gridX, int gridY, LocalDateTime start, LocalDateTime end, UserPojo user) {
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        Response re = client.target(envProvider.getANALYSE_URL())
                .path("analyse/getListOfMovementsInGridAndTimeIntervallWithoutUser")
                .queryParam("gridY", gridY)
                .queryParam("start", start.toString())
                .queryParam("end", end.toString())
                .queryParam("gridX", gridX)
                .queryParam("userId", user.getId())
                .request()
                .get();
        return Arrays.asList(re.readEntity(MovementPojo[].class));
    }

    public MovementPojo getMovement(Long nextMovement) {
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        Response re = client.target(envProvider.getANALYSE_URL())
                .path("analyse/movement")
                .queryParam("id", nextMovement)
                .request()
                .get();
        return re.readEntity(MovementPojo.class);
    }
}
