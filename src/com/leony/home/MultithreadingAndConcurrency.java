package com.leony.home;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class MultithreadingAndConcurrency {
    private String filePath = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\grades.txt";

    public void createAndRunSimpleThreads() {
        List<Thread> threads = new ArrayList<>();
        threads.addAll(Arrays.asList(
                new Thread(new InnerRunnable(filePath, filePath.replace(".txt", "4.txt"))),
                new Thread(new InnerRunnable(filePath, filePath.replace(".txt", "5.txt"))),
                new Thread(new InnerRunnable(filePath, filePath.replace(".txt", "6.txt")))
        ));
        threads.forEach(thread -> {
            thread.start();
            try {
                thread.join(); // join with main thread so it will wait for all threads to finish before finishing itself.
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void createAndRunThreadsUsingThreadPool() {
        ExecutorService es = Executors.newFixedThreadPool(3); // 3 threads max at a time
        es.submit(new InnerRunnable(filePath, filePath.replace(".txt", "4.txt")));
        es.submit(new InnerRunnable(filePath, filePath.replace(".txt", "5.txt")));
        es.submit(new InnerRunnable(filePath, filePath.replace(".txt", "6.txt")));
        try {
            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getThreadResultsUsingCallable() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        List<Future<Integer>> results = new ArrayList<>();
        results.add(es.submit(new InnerCallable(filePath)));
        results.add(es.submit(new InnerCallable(filePath)));
        results.add(es.submit(new InnerCallable(filePath)));

        int total = 0;
        try {
            for (Future<Integer> result : results) {
                total += result.get();
            };
            System.out.println("Total got from 3 files is: " + total);
        } catch (ExecutionException e) { // thread exception
            Throwable originalException = e.getCause(); // InnerCallable exception
            System.out.println("Error during thread execution: " + originalException.getMessage());
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }
    }

    public void useSynchronizedBlocks() {
        ExecutorService es = Executors.newFixedThreadPool(1);
        InnerCallable callable = new InnerCallable(filePath);

        /**
         * synchronized block is sometimes better than synchronized method because it can hold many method calls in a block.
         */
        synchronized (callable) {
            Future<Integer> future = es.submit(callable);
            try {
                System.out.println("Use synchronized blocks; total is: " + future.get());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void moreSynchronizedTools() {
        List<String> synchronizedList = Collections.synchronizedList(Arrays.asList("first", "second", "third"));
        Map<String, String> synchronizedMap = Collections.synchronizedMap(Map.of("key", "val", "key2", "val2"));
        Queue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        Queue<String> priorityBlockingQueue = new PriorityBlockingQueue<>();
    }

    private synchronized int readTotalFromFile(String sourceFilePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(sourceFilePath));
        int total = 0;
        for (String line : lines) {
            total += Integer.valueOf(line);
        }
        return  total;
    }

    private synchronized void writeTotalToFile(String sourceFilePath, String destinationFilePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destinationFilePath))) {
            int total = readTotalFromFile(sourceFilePath);
            writer.write("Total: " + total);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Using Runnable interface, to pass directly to Thread class or thread pool.
     */
    private class InnerRunnable implements Runnable  {
        String sourceFilePath;
        String destinationFilePath;

        public InnerRunnable(String sourceFilePath, String destinationFilePath) {
            this.sourceFilePath = sourceFilePath;
            this.destinationFilePath = destinationFilePath;
        }

        @Override
        public void run() {
            System.out.printf("Create runnable to write files from %s to %s\n", this.sourceFilePath, this.destinationFilePath);
            writeTotalToFile(this.sourceFilePath, this.destinationFilePath);
        }
    }

    /**
     * Using Callable interface, to pass to thread pool and get result.
     */
    private class InnerCallable implements Callable<Integer> {
        String sourceFilePath;
        String destinationFilePath;

        public InnerCallable(String sourceFilePath) {
            this.sourceFilePath = sourceFilePath;
        }

        @Override
        public Integer call() throws Exception {
            System.out.printf("Create callable to get total from file %s\n", this.sourceFilePath);
            return readTotalFromFile(this.sourceFilePath);
        }
    }
}
