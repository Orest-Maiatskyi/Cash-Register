package com.epam.cashregister.services.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class PropertiesManager {

    public static Properties getPropertyFile(String path) {
        try (InputStream input = Files.newInputStream(ResourcesUtil.getFileFromResources(path).toPath())) {
            BufferedReader in = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }
}
