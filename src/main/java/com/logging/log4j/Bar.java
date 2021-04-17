package com.logging.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bar {

    private static final Logger logger = LogManager.getLogger(Bar.class.getName());

    public boolean doIt() {
        logger.traceEntry();
        logger.error("Dit it again!");
        return logger.traceExit(false);
    }
}
