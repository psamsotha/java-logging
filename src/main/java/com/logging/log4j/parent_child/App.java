package com.logging.log4j.parent_child;

import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * This example points out that the NAME of the logger will always originate from the class in which it
 * is created and the CLASS name in each log event will always reflect the Class from which the logging
 * method was called.
 */
public class App {
    static {
        try {
            ((LoggerContext) LogManager.getContext(false))
                    .setConfigLocation(App.class.getResource("/log4j/parent_child/log4j2.xml").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        Marker marker = MarkerManager.getMarker("CLASS");
        Child child = new Child();

        System.out.println("------ Parent Logger ------");
        child.log(null);
        child.log(marker);
        child.logFromChild(null);
        child.logFromChild(marker);
        child.parentLog(null);
        child.parentLog(marker);

        child.setLogger(child.childLogger);

        System.out.println("------- Parent Logger set to Child Logger ----------");
        child.log(null);
        child.log(marker);
        child.logFromChild(null);
        child.logFromChild(marker);
    }
}
