package us.ilite.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ChrisCSVWritter {
    private static final int NUM_THREADS = 10;
    private static int THREAD_COUNTER = 1;
    private static final ScheduledExecutorService ses = Executors.newScheduledThreadPool(NUM_THREADS+1, (runnable -> {
        return new Thread(runnable, "Worker threads-" + THREAD_COUNTER++);
    }));

    private static final ScheduledExecutorService mExService =
            Executors.newSingleThreadScheduledExecutor((run) -> new Thread(run, "My timer thread"));

    public static LinkedBlockingDeque<String> kCSVLoggerQueue = new LinkedBlockingDeque<>();
    private final Consumer<String> writer;
    List<Future<?>>allTasks = new ArrayList<>();

    public ChrisCSVWritter(Consumer<String> writer) throws IOException {
        this.writer = writer;
        IntStream.range(0, NUM_THREADS).forEach(anInt -> {
            ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(() -> {
                kCSVLoggerQueue.add("Message: " + anInt + " received");
            }, 0, 500, TimeUnit.MILLISECONDS);

            allTasks.add(scheduledFuture);
        });

        ScheduledFuture<?> scheduledFuture = mExService.scheduleAtFixedRate(() -> {
            List<String> snapShot = new ArrayList<>();
            kCSVLoggerQueue.drainTo(snapShot);
            snapShot.stream().forEach(writer::accept);

        }, 1, 1, TimeUnit.SECONDS);
        allTasks.add(scheduledFuture);
    }

    public void stopTest() {
        allTasks.stream().forEach(b ->b.cancel(true));
        allTasks.clear();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int testDurationInMins = 5;
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("output.txt"), true));
        ChrisCSVWritter aWriter = new ChrisCSVWritter((log) -> {
            try {
                bw.write(log);
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();

            }

        });

        ses.schedule(()->{
            System.out.println("Stopping the test");
            aWriter.stopTest();
        }, testDurationInMins, TimeUnit.MINUTES).get();

        System.out.println("Stop!");
        System.out.println("Say goodbye!");
        System.exit(0);
    }
}