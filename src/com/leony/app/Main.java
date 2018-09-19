package com.leony.app;

import com.leony.home.AppExecutionEnv;
import com.leony.home.FormattingAndRegex;
import com.leony.home.WorkingWithCollections;
import com.leony.home.WorkingWithFiles;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        workingWithFiles();
        formattingAndRegex();
        workingWithCollections();
        appExecutionEnv(args);
    }

    private static void appExecutionEnv(String[] args) {
        AppExecutionEnv appExecutionEnv = new AppExecutionEnv();
        String appPropsPath = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\app.properties";
        String defaultsPropsPath = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\defaults.properties";
        String appPropsPathXml = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\app.xml";

        printMessage("AppExecutionEnv: print cmd arguments");
        appExecutionEnv.printCmdArguments(args);

        printMessage("AppExecutionEnv: save persistence properties");
        appExecutionEnv.savePersistenceProperties(appPropsPath);
        appExecutionEnv.savePersistenceProperties(appPropsPathXml);

        printMessage("AppExecutionEnv: load persistence properties");
        appExecutionEnv.loadPersistenceProperties(appPropsPath);
        appExecutionEnv.loadPersistenceProperties(appPropsPathXml);

        printMessage("AppExecutionEnv: use default properties");
        appExecutionEnv.useDefaultProperties();

        printMessage("AppExecutionEnv: load user properties and merge with default properties");
        appExecutionEnv.loadUserPropsAndMergeWithDefaultProps(appPropsPath, defaultsPropsPath);

        printMessage("AppExecutionEnv: print system properties");
        appExecutionEnv.printSystemProperties();
    }

    private static void workingWithCollections() {
        WorkingWithCollections workingWithCollections = new WorkingWithCollections();

        printMessage("WorkingWithCollections: create simple array list");
        workingWithCollections.createSimpleArrayList();

        printMessage("WorkingWithCollections: use common collections methods");
        workingWithCollections.useCommonCollectionsMethods();

        printMessage("WorkingWithCollections: use Java 8 collections methods");
        workingWithCollections.useJava8CollectionsMethods();

        printMessage("WorkingWithCollections: convert between collection and array");
        workingWithCollections.convertBetweenCollectionAndArray();

        printMessage("WorkingWithCollections: play with common collections");
        workingWithCollections.playWithCommonCollections();

        printMessage("WorkingWithCollections: play with map collections");
        workingWithCollections.playWithMapCollections();
    }

    private static void formattingAndRegex() {
        FormattingAndRegex formattingAndRegex = new FormattingAndRegex();
        String filePath = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\grades3.txt";

        printMessage("FormattingAndRegex: join string using StringJoiner");
        String[] words = new String[] {"first", "second", "third"};
        formattingAndRegex.joinStringsUsingStringJoiner(words);

        printMessage("FormattingAndRegex: format string using StringFormat");
        formattingAndRegex.formatStringUsingStringFormat("lalala", "lululu", "1.234567");
        formattingAndRegex.printClassObjectUsingFormattableInterface(formattingAndRegex);
        formattingAndRegex.writeFormattedContentFoFileUsingFormatter(filePath);

        printMessage("FormattingAndRegex: use regex methods from String class");
        formattingAndRegex.useRegexMethodsFromStringClass();

        printMessage("FormattingAndRegex: use pattern and matcher classes");
        formattingAndRegex.usePatternAndMatcherClasses();
    }

    public static void workingWithFiles() {
        WorkingWithFiles workingWithFiles = new WorkingWithFiles();

        printMessage("WorkingWithFiles: read file using input stream");
        String filePath = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\grades.txt";
        String filePath2 = filePath.replace(".txt", "2.txt");
        workingWithFiles.readFileViaInputStream(filePath);

        printMessage("WorkingWithFiles: read file using reader");
        workingWithFiles.readFileViaReader(filePath);

        printMessage("WorkingWithFiles: write to file using output stream");
        workingWithFiles.writeFileUsingOutputStream(filePath, new byte[] {10, 126, 101});
        workingWithFiles.readFileViaInputStream(filePath2);

        printMessage("WorkingWithFiles: write to file using writer");
        String fileContents = "lala\nlalala\nlalalala\nlalalalala";
        workingWithFiles.writeFileUsingWriter(filePath, fileContents);
        workingWithFiles.readFileViaReader(filePath2);

        printMessage("WorkingWithFiles: read file using Files util and readAllLines");
        workingWithFiles.readAllLinesUsingFilesUtil(filePath);

        printMessage("WorkingWithFiles: create zip file");
        workingWithFiles.createAndFillZipFile("grades.zip", filePath, filePath2);
    }

    private static void printMessage(String msg) {
        System.out.println("\n========= " + msg + " ===========\n");
    }
}
