package com.evilcorp.location;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class CoordinateIT extends BaseIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.basePath = "/coordinates";
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
}
