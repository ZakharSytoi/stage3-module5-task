package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDtoRequest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CommentControllerTests {
    private final static String BASE_URL = "/news-management-api/v1/comments";

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
                .body("content", equalTo("Exploring the ocean's depths is like discovering another planet."));
    }

    @Test
    public void addTagTest(){
        given()
                .contentType("application/json")
                .body(new CommentDtoRequest(null, "Cat jumped into the water", 18L))
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .body("content", equalTo("Cat jumped into the water"));
    }

    @Test
    public void patchTagTest(){
        given()
                .contentType("application/json")
                .body(new CommentDtoRequest(null, "Cat jumped into the water", 18L))
                .when()
                .put(BASE_URL+"/8")
                .then()
                .statusCode(200)
                .body("content", equalTo("Cat jumped into the water"));
    }

    @Test
    public void deleteAuthorTest(){

        int id = given()
                .contentType("application/json")
                .body(new CommentDtoRequest(null, "Cat jumped into the water", 18L))
                .when().post(BASE_URL)
                .then()
                .extract().path("id");

        given().pathParam("id", id)
                .when().delete(BASE_URL + "/{id}").then()
                .statusCode(204);
    }
}
