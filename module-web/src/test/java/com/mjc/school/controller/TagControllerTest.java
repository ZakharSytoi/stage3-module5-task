package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TagControllerTest {
    private final static String BASE_URL = "/news-management-api/v1/tags";

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
                .body("name", equalTo("    Ethical Dilemmas"));
    }

    @Test
    public void addTagTest(){
        given()
                .contentType("application/json")
                .body(new TagDtoRequest(null, "Global Crises"))
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201)
                .body("name", equalTo("Global Crises"));
    }

    @Test
    public void patchTagTest(){
        given()
                .contentType("application/json")
                .body(new TagDtoRequest(null, "Global Crises"))
                .when()
                .put(BASE_URL+"/8")
                .then()
                .statusCode(200)
                .body("name", equalTo("Global Crises"));
    }

    @Test
    public void deleteTagTest(){

        int id = given()
                .contentType("application/json")
                .body(new AuthorDtoRequest(null, "Global Crises"))
                .when().post(BASE_URL)
                .then()
                .extract().path("id");

        given().pathParam("id", id)
                .when().delete(BASE_URL + "/{id}").then()
                .statusCode(204);
    }
}