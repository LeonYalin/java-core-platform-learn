package com.leony.home;

import com.oracle.tools.packager.Log;

import java.io.IOException;
import java.util.logging.*;

public class WorkingWithLogs {
    static Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void printSimpleLogs() {
        logger.setLevel(Level.ALL); // Will allow printing og log levels from SEVERE to Provided level.

        System.out.println("Print logs according to explicitly provided log level:");
        logger.log(Level.SEVERE, "Serious failure, value of 1000");
        logger.log(Level.WARNING, "Potential problem, value of 900");
        logger.log(Level.INFO, "General info, value of 800");
        logger.log(Level.CONFIG, "Configuration info, value of 700");
        logger.log(Level.FINE, "General developer info, value of 500");
        logger.log(Level.FINER, "Detailed developer info, value of 400");
        logger.log(Level.FINEST, "Specialized developer info, value of 300");
        logger.log(Level.ALL, "Everything, value of Integer.MIN_VALUE");
        logger.log(Level.OFF, "Nothing, value of Integer.MAX_VALUE");

        System.out.println("\nPrint logs using dedicated log level methods:");
        logger.severe("Serious failure, value of 1000");
        logger.warning("Potential problem, value of 900");
        logger.info("General info, value of 800");
        logger.config("Configuration info, value of 700");
        logger.fine("General developer info, value of 500");
        logger.finer("Detailed developer info, value of 400");
        logger.finest("Specialized developer info, value of 300");

        System.out.println("\nPrint logs using the precised logP method:");
        logger.setLevel(Level.ALL);
        try {
            logger.logp(
                    Level.INFO, WorkingWithLogs.class.getName(),
                    WorkingWithLogs.class.getMethod("printSimpleLogs").getName(),
                    "LogP precise log message!!");
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("\nPrint precise logP methods: entering and exiting");
            logger.entering(WorkingWithLogs.class.getName(), WorkingWithLogs.class.getMethod("printSimpleLogs").getName());
            logger.logp(
                    Level.WARNING, WorkingWithLogs.class.getName(),
                    WorkingWithLogs.class.getMethod("printSimpleLogs").getName(),
                    "LogP message between entering and exiting!!");
            logger.exiting(WorkingWithLogs.class.getName(), WorkingWithLogs.class.getMethod("printSimpleLogs").getName());
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createCustomLogger() {
        Logger myLogger = Logger.getLogger("com.leony.home"); // don't exist, we are creating it
        Handler handler = new ConsoleHandler();
        Formatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        myLogger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.info("A message from my custom logger!!");
    }

    public void logDataToFile() {
        try {
            Logger logger = Logger.getLogger("com.leony.home");
            FileHandler fileHandler = new FileHandler("./app_%g.log", 1024, 5);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            System.out.println("Logging data fo file");
            logger.info("Log data using fileHandler");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void useParentAndChildLoggers() {
        Logger parentLogger = Logger.getLogger("com.leony.parent");
        Logger childLogger = Logger.getLogger("com.leony.parent.child");

        System.out.printf("Log levels are parent:%s, child:%s\n", parentLogger.getLevel(), childLogger.getLevel());
        System.out.println("Log an info log from parent");
        parentLogger.info("log from parent");
        System.out.println("Log an info log from child. Should be logged twice: both from parent and child!!");
        childLogger.info("log from child");
        System.out.println("Log an entering from child - should not be displayed because of log level");
        childLogger.entering(WorkingWithLogs.class.getName(), "useParentAndChildLoggers");
        parentLogger.setLevel(Level.ALL);
        System.out.println("Child logger level after parent set to ALL");
        System.out.printf("Log levels are parent:%s, child:%s\n", parentLogger.getLevel(), childLogger.getLevel());
        System.out.println("Log an entering from child - SHOULD! be displayed because we've set log level to parent");
        childLogger.entering(WorkingWithLogs.class.getName(), "useParentAndChildLoggers");
    }
}
