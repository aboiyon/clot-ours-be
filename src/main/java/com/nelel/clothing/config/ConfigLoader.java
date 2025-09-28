package com.nelel.clothing.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String get(String key) {
        // 1. Check environment variable
        String value = System.getenv(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }

        // 2. Fallback to application.properties
        return properties.getProperty(key);
    }
}
