package com.vkorba;

import com.vkorba.commands.AddUserCommand;
import com.vkorba.commands.Command;
import com.vkorba.commands.DeleteAllUsersCommand;
import com.vkorba.commands.PrintAllUsersCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class CommandProducer implements Runnable {
    private final BlockingQueue<Command> commandQueue;
    private static final int RETRY_LIMIT = 3;
    private static final int EXECUTION_INDEX = 0;
    private static final int MILLIS_EXECUTION_TIMEOUT = 1000;

    public CommandProducer(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
//        do {
            System.out.println("Producing");
            produceCommands();
//        } while (!Thread.currentThread().isInterrupted());
    }

    private void produceCommands() {
        try {
            addToQueue(new AddUserCommand("John"), EXECUTION_INDEX);
            addToQueue(new AddUserCommand("Michael"), EXECUTION_INDEX);
            addToQueue(new AddUserCommand("Joe"), EXECUTION_INDEX);
            addToQueue(new AddUserCommand("Elizabeth"), EXECUTION_INDEX);
            addToQueue(new PrintAllUsersCommand(), EXECUTION_INDEX);
            addToQueue(new DeleteAllUsersCommand(), EXECUTION_INDEX);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void addToQueue(Command command, int executions) throws InterruptedException {
        if (!commandQueue.offer(command, MILLIS_EXECUTION_TIMEOUT, TimeUnit.MILLISECONDS)) {
            var failedCommand = commandQueue.poll();
            if (executions < RETRY_LIMIT) {
                addToQueue(failedCommand, executions + 1);
            } else {
                throw new RuntimeException(String.format("Retry limit reached for command: %s", failedCommand));
            }
        }
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(MILLIS_EXECUTION_TIMEOUT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}