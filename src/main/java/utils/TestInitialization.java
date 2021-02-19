package utils;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.nio.file.Paths;
//import org.apache.log4j.Logger;

public class TestInitialization {

    private static boolean isInit;
//    private static final org.apache.log4j.Logger LOG = Logger.getLogger(TestInitialization.class);

    static {
        isInit = false;
    }

    public static synchronized void init() {
        if (!isInit) {
            try {
//                LOG.info("Test execution initialization");
                String userDirProperty = System.getProperty("user.dir");

                System.setProperty("isLocalRun", Boolean.toString(userDirProperty.contains("C:\\Users") && !userDirProperty.contains("gac8wz")));

                if (System.getProperty("isLocalRun").equals("false")) {
                    RestAssured.proxy("localhost", 3128);
                    RestAssured.useRelaxedHTTPSValidation();
                }

                TestConfiguration.readRunProperties(Paths.get(userDirProperty, "run.properties"));
                String env = TestConfiguration.getEnv();
                TestConfiguration.readConfig(Paths.get(userDirProperty, "env_config", env + ".properties"));

                RestAssured.filters(
                        new RequestLoggingFilter(LogDetail.URI, true, System.out),
                        new ResponseLoggingFilter()
                );

                isInit = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
