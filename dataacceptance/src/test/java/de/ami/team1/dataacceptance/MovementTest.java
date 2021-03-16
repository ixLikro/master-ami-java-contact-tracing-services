package de.ami.team1.dataacceptance;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Testing the REST API for accepting Rawmovementdata
 */
@QuarkusTest
public class MovementTest {

    @Test
    public void testRaw(){
        String json = "{\"userId\": 3000, \"latitude\":20,\"lonitude\":20,\"timestamp\": \"2016-03-15T13:54:30.444\" }";
        given().contentType(ContentType.JSON).body(json).post("/movement").then().statusCode(200);
    }

}
