package com.logging.log4j;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * Log4j2
 *
 * Levels:
 *   - ALL
 *   - TRACE
 *   - DEBUG
 *   - INFO
 *   - WARN
 *   - ERROR
 *   - FATAL
 *   - OFF
 *
 * Config:
 *   - File name format: log4j2.[extension]
 *   - Supported formats: XML, JSON, YAML, Properties
 */
public class Log4jLogging {

    // Automatically uses fully qualified class name
    private static final Logger logger;
    private static final String configPath = "/log4j/log4j2.xml";

    static {
        try {
            ((LoggerContext)LogManager.getContext(false))
                    .setConfigLocation(Log4jLogging.class.getResource(configPath).toURI());
            logger = LogManager.getLogger(Log4jLogging.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        logger.trace("Entering application.");
        logger.debug("This is a {} using {}", "test", "interpolation");

        Bar bar = new Bar();
        if (!bar.doIt()) {
            logger.error("Didn't do it!");
        }
        logger.trace("Exiting application.");
    }
}
