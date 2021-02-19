import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.TestInitialization;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;

public class restAssured {

    @BeforeClass
    public static void init() {
//        proxy("localhost", 3128);
//        useRelaxedHTTPSValidation();
        TestInitialization.init();
    }

    String url = "http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";


//    @Test
//    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {
//        given().when().get("http://ergast.com/api/f1/2017/circuits.json").
//                then().assertThat().statusCode(200).
//                and().contentType(ContentType.JSON).
//                and().header("Content-Length", equalTo("4551"));
//                assertThat().body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
//    }

//    @Test
//    public void canGetLuke() {
//        RestAssured.get("https://swapi.dev/api/people/1/?format=json").
//                then().
//                assertThat().body("name", equalTo("Luke Skywalker")).and().
//                assertThat().statusCode(200);
//    }
//
//    @Test
//    public void canGetC3POandParseWithJsonPath() {
//
//        Response response = RestAssured.get("https://swapi.dev/api/people/2/?format=json").andReturn();
//
//        String json = response.getBody().asString();
//        System.out.println(json);
//
//        JsonPath jsonPath = new JsonPath(json);
//        Assert.assertEquals("C-3PO", jsonPath.getString("name"));
//    }
//
//    @Test
//    public void canGetC3POandParseWithRestAssure() {
//        RestAssured.get("https://swapi.dev/api/people/2/?format=json").
//                then().assertThat().body("name", equalTo("C-3PO"));
//    }

    @Test
    public void getResponseBody() {
//        RestAssured.given().
//                get("http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1").
//                then().log().all();
        given().queryParam("CUSTOMER_ID", "68195")
                .queryParam("PASSWORD", "1234!")
                .queryParam("Account_No","1")
                .when().get("http://demo.guru99.com/V4/sinkministatement.php")
                .then().log().all();
    }

    @Test
    public void getResponseStatus() {
        int statusCode = given().queryParam("CUSTOMER_ID", "68195")
                .queryParam("PASSWORD", "1234!")
                .queryParam("Account_No","1")
                .when().get("http://demo.guru99.com/V4/sinkministatement.php").statusCode();
        System.out.println("The response code is: " + statusCode);


        given().when().get(url).then().assertThat().statusCode(200);
    }

    @Test
    public void getResponseCodeMeWay() {
        given().queryParam("CUSTOMER_ID", "68195")
                .queryParam("PASSWORD", "1234!")
                .queryParam("Account_No","1")
                .when().get("http://demo.guru99.com/V4/sinkministatement.php")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void getResponseTime() {
        long time = get(url).timeIn(TimeUnit.MILLISECONDS);
        System.out.println("The time taken to fetch the response " + time + " ms" );
        Assert.assertTrue(time < 1000);
    }
}
