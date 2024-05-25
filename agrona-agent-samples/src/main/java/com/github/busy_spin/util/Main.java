package com.github.busy_spin.util;

import org.agrona.concurrent.ShutdownSignalBarrier;
import org.agrona.concurrent.SigInt;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> System.out.printf("%s - Hello World !!!", Thread.currentThread().getName()));

        SigInt.register(() -> {
            barrier.signal();
            System.out.printf("Thread [%s] reporting - Kill Signal received\n", Thread.currentThread().getName());
        });
        barrier.await();

        try {
            System.out.println("Terminating the executor service.");
            List<Runnable> runnables = executorService.shutdownNow();
            System.out.printf("%d, number of runnable cancelled\n", runnables.size());
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Reattempting to shutdown Executor service.\n");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.printf("Exiting the program, executor service is terminated = %s\n", executorService.isTerminated());
    }
}
