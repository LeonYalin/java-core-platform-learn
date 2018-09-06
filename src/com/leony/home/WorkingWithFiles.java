package com.leony.home;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
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

    public void createZipFile(String zipName) {
        try (FileSystem zipFs = this.openZip(Paths.get(zipName))) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private FileSystem openZip(Path zipPath) throws IOException, URISyntaxException {
        Map<String, String> props = new HashMap<>();
        props.put("create", "true");

        FileSystem zipFs = FileSystems.newFileSystem(
            new URI("jar:file", zipPath.toUri().getPath(), null),
            Map.of("create", "true")
        );

        return zipFs;
    }
}
