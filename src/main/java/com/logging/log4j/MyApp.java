package com.logging.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyApp {

    private static final Logger logger = LogManager.getLogger(MyApp.class);

    public static void main(String... args) throws Exception {
        logger.trace("Entering application.");

        Bar bar = new Bar();
        if (!bar.doIt()) {
            logger.error("Didn't do it!");
        }
        logger.trace("Exiting application.");
    }
}
