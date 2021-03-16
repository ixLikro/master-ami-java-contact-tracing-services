package de.ami.team1.preprocessing.controller;


import de.ami.team1.preprocessing.entities.User;
import de.ami.team1.preprocessing.pojo.InfectionReportPojo;
import de.ami.team1.preprocessing.pojo.UserPojo;
import de.ami.team1.preprocessing.services.UserService;
import de.ami.team1.preprocessing.util.EnvProvider;
import de.ami.team1.preprocessing.util.RestClientInterceptor;
import de.ami.team1.preprocessing.util.UserHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserController {

    @Inject
    UserService userService;
    @Inject
    UserHelper userHelper;
    @Inject
    EnvProvider envProvider;

    private Client client;
    private String URL;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        URL = envProvider.getCONTACT_TRACKING_URL();
    }

    /**
     * Creates a new User from a given Pojo and returns pojo with id of the new created user.
     *
     * @param userPojo Pojo of User
     * @return UserPojo with added id
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserPojo userPojo) {
        User user = userHelper.upcycle(userPojo);
        user = userService.save(user);
        userPojo.setId(user.getId());
        return Response.status(200).entity(userPojo).build();
    }

    @PUT
    @Path("/infected")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reportInfectedUser(InfectionReportPojo infectionReportPojo) {
        User user = userService.read(infectionReportPojo.getUserId());
        user.setDateOfInfection(infectionReportPojo.getDateOfInfection());

        UserPojo userPojo = new UserPojo(user.getId(), user.getMail(), user.getDateOfBirth(), user.getGender(), infectionReportPojo.getDateOfInfection());
        client.target(URL).path("/contacttracking").request().post(Entity.entity(userPojo, MediaType.APPLICATION_JSON_TYPE));

        userService.save(user);
        // TODO: 09.02.21 send Request to analysis
        return Response.status(200).build();
    }
}
