package co.com.ies.pruebas.webservice.task.local;

import co.com.ies.pruebas.webservice.task.TaskTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class ProcessorDelayedLocal {
    Random rn = new SecureRandom();
    private final FinishedTasckLocal finishedTasck;

    public ProcessorDelayedLocal(FinishedTasckLocal finishedTasck) {
        this.finishedTasck = finishedTasck;
    }

    @Async
    public void processElement(TaskTest element) {
        final int timeSleep = rn.nextInt(2000);

        try {
            Thread.sleep(timeSleep);
            System.out.println("element = " + element + " ,timeSleep = "+ timeSleep);
            finishedTasck.add(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
