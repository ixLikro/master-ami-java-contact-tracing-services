package de.ami.team1.contacttracking.tracking;

import de.ami.team1.contacttracking.util.EnvProvider;
import de.ami.team1.contacttracking.util.Point;
import de.ami.team1.contacttracking.util.RestClientInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@RequestScoped
public class ContactNotifierService {

    @Inject
    EnvProvider envProvider;

    public void notifyViaRESTCall(String mail, Point point, LocalDateTime time, Long userId) {
        NotificationPojo notificationPojo = new NotificationPojo(mail, time, point,userId);
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        try {
            Response re = client.target(envProvider.getGENERATOR_URL()).path("/notify").request().post(Entity.entity(notificationPojo, MediaType.APPLICATION_JSON));
        }catch (Exception ignored){
        }
    }
}
