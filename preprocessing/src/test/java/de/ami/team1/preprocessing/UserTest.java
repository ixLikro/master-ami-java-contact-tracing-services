package de.ami.team1.preprocessing;

import de.ami.team1.preprocessing.pojo.UserPojo;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

import static io.restassured.RestAssured.given;

/**
 * Test the REST API for the User. Requires the default database provided in the script folder.
 */
@QuarkusTest
public class UserTest {
    String PATTERN = "[0-9]*";

    @Test
    public void createUser(){
        String json = "{\"mail\": \"test@test.de\", \"dateOfBirth\": \"1990-12-12\", \"gender\":\"m\"}";
        given().contentType(ContentType.JSON).body(json).post("/user").then().statusCode(200).body("id",matchesPattern(PATTERN));
    }

}
