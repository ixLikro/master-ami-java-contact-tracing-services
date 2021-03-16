package de.ami.team1.visualization.view;

import de.ami.team1.util.ConfigService;
import de.ami.team1.util.RestClientInterceptor;
import de.ami.team1.visualization.model.api.NewUserPojo;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * this controller handles the new user part of the landingpage.
 */
@Named
@ViewScoped
public class LandingpageNewUserController implements Serializable {
    @Inject
    LandingpageManuallyController userController;

    @Inject
    private ConfigService config;


    Client client;
    private String URL;
    private boolean showError;
    private String errorString = "Fehler bei dem Speichern des neuen Nutzers. Bitte überprüfe deine Eingaben und versuche es erneut.";

    //dropdown values
    private Map<String, Integer> yearValues;
    private Map<String, Integer> dayValues;

    private char gender;
    private int year, month, day;
    private String email;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newBuilder()
                .register(new RestClientInterceptor())
                .build();
        URL = config.getPREPROCESSING_URL();

        //init year and day dropdowns
        yearValues = new LinkedHashMap<>();
        dayValues = new LinkedHashMap<>();
        // 1 to 31
        for (int i = 1; i <= 31; i++) {
            dayValues.put(i + "", i);
        }
        //current year to current year - 130
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear; i > currentYear - 130; i--) {
            yearValues.put(i + "", i);
        }
    }

    /**
     * creates a new user, with the data that the user typed in.
     */
    public void createUser() {
        showError = false;
        Response response = client
                .target(URL)
                .path("user")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(new NewUserPojo(email, LocalDate.of(year, month, day), gender), MediaType.APPLICATION_JSON_TYPE));

        if(!(response.getStatus() >= 200 && response.getStatus() < 300)){
            showError = true;
            System.err.println("Error during User creation, Response code: "+ response.getStatus());
            return;
        }

        email = "";

        //force user to reload
        userController.setReloadUserFlag(true);
        userController.loadUsers();
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getYearValues() {
        return yearValues;
    }

    public void setYearValues(Map<String, Integer> yearValues) {
        this.yearValues = yearValues;
    }

    public Map<String, Integer> getDayValues() {
        return dayValues;
    }

    public void setDayValues(Map<String, Integer> dayValues) {
        this.dayValues = dayValues;
    }

    public boolean isShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError = showError;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
