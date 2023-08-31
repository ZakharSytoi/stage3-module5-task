package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NewsControllerTest {
    private final static String BASE_URL = "/news-management-api/v1/news";

    @Test
    public void getAllWithPaginationTest() {
        given()
                .get(BASE_URL + "?pageNo=0&pageSize=5")
                .then()
                .assertThat()
                .statusCode(200)
                .body("content", hasSize(greaterThan(1)))
                .body("last", equalTo(false))
                .body("first", equalTo(true));
    }


    @Test
    public void getByIdTest() {
        given()
                .get(BASE_URL + "/8")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(8))
                .body("content", equalTo("Cat jumped into the water"))
                .body("title", equalTo("cats"));
    }

    @Test
    public void addTagTest() {
        given()
                .contentType("application/json")
                .body(new NewsDtoRequest(null,
                        "cats",
                        "Cat jumped into the water",
                        14L,
                        List.of(1L, 4L, 5L)))
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .body("content", equalTo("Cat jumped into the water"))
                .body("title", equalTo("cats"));
    }

    @Test
    public void patchTagTest() {
        given()
                .contentType("application/json")
                .body(new NewsDtoRequest(null,
                        "cats",
                        "Cat jumped into the water",
                        14L,
                        List.of(1L, 4L, 5L))).when()
                .put(BASE_URL + "/8")
                .then()
                .statusCode(200)
                .body("content", equalTo("Cat jumped into the water"))
                .body("title", equalTo("cats"));
    }

    @Test
    public void deleteAuthorTest() {

        int id = given()
                .contentType("application/json")
                .body(new NewsDtoRequest(null,
                        "cats",
                        "Cat jumped into the water",
                        14L,
                        List.of(1L, 4L, 5L))).when().when().post(BASE_URL)
                .then()
                .extract().path("id");

        given().pathParam("id", id)
                .when().delete(BASE_URL + "/{id}").then()
                .statusCode(204);
    }
}
