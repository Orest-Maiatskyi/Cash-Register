package com.epam.cashregister.services.utils;

import com.epam.cashregister.contollers.servlets.viewservlets.LoginServlet;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;

public class ResourcesUtil {

    final static Logger logger = Logger.getLogger(ResourcesUtil.class);

    // get file from classpath, resources folder
    public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = ResourcesUtil.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            logger.error("file " + fileName + " is not found in the resources folder !");
            throw new IllegalArgumentException("file " + fileName + " is not found in the resources folder !");
        }
        else return new File(resource.getFile());
    }

}
