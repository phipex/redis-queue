package co.com.ies.pruebas.webservice;

import co.com.ies.pruebas.webservice.task.FinishedTasck;
import co.com.ies.pruebas.webservice.task.QueueAsyncLocal;
import co.com.ies.pruebas.webservice.task.TastTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Random;

@RestController
public class SplitTransactionController {

    private final QueueAsyncLocal queueAsyncLocal;
    private final FinishedTasck finishedTasck;

    public SplitTransactionController(QueueAsyncLocal queueAsyncLocal, FinishedTasck finishedTasck) {
        this.queueAsyncLocal = queueAsyncLocal;
        this.finishedTasck = finishedTasck;
    }

    @PostMapping("/resquest")
    public ResponseEntity<String> request(){

        Random rn = new SecureRandom();
        final int nexInt = rn.nextInt();
        queueAsyncLocal.offerTasck(new TastTest(nexInt));

        return ResponseEntity.ok(String.valueOf(nexInt));
    }

    @PostMapping("/result")
    public ResponseEntity<Boolean> result(@RequestBody TastTest value){

        final boolean nextBoolean = finishedTasck.contains(value);

        return ResponseEntity.ok(nextBoolean);
    }

}
