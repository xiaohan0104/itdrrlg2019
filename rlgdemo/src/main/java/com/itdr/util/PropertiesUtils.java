package com.itdr.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    public static String getValue(String key){
        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("sets.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String property = properties.getProperty(key);
        return property;
    }
}
