package com.practice.website.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.util.Properties;


public class FileIOUtil {

    private static Logger logger = LogManager.getLogger(FileIOUtil.class);

    public static Properties jdbcGetPropertise(String filePathDBPropertise) {
        Properties properties = null;
        try {
            logger.info("File Path {} ", filePathDBPropertise);
            properties = new Properties();
            properties.load(new FileReader(filePathDBPropertise));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

}
