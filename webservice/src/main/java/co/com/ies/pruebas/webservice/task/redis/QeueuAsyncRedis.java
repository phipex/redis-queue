package co.com.ies.pruebas.webservice.task.redis;

import co.com.ies.pruebas.webservice.task.QueueAsyncAbstract;
import co.com.ies.pruebas.webservice.task.TaskTest;
import org.redisson.api.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Component
public class QeueuAsyncRedis  extends QueueAsyncAbstract<TaskTest> {

    private static final String KEY_QEUEU = "TaskTest_Qeueu";

    @Autowired
    @Qualifier("remoteProcessQeueu")
    private ServiceProcessQeueu serviceProcessQeueu;

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
        final int value = element.getValue();
        final long timeInMillis = Calendar.getInstance().getTimeInMillis();

        System.out.println("QeueuAsyncRedis.offer "+timeInMillis+" value = " + value +" queue = " + queue);
        RQueue<TaskTest> queue2 = redissonClient.getQueue(KEY_QEUEU);
        System.out.println("QeueuAsyncRedis.offer  "+timeInMillis+" value = " + value +" queue2 = " + queue2);
    }

    @Async
    @Override
    protected void publishNewElementsAdded() {
        System.out.println("QeueuAsyncRedis.publishNewElementsAdded");
        publishEvents();
    }

    @Async
    void publishEvents() {
        System.out.println("Nueva tarea se ha agregado");
        // TODO llamar el servicio remoto para que procese la lista
        // TODO crear la tarea runable para que pueda ejecutar
        serviceProcessQeueu.processQeueu();
    }

    @Override
    protected Queue<TaskTest> getQueue() {
        final RQueue<TaskTest> queue = redissonClient.getQueue(KEY_QEUEU);
        //TODO mirar el tema de los listener
        return queue;
    }

    @Override
    protected void processElement(TaskTest element) {
        System.out.println("QeueuAsyncRedis.processElement "+ element.getValue());
        final RQueue<TaskTest> queue = redissonClient.getQueue(KEY_QEUEU + "_1");
        queue.add(element);
        processorDelayed.processElement(element);

    }
}
