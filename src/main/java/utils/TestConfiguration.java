package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class TestConfiguration {

    private static String env;
    private static String host;

    private static Properties readProperties(Path file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file.toFile())) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        }
    }

    public static void readRunProperties(Path localfile) throws IOException {
        if (System.getProperty("env") == null) {
            Properties properties = readProperties(localfile);
            env = properties.getProperty("env");
        } else {
            env = System.getProperty("env");
        }
    }

    public static void readConfig(Path path) throws IOException {
        Properties properties = readProperties(path);
        host = properties.getProperty("host");
    }

    public static String getHost() {
        return host;
    }

    public static String getEnv() {
        return env;
    }
}
