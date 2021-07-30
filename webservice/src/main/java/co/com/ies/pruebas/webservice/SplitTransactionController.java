package co.com.ies.pruebas.webservice;

import co.com.ies.pruebas.webservice.task.redis.FinishedTasckRedis;
import co.com.ies.pruebas.webservice.task.redis.QeueuAsyncRedis;
import co.com.ies.pruebas.webservice.task.TaskTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Random;

@RestController
public class SplitTransactionController {

    private final QeueuAsyncRedis queueAsync;
    private final FinishedTasckRedis finishedTasck;

    public SplitTransactionController(QeueuAsyncRedis queueAsync, FinishedTasckRedis finishedTasck) {
        this.queueAsync = queueAsync;
        this.finishedTasck = finishedTasck;
    }

    @PostMapping("/resquest")
    public ResponseEntity<String> request(){

        Random rn = new SecureRandom();
        final int nexInt = rn.nextInt();
        System.out.println("agregando nexInt = " + nexInt);
        queueAsync.offerTasck(new TaskTest(nexInt));

        return ResponseEntity.ok(String.valueOf(nexInt));
    }

    @PostMapping("/result")
    public ResponseEntity<Boolean> result(@RequestBody TaskTest value){

        final boolean nextBoolean = finishedTasck.contains(value);

        return ResponseEntity.ok(nextBoolean);
    }
    @PostMapping("/process")
    public ResponseEntity<String> procesarQueue(){
        queueAsync.processQueue();
        return ResponseEntity.ok("Procesada la lista");
    }


}
