package co.com.ies.pruebas.webservice.task.redis;

import co.com.ies.pruebas.webservice.task.QueueAsyncAbstract;
import co.com.ies.pruebas.webservice.task.TaskTest;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

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
        // TODO llamar el servicio remoto para que procese la lista
        // TODO crear la tarea runable para que pueda ejecutar
        ExecutorOptions executorOptions = ExecutorOptions.defaults().taskRetryInterval(15, TimeUnit.SECONDS);
        final RScheduledExecutorService executorService = redissonClient.getExecutorService("myExecutor", executorOptions);
        WorkerOptions workerOptions = WorkerOptions.defaults().taskTimeout(2, TimeUnit.SECONDS);
        //workerOptions.beanFactory(beanFactory);
        executorService.registerWorkers(workerOptions);

        Runnable task2 = (Runnable & Serializable)() -> System.out.println("Running task2...");
        executorService.schedule(task2, 1, TimeUnit.SECONDS);
        executorService.schedule(new RemoteServiceProcessor(),1000,TimeUnit.MILLISECONDS);
        //myExecutor.shutdown();
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
