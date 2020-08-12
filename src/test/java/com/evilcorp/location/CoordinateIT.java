package com.evilcorp.location;

import com.evilcorp.location.domain.model.Coordinate;
import com.evilcorp.location.domain.repository.CoordinateRepository;
import com.evilcorp.location.util.DatabaseCleaner;
import com.evilcorp.location.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CoordinateIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        RestAssured.basePath = "/coordinates";

        databaseCleaner.clearTables();
        addBaseData();
    }

    @Test
    public void shouldReturnStatus200_WhenGetCoordinates() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnCorrectSize_WhenGetCoordinates() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(2));
    }

    @Test
    public void shouldReturnStatus201_WhenCreateCoordinate() {
        String createCoordinateExample1 = ResourceUtils.getContentFromResource(
                "/json/coordinates/create-coordinate-example-1.json"
        );

        given()
                .body(createCoordinateExample1)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnStatus404_WhenGetByIdNotExists() {
        given()
                .pathParam("coordinateId", 5)
                .accept(ContentType.JSON)
                .when()
                .get("/{coordinateId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void addBaseData() {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(1.0);
        coordinate.setLongitude(2.0);
        coordinateRepository.save(coordinate);

        coordinate = new Coordinate();
        coordinate.setLatitude(23.1);
        coordinate.setLongitude(7.5);
        coordinateRepository.save(coordinate);

    }
}
