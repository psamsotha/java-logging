package com.logging.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger();

    public static void main(String... args) throws Exception {
        logger.trace("Entering application.");
        logger.debug("This is a {} using {}", "test", "interpolation");

        Bar bar = new Bar();
        if (!bar.doIt()) {
            logger.error("Didn't do it!");
        }
        logger.trace("Exiting application.");
    }
}
