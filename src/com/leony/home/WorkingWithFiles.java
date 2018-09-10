package com.leony.home;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WorkingWithFiles {

    public void readFileViaInputStream(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            int intVal;
            while ((intVal = inputStream.read()) > -1) {
                System.out.println((char)intVal);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readFileViaReader(String filePath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeFileUsingOutputStream(String filePath, byte[] bytesToWrite) {
        File newFile = new File(filePath.replace(".txt", "2.txt"));
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try (OutputStream outputStream = new FileOutputStream(newFile)) {
            outputStream.write(bytesToWrite);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeFileUsingWriter(String filePath, String dataToWrite) {
        File newFile = new File(filePath.replace(".txt", "2.txt"));
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try (Writer writer = new FileWriter(newFile)) {
            writer.write(dataToWrite);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readAllLinesUsingFilesUtil(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line: lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createAndFillZipFile(String zipName, String... paths) {
        try (FileSystem zipFs = this.createZip(Paths.get(zipName))) {
            for (String path: paths) {
                Files.copy(
                        Paths.get(path),
                        zipFs.getPath("/" + Paths.get(path).getFileName()),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            this.addFileToZipUsingBuggeredReader(zipFs);
            this.addFileToZipUsingFilesWrite(zipFs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addFileToZipUsingFilesWrite(FileSystem zipFs) throws IOException {
        String[] rows = new String[] {
                "a",
                "b b",
                "c c c",
                "d d d d",
                "e e e e e"
        };
        Files.write(zipFs.getPath("/newFile2.txt"), Arrays.asList(rows), Charset.defaultCharset(), StandardOpenOption.CREATE);
    }

    private void addFileToZipUsingBuggeredReader(FileSystem zipFs) throws IOException {
        String[] rows = new String[] {
                "1",
                "2 2",
                "3 3 3",
                "4 4 4 4",
                "5 5 5 5 5"
        };

        try(BufferedWriter writer = Files.newBufferedWriter(zipFs.getPath("/newFile1.txt"))) {
            for (String row: rows) {
                writer.write(row);
                writer.newLine();
            }
        }
    }

    private FileSystem createZip(Path zipPath) throws IOException, URISyntaxException {
        return FileSystems.newFileSystem(
            new URI("jar:file", zipPath.toUri().getPath(), null),
            Map.of("create", "true"));
    }
}
