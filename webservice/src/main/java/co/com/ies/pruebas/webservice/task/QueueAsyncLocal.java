package co.com.ies.pruebas.webservice.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class QueueAsyncLocal extends QueueAsyncAbstract<TastTest>{

    private final Queue<TastTest> queue = new LinkedList<>();

    private final ProcessorDelayed processorDelayed;

    public QueueAsyncLocal(ProcessorDelayed processorDelayed) {
        this.processorDelayed = processorDelayed;
    }

    @Async
    @Override
    protected void offer(TastTest element) {
        queue.add(element);
    }

    @Async
    @Override
    protected void publishNewElementsAdded() {
        System.out.println("Nueva tarea se ha agregado");
        onNewElementAdded();
    }

    @Override
    protected Queue<TastTest> getQueue() {
        return queue;
    }

    @Async
    @Override
    protected void processElement(TastTest element) {

        processorDelayed.processElement(element);

    }
}
