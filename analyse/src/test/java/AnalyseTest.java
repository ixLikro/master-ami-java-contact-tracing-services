import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;

@QuarkusTest
public class AnalyseTest {
    String PATTERN = "[0-9]*";

    @Test
    void getHeatMapData() {
        given().when().queryParam("p1Lat", 0.0).queryParam("p1Lon", 0.0).queryParam("p1Lat", 0.0).queryParam("p1Lon", 0.0).get("/analyse/getHeatmapData").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void getMovementFromUser() {
        given().when().queryParam("userId", 3000).get("/analyse/getMovementFromUser").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void countUserWithFilter() {
        given().when().queryParam("filter", "com").get("/analyse/countUserWithFilter").then().statusCode(200).body(matchesPattern(PATTERN));
    }

    @Test
    void listUserIds() {
        given().when().get("/analyse/listUserIds").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void getLatestCoordinatesByInfectedUsers() {
        given().when().get("/analyse/getLatestCoordinatesByInfectedUsers").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void getFirstCoordinatesOfInfectedUsers() {
        given().when().get("/analyse/getFirstCoordinatesOfInfectedUsers").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void getCoordinatesOfInfectedUsersByMonth() {
        given().when().queryParam("minusMonth", 2).get("/analyse/getCoordinatesOfInfectedUsersByMonth").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void getTrendRelevantMovements() {
        given().when().get("/analyse/getTrendRelevantMovements").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }

    @Test
    void readLatestMovementByUserId() {
        given().when().queryParam("userId", 3000).get("/analyse/readLatestMovementByUserID").then().statusCode(200).body("id.toString()", matchesPattern(PATTERN));
    }

    @Test
    void readAmountOfEntries() {
        given().when().queryParam("userId", 3000).get("/analyse/readAmountOfEntries").then().statusCode(200).body(matchesPattern(PATTERN));
    }

    @Test
    void getListOfUserMovemntsSinceDate() {
        given().when().queryParam("userId", 3000).queryParam("minusDays", "2020-12-31").get("/analyse/getListOfUserMovemntsSinceDate").then().statusCode(200).body("size().toString()", matchesPattern(PATTERN));
    }


}
