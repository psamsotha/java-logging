package com.logging.log4j.mdc;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * See config file resources/log4j/log4j2-mdc.xml.
 * Logs should show the userId. This is an example of
 * Mapped Diagnostic Context (MDC) using Log4j's ThreadContext
 */
public class MdcExample {

    private static final Logger logger;

    static {
        try {
            ((LoggerContext) LogManager.getContext(false))
                    .setConfigLocation(MdcExample.class.getResource("/log4j/log4j2-mdc.xml").toURI());
            logger = LogManager.getLogger();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(new Request(logger));
        }
        shutdownAndAwaitTermination(pool);
    }

    private static class Request implements Runnable {

        private final Logger logger;

        private Request(Logger logger) {
            this.logger = logger;
        }

        @Override
        public void run() {
            ThreadContext.put("userId", UUID.randomUUID().toString());
            logger.info("Handling request.");
            ThreadContext.clearAll();
        }
    }

    /**
     * From ExecutorService Javadoc
     * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
     */
    private static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
