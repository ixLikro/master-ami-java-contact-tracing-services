package de.ami.team1.preprocessing;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;

/**
 * Test the REST API for the Movementcontroller. Requires the default database provided in the script folder.
 */
@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovementTest {

    @Test
    public void post() {
        String json = "[{\"userId\": 3000, \"latitude\":20,\"lonitude\":20,\"timestamp\": \"2016-03-15T13:54:30.444\" }]";
        given().contentType(ContentType.JSON).body(json).post("/movement/etl").then().statusCode(200);
    }

}
