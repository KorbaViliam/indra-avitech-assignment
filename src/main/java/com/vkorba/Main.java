package com.vkorba;


import com.vkorba.commands.Command;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        var commandQueue = new LinkedBlockingQueue<Command>();
        var commandProducer = new CommandProducer(commandQueue);
        var commandConsumer = new CommandConsumer(commandQueue);

        // Start the producer thread
        var executorProducer = Executors.newSingleThreadExecutor();
        var futureProducer = executorProducer.submit(commandProducer);

        // Start the consumer thread
        var executorConsumer = Executors.newSingleThreadExecutor();
        var futureConsumer = executorConsumer.submit(commandConsumer);


        try {
            // Wait for the producer thread to complete
            futureProducer.get();

            // Stop the producer thread
            executorProducer.shutdownNow();
            System.out.println("Producer thread finished");

            // Wait for the consumer thread to complete
            futureConsumer.get();

            // Stop the consumer thread
            executorConsumer.shutdownNow();
            System.out.println("Consumer thread finished");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}