package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorDtoRequest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class AuthorControllerTest {
    private final static String BASE_URL = "/news-management-api/v1/authors";

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
                .get(BASE_URL + "/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("name", equalTo("R. L. Stine"));
    }

    @Test
    public void addAuthorTest(){
        given()
                .contentType("application/json")
                .body(new AuthorDtoRequest(null, "Janka Kupala"))
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .body("name", equalTo("Janka Kupala"));
    }

    @Test
    public void patchAuthorTest(){
        given()
                .contentType("application/json")
                .body(new AuthorDtoRequest(null, "Ryhor Baradulin"))
                .when()
                .put(BASE_URL+"/8")
                .then()
                .statusCode(200)
                .body("name", equalTo("Ryhor Baradulin"));
    }

    @Test
    public void deleteAuthorTest(){

        int id = given()
                .contentType("application/json")
                .body(new AuthorDtoRequest(null, "Ryhor Baradulin"))
                .when().post(BASE_URL)
                .then()
                .extract().path("id");

        given().pathParam("id", id)
                .when().delete(BASE_URL + "/{id}").then()
                .statusCode(204);
    }
}