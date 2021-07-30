package co.com.ies.pruebas.webservice.task.redis;

import co.com.ies.pruebas.webservice.task.QueueAsyncAbstract;
import co.com.ies.pruebas.webservice.task.TaskTest;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class QeueuAsyncRedis  extends QueueAsyncAbstract<TaskTest> {

    private static final String KEY_QEUEU = "TaskTest_Qeueu";

    private final RedissonClient redissonClient;
    private final ProcessorDelayedRedis processorDelayed;

    public QeueuAsyncRedis(RedissonClient redissonClient, ProcessorDelayedRedis processorDelayed) {
        this.redissonClient = redissonClient;
        this.processorDelayed = processorDelayed;
    }

    @Override
    protected void offer(TaskTest element) {
        RQueue<TaskTest> queue = redissonClient.getQueue(KEY_QEUEU);
        queue.add(element);
    }

    @Override
    protected void publishNewElementsAdded() {
        System.out.println("Nueva tarea se ha agregado");
        processQueue();
    }

    @Override
    protected Queue<TaskTest> getQueue() {
        final RQueue<TaskTest> queue = redissonClient.getQueue(KEY_QEUEU);
        //TODO mirar el tema de los listener
        return queue;
    }

    @Override
    protected void processElement(TaskTest element) {
        processorDelayed.processElement(element);
    }
}
