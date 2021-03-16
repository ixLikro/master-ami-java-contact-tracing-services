package de.ami.team1;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@QuarkusTest
public class NotifierTest {



    @Test
    public void post(){
        String json = "{\"email\":\"test@test.de\",\"timeOfContact\":\"2021-01-24T13:56:39.432\", \"contactPoint\":{\"x\":26.45,\"y\":25.65}}";
        given().contentType(ContentType.JSON).body(json).post("/notify").then().statusCode(200);
    }

}
