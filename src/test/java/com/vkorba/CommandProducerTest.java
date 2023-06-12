package com.vkorba;

import com.vkorba.commands.AddUserCommand;
import com.vkorba.commands.Command;
import com.vkorba.commands.DeleteAllUsersCommand;
import com.vkorba.commands.PrintAllUsersCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CommandProducerTest {

    @Test
    public void testCommandProducer() throws InterruptedException, ExecutionException {
        // Given
        BlockingQueue<Command> commandQueue = mock(BlockingQueue.class);
        CommandProducer producer = new CommandProducer(commandQueue);
        var executorProducer = Executors.newSingleThreadExecutor();
        var futureProducer = executorProducer.submit(producer);

        when(commandQueue.offer(Mockito.any(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(true);

        // When
        futureProducer.get();
        executorProducer.shutdownNow();

        // Then
        ArgumentCaptor<Command> captor = ArgumentCaptor.forClass(Command.class);
        verify(commandQueue, times(6)).offer(captor.capture(), anyLong(), any());

        assertTrue(captor.getAllValues().get(0) instanceof AddUserCommand);
        assertTrue(captor.getAllValues().get(1) instanceof AddUserCommand);
        assertTrue(captor.getAllValues().get(2) instanceof AddUserCommand);
        assertTrue(captor.getAllValues().get(3) instanceof AddUserCommand);
        assertTrue(captor.getAllValues().get(4) instanceof PrintAllUsersCommand);
        assertTrue(captor.getAllValues().get(5) instanceof DeleteAllUsersCommand);
    }
}