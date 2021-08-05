package co.com.ies.pruebas.webservice.task.redis;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ServiceProcessQeueuImpl  implements ServiceProcessQeueu{

    private final QeueuAsyncRedis qeueuAsyncRedis;

    public ServiceProcessQeueuImpl(QeueuAsyncRedis qeueuAsyncRedis) {
        this.qeueuAsyncRedis = qeueuAsyncRedis;
    }

    @Async
    @Override
    public void processQeueu() {
        System.out.println("ServiceProcessQeueuImpl.processQeueu");
        qeueuAsyncRedis.processQueue();
    }
}
