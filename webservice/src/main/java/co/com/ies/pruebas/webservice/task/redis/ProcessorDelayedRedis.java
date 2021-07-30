package co.com.ies.pruebas.webservice.task.redis;

import co.com.ies.pruebas.webservice.task.TaskTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class ProcessorDelayedRedis {
    Random rn = new SecureRandom();
    private final FinishedTasckRedis finishedTasck;

    public ProcessorDelayedRedis(FinishedTasckRedis finishedTasck) {
        this.finishedTasck = finishedTasck;
    }

    @Async
    public void processElement(TaskTest element) {
        //final int timeSleep = rn.nextInt(2000);
        final int timeSleep = 2000;

        try {
            Thread.sleep(timeSleep);
            System.out.println("resolviendo element = " + element + " ,timeSleep = "+ timeSleep);
            finishedTasck.add(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
