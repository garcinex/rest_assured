package utils;

import io.restassured.RestAssured;
import org.apache.log4j.Logger;

import java.io.IOException;

public class TestInitialization {

    private static boolean isInit;
    private static final org.apache.log4j.Logger LOG = Logger.getLogger(TestInitialization.class);

    static {
        isInit = false;
    }

    public static synchronized void init() {
        if (!isInit) {
            try {
                LOG.info("Test execution initialization");

                System.setProperty("isLocalRun", Boolean.toString(System.getProperty("user.dir").contains("C:\\Users")));

                if (System.getProperty("isLocalRun").equals("false")) {
                    RestAssured.proxy("localhost", 3128);
                    RestAssured.useRelaxedHTTPSValidation();
                }

                isInit = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
