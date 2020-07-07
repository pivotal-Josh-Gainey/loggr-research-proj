package com.jgainey.secretsauce.secretsauce.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    public static Gson gson = new Gson();
    public static Logger LOGGER;
    enum LOGTYPE{INFO,WARNING,ERROR,DEBUG}

    public static void initLogger(){
        LOGGER = LoggerFactory.getLogger(Utils.class);
        logInfo("SS Logger Ready");
    }

    public static String getAsJsonString(String fullPathStringName) {
        File file = new File(fullPathStringName);
        StringBuilder sb = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (scanner.hasNext()) {
                do {
                    sb.append(scanner.nextLine());
                } while (scanner.hasNextLine());

            } else {
                return sb.toString();
            }

        } catch (NullPointerException nully) {
            logError("error parsing file "+ fullPathStringName);
        }
        return sb.toString();
    }

    public static void logDebug(String message){
        LOGGER.debug(message);
    }

    public static void logInfo(String message){
        LOGGER.info(message);
    }

    public static void logWarning(String message){
        LOGGER.warn(message);
    }

    public static void logError(String message){
        LOGGER.error(message);
    }
}
