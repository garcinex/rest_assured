package utils;

import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

public class TestInitialization {

    private static boolean isInit;
    private static final Logger LOG = LogManager.getLogger(TestInitialization.class);

    static {
        isInit = false;
    }

    public static synchronized void init() {
        if (!isInit) {
            try {
                LOG.info("Test execution initialization");
                String userDirProperty = System.getProperty("user.dir");

                System.setProperty("isLocalRun", Boolean.toString(userDirProperty.contains("C:\\Users") && !userDirProperty.contains("gac8wz")));

                if (System.getProperty("isLocalRun").equals("false")) {
                    RestAssured.proxy("localhost", 3128);
                    RestAssured.useRelaxedHTTPSValidation();
                }

                TestConfiguration.readRunProperties(Paths.get(userDirProperty, "run.properties"));
                String env = TestConfiguration.getEnv();
                TestConfiguration.readConfig(Paths.get(userDirProperty, "env_config", env + ".properties"));

                RestAssured.filters((request, response, filterContext) -> {
                    String requestMsg = "--> Request " + request.getMethod() + " " + request.getURI();

                    Response next = filterContext.next(request, response);
                    String responseBody = ((RestAssuredResponseImpl) next).getBody().asString();
                    String responseMsg = "<-- Response " + next.getStatusCode() + " " + responseBody;

                    LOG.info(requestMsg);
                    LOG.info(responseMsg);

                    return next;
                });

                isInit = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
