package de.ami.team1.dataacceptance.scheduling;

import de.ami.team1.dataacceptance.entities.RawMovement;
import de.ami.team1.dataacceptance.services.RawMovementService;
import de.ami.team1.dataacceptance.util.EnvProvider;
import de.ami.team1.dataacceptance.util.MovementDowncycler;
import de.ami.team1.dataacceptance.util.RestClientInterceptor;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Startup
public class MovementScheduler {
    @Inject
    RawMovementService rawMovementService;
    @Inject
    private EnvProvider envProvider;

    @Transactional
    @Scheduled(every = "120s", delay = 1)
    public void atSchedule() {
        List<RawMovement> rawMovementList = loadMovements();
        if (sendRawMovements(rawMovementList)) {
            deleteOldMovements(rawMovementList);
        }

        //todo send data via rest call to preprocessing

    }

    @Transactional
    public void deleteOldMovements(List<RawMovement> rawList) {
        while (rawList.size() > 0) {
            RawMovement raw = rawList.remove(0);
            rawMovementService.delete(raw);
        }
    }


    @Transactional
    public List<RawMovement> loadMovements() {
        return rawMovementService.readAllOrderByTimestamp();
    }

    private boolean sendRawMovements(List<RawMovement> rawMovements) {
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        Response re = client.target(envProvider.getPREPROCESSING_URL()).path("movement/etl").request().post(Entity.entity(MovementDowncycler.createPojos(rawMovements), MediaType.APPLICATION_JSON));
        if (re.getStatus() == 200) {
            return true;
        }
        return false;
    }

}
