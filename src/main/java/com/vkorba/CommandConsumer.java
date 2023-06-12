package com.vkorba;

import com.vkorba.commands.Command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

class CommandConsumer implements Callable<Void> {
    private final BlockingQueue<Command> commandQueue;

    public CommandConsumer(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public Void call() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                var command = commandQueue.poll(2, TimeUnit.SECONDS);
                if (command == null){
                    break;
                }
                command.execute();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return null;
    }
}


