package com.logging.log4j;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.apache.logging.log4j.test.appender.ListAppender;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.apache.logging.log4j.Level.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the functionality of a Log4j logger.
 */
public class Log4jTest {

    @Rule
    public LoggerContextRule init = new LoggerContextRule("log4j/log4j2.xml");
    @Rule
    public final SystemOutRule sysOut = new SystemOutRule().enableLog();

    private static final Level[] ALL_LOG_LEVELS = new Level[] {TRACE, DEBUG, INFO, WARN, ERROR, FATAL};

    // @Test  example from docs
    public void testSomeAwesomeFeature() {
        final LoggerContext ctx = init.getLoggerContext();
        final Logger logger = init.getLogger("org.apache.logging.log4j.my.awesome.test.logger");
        final Configuration cfg = init.getConfiguration();
        final ListAppender app = init.getListAppender("List");
        logger.warn("Test message");
        final List<LogEvent> events = app.getEvents();
    }

    @Test
    public void testBarLogsAllToConsole() {
        final Logger logger = init.getLogger(Bar.class);
        logMessages(logger);

        String log = sysOut.getLog();
        assertThat(log).has(onlyOneOfEachLevel());
    }

    private void logMessages(Logger logger, Level... levels) {
        if (levels.length == 0) {
            levels = ALL_LOG_LEVELS;
        }
        for (Level level: levels) {
            logger.log(level, "Message");
        }
    }

    private static Condition<String> onlyOneOfEachLevel() {
        return new Condition<>("All log levels only once") {
            @Override
            public boolean matches(String log) {
                for (Level level: ALL_LOG_LEVELS) {
                    final String levelStr = level.name();
                    final int firstIndex = log.indexOf(levelStr);
                    if (firstIndex == -1) {
                        return false;
                    }
                    if (firstIndex != log.lastIndexOf(levelStr)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    private static class LogStringAssert extends AbstractAssert<LogStringAssert, String> {

        public LogStringAssert(String log) {
            super(log, String.class);
        }

        public static LogStringAssert assertThat(String log) {
            return new LogStringAssert(log);
        }

        public LogStringAssert containsOnlyOneOfEachLevel() {
            isNotNull();
            final String log = actual;
            for (Level level: ALL_LOG_LEVELS) {
                final String levelStr = level.name();
                final int firstIndex = log.indexOf(levelStr);
                if (firstIndex == -1) {
                    failWithMessage("Log does not contain level " + levelStr);
                }
                if (firstIndex != actual.lastIndexOf(levelStr)) {
                    failWithMessage("Log contains level " + levelStr + " more than once:"
                                    + System.lineSeparator()
                                    + log);
                }
            }
            return this;
        }
    }
}
