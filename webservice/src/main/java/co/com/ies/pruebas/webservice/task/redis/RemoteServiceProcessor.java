package co.com.ies.pruebas.webservice.task.redis;

import org.redisson.api.annotation.RInject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class RemoteServiceProcessor  implements Runnable, Serializable {

    @RInject
    private transient QeueuAsyncRedis qeueuAsyncRedis;

    @Override
    public void run() {
        System.out.println("RemoteServiceProcessor.run");
        qeueuAsyncRedis.processQueue();
    }
}
