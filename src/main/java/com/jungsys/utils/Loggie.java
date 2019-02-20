package com.jungsys.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggie {
    private static final Logger log = LoggerFactory.getLogger("Blue-Green");

    public static void debug(String msg) {
        log.debug(msg);
    }

    public static void info(String msg) {
        log.info(msg);
    }

    public static void warn(String msg) {
        log.warn(msg);
    }

    public static void error(String msg) {
        log.error(msg);
    }

    public static void error(Exception e) {
        log.error(e.toString(), e);
    }
}
