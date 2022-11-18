package com.example.uitest;

import java.io.IOException;
import java.util.Properties;

public class TestProperties {
    private final Properties properties;

    TestProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("conf.properties"));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String readProperty(String keyName) {
        return properties.getProperty(keyName, "There is no key in the properties file");
    }
}
