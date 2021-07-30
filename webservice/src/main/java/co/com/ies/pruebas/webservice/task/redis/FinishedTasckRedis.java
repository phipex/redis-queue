package co.com.ies.pruebas.webservice.task.redis;

import co.com.ies.pruebas.webservice.task.TaskTest;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class FinishedTasckRedis {

    private static final String KEY_LIST = "TaskTest_List";

    private final RedissonClient redissonClient;

    public FinishedTasckRedis(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void add(TaskTest tastTest){
        RList<TaskTest> lista = redissonClient.getList(KEY_LIST);
        lista.add(tastTest);
    }

    public int size(){
        RList<TaskTest> lista = redissonClient.getList(KEY_LIST);
        return lista.size();
    }

    public boolean contains(TaskTest tastTest){
        //sout();
        RList<TaskTest> lista = redissonClient.getList(KEY_LIST);
        return lista.contains(tastTest);
    }

    public boolean contains(Integer value) {
        return contains(new TaskTest(value));
    }


}
