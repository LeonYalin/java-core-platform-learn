package com.leony.app;

import com.leony.home.WorkingWithFiles;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        workingWithFiles();
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
        workingWithFiles.createZipFile("gradesZip");
    }

    private static void printMessage(String msg) {
        System.out.println("\n========= " + msg + " ===========\n");
    }
}
