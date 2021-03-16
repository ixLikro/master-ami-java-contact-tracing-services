package de.ami.team1.controller;

import de.ami.team1.generator.DataPointGenerator;
import de.ami.team1.pojo.GenTaskPojo;
import de.ami.team1.pojo.RawMovementPojo;
import de.ami.team1.pojo.UserPojo;
import de.ami.team1.util.EnvProvider;
import de.ami.team1.util.NamePool;
import de.ami.team1.util.RestClientInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * REST Endpoint for generating movementdata
 */

@Path("/generator")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GeneratorController {
    private String url;
    @Inject
    private EnvProvider envProvider;


    /**
     * creates a simulation task with the generation of new users and datapoints as given in the gentaskpojo
     *
     * @param genTaskPojo simulation task
     * @return status of success
     */
    @POST
    @Path("/movement")
    public Response generateMovementData(GenTaskPojo genTaskPojo) {
        //getting the ids of the existing users
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        LinkedList<Long> userIds = new LinkedList<>();
        Response response = client.target(envProvider.getANALYSE_URL()).path("analyse/listUserIds").request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() == 200) {
            Long[] resps = response.readEntity(Long[].class);
            userIds.addAll(Arrays.asList(resps));
        }
        List<Long> usersForGeneration = new LinkedList<>();
        int usersToBeCreated = genTaskPojo.getNewUsers();
        if (userIds.size() < genTaskPojo.getUsers()) {
            usersToBeCreated += genTaskPojo.getUsers() - userIds.size();
            usersForGeneration.addAll(userIds);

        } else {
            Collections.shuffle(userIds);
            Set<Long> randomSet = new HashSet<Long>(userIds.subList(0, genTaskPojo.getUsers()));
            usersForGeneration.addAll(randomSet);
        }


        for (int i = 0; i < usersToBeCreated; i++) {
            UserPojo userPojo = NamePool.generateUser();
            Long id = postNewUser(userPojo);
            if (id != null) {
                usersForGeneration.add(id);
            }
        }
        for (Long id : usersForGeneration) {
            //getLastMovement
            RawMovementPojo pojo = null;
            Response re = client.target(envProvider.getANALYSE_URL()).path("analyse/readLatestMovementByUserID").queryParam("userId", id).request().get();
            if (re.getStatus() == 200) {
                pojo = re.readEntity(RawMovementPojo.class);
            }
            for (int i = 0; i < genTaskPojo.getAmountDataPointPerUsers(); i++) {
                RawMovementPojo gen = DataPointGenerator.generateRandomPointAndTime(id, pojo);
                sendMovement(gen);
                pojo = gen;
            }
        }


        return Response.status(200).build();


    }

    /**
     * Sends a new UserPojo to create a new user
     *
     * @param userPojo UserPojo
     * @return Long id of new user
     */
    private Long postNewUser(UserPojo userPojo) {
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        Response re = client.target(envProvider.getPREPROCESSING_URL()).path("user/").request().post(Entity.entity(userPojo, MediaType.APPLICATION_JSON));
        if (re.getStatus() == 200) {
            return re.readEntity(UserPojo.class).getId();
        }
        return null;
    }

    /**
     * sending a new rawmovement to the server
     *
     * @param rawMovementPojo
     */
    private void sendMovement(RawMovementPojo rawMovementPojo) {
        Client client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        Response re = client.target(envProvider.getDATA_ACCEPTANCE_URL()).path("/movement").request().post(Entity.entity(rawMovementPojo, MediaType.APPLICATION_JSON));
    }


}
