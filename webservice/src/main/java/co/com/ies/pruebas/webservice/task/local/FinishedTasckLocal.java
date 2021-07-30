package co.com.ies.pruebas.webservice.task.local;

import co.com.ies.pruebas.webservice.task.TaskTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinishedTasckLocal {

    final List<TaskTest> lista = new ArrayList<>();

    public void add(TaskTest tastTest){
        lista.add(tastTest);
    }

    public int size(){
        return lista.size();
    }

    public boolean contains(TaskTest tastTest){
        //sout();
        return lista.contains(tastTest);
    }

    public boolean contains(Integer value) {
        return contains(new TaskTest(value));
    }

    public void sout(){
        System.out.println("lista = " + lista);
    }
}
