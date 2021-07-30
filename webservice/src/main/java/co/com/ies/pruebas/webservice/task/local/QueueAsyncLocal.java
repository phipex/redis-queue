package co.com.ies.pruebas.webservice.task.local;

import co.com.ies.pruebas.webservice.task.QueueAsyncAbstract;
import co.com.ies.pruebas.webservice.task.TaskTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class QueueAsyncLocal extends QueueAsyncAbstract<TaskTest> {

    private final Queue<TaskTest> queue = new LinkedList<>();

    private final ProcessorDelayedLocal processorDelayed;

    public QueueAsyncLocal(ProcessorDelayedLocal processorDelayed) {
        this.processorDelayed = processorDelayed;
    }

    @Async
    @Override
    protected void offer(TaskTest element) {
        queue.add(element);
    }

    @Async
    @Override
    protected void publishNewElementsAdded() {
        System.out.println("Nueva tarea se ha agregado");
        processQueue();
    }

    @Override
    protected Queue<TaskTest> getQueue() {
        return queue;
    }

    @Async
    @Override
    protected void processElement(TaskTest element) {

        processorDelayed.processElement(element);

    }
}
