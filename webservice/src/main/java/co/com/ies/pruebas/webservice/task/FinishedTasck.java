package co.com.ies.pruebas.webservice.task;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinishedTasck {

    final List<TastTest> lista = new ArrayList<>();

    public void add(TastTest tastTest){
        lista.add(tastTest);
    }

    public int size(){
        return lista.size();
    }

    public boolean contains(TastTest tastTest){
        //sout();
        return lista.contains(tastTest);
    }

    public boolean contains(Integer value) {
        return contains(new TastTest(value));
    }

    public void sout(){
        System.out.println("lista = " + lista);
    }
}
