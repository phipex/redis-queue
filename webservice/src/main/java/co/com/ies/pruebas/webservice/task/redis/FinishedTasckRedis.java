package co.com.ies.pruebas.webservice.task.redis;

import co.com.ies.pruebas.webservice.task.TaskTest;
import org.redisson.api.RList;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Component
public class FinishedTasckRedis {

    private static final String KEY_LIST = "TaskTest_List";

    private final RedissonClient redissonClient;

    public FinishedTasckRedis(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void add(TaskTest tastTest){
        RSet<Integer> lista = redissonClient.getSet(KEY_LIST);
        try {
            final int value = tastTest.getValue();
            lista.add(value);
            System.out.println("FinishedTasckRedis.add agregando value = " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int size(){
        RSet<Integer> lista = redissonClient.getSet(KEY_LIST);
        return lista.size();
    }

    public boolean contains(TaskTest tastTest){
        //sout();
        RSet<Integer> lista = redissonClient.getSet(KEY_LIST);
        return lista.contains(tastTest.getValue());
    }

    public boolean contains(Integer value) {
        return contains(new TaskTest(value));
    }


}
