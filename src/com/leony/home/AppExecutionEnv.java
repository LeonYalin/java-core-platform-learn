package com.leony.home;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppExecutionEnv {
    public void printCmdArguments(String[] args) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (String arg : args) {
            sj.add(arg);
        }
        System.out.println("Arguments from the command line are: " + sj.toString());
    }

    public void savePersistenceProperties(String filePath) {
        Properties props = new Properties();
        props.setProperty("name", "Leon Yalin");
        props.setProperty("status", "married");
        String comments = "Name and status of a person";

        if (this.isXmlFile(filePath)) {
            try (OutputStream outputStream = Files.newOutputStream(Paths.get(filePath))) {
                props.storeToXML(outputStream, comments);
                System.out.println("Saving properties to XML finished");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
                props.store(writer, comments);
                System.out.println("Saving properties finished");
            } catch (IOException e ){
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadPersistenceProperties(String filePath) {
        Properties props = new Properties();

        if (this.isXmlFile(filePath)) {
            try(InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
                props.loadFromXML(inputStream);
                System.out.printf("Loaded properties from XML 'name', value: '%s'\n", props.getProperty("name"));
                System.out.println("Print all properties from XML: " + props);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try(Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
                props.load(reader);
                System.out.printf("Loaded properties 'name', value: '%s'\n", props.getProperty("name"));
                System.out.println("Print all properties: " + props);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void useDefaultProperties() {
        Properties defaults = new Properties();
        defaults.setProperty("name", "Leon Yalin");
        defaults.setProperty("status", "married");

        Properties props = new Properties(defaults);
        System.out.println("Create properties map: " + props);
        System.out.printf(
                "Print non-existing 'name' and 'status' values with predefined defaults: name=%s, status=%s",
                props.getProperty("name"), props.getProperty("status"));

    }

    public void loadUserPropsAndMergeWithDefaultProps(String appPropsPath, String defaultsPropsPath) {

        // load default props
        Properties defaultProps = new Properties();
        try(InputStream inputStream = Files.newInputStream(Paths.get(defaultsPropsPath))) {
            defaultProps.load(inputStream);
            System.out.println("Load default properties: " + defaultProps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // load user props
        Properties appProps = new Properties(defaultProps);
        try(InputStream inputStream = AppExecutionEnv.class.getResourceAsStream("../resources/app.properties")) {
            appProps.load(inputStream);
            System.out.println("Load app properties: " + appProps);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // persist data between runs
        if (appProps.getProperty("isFirstRun") == null) {
            appProps.setProperty("isFirstRun", "true");
            System.out.println("This is the FIRST run!!");

            // save props
            try(OutputStream outputStream = Files.newOutputStream(Paths.get(appPropsPath))) {
                appProps.store(outputStream, null);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("This is NOT the first run!!");
        }
    }

    public void printSystemProperties() {
        System.out.println("Print system properties:");
        System.getProperties().forEach((k, v) -> System.out.printf("[%s %s]\n", k, v));

        System.out.println("\nPrint individual system properties:");
        System.out.println("user.name: " + System.getProperty("user.name"));
        System.out.println("user.home: " + System.getProperty("user.home"));
        System.out.println("os.arch: " + System.getProperty("os.arch"));

        System.out.println("\nPrint system env:");
        System.getenv().forEach((k, v) -> System.out.printf("[%s %s]\n", k, v));

        System.out.println("\nPrint individual system env props:");
        System.out.println("COMPUTERNAME: " + System.getenv("COMPUTERNAME"));
        System.out.println("SystemRoot: " + System.getenv("SystemRoot"));
        System.out.println("MyEnvVar, should be provided as a cmd argument: " + System.getenv("MyEnvVar"));
    }

    private boolean isXmlFile(String filePath) {
        Pattern pattern = Pattern.compile("(.*).xml$");
        return pattern.matcher(filePath).matches();
    }
}
