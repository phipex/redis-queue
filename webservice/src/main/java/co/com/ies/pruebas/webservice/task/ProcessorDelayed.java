package co.com.ies.pruebas.webservice.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class ProcessorDelayed {
    Random rn = new SecureRandom();
    private final FinishedTasck finishedTasck;

    public ProcessorDelayed(FinishedTasck finishedTasck) {
        this.finishedTasck = finishedTasck;
    }

    @Async
    public void processElement(TastTest element) {
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
