package specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import tests.ApiTestBase;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class BaseSpec extends ApiTestBase {

    public static RequestSpecification RequestSpec = with()
            .filter(withCustomTemplates())
            .log().body()
            .log().headers()
            .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
            .contentType(JSON);

    public static ResponseSpecification responseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
             .expectStatusCode(expectedStatusCode)
             .log(STATUS)
             .log(BODY)
             .build();
    }

}
