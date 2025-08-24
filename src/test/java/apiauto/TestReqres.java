package apiauto;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

public class TestReqres {

    @Test
    public void testGetListUser() {

        File jsonSchema = new File("src/test/resources/jsonSchema/getListUsersSchema.json");


        RestAssured.given()
                .baseUri("https://reqres.in")
                .header("x-api-key", "reqres-free-v1")   // <-- add this
                .when()
                .get("/api/users?page=1")
                .then()
                .log().all()
                .statusCode(200)
                .body("per_page", Matchers.equalTo(6))
                .body("page", Matchers.equalTo(1))
                .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }



    @Test
    public void testPostCreateUser() {

        String name = "Rayhan";
        String job = "QA";

        JSONObject bodyObj = new JSONObject();

        bodyObj.put("name", name);
        bodyObj.put("job", job);


        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(name));
    }

}
