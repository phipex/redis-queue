package co.com.ies.pruebas.webservice.task;

import java.util.List;
import java.util.Queue;

public abstract class QueueAsyncAbstract<TasckType> {

    protected abstract void offer(TasckType element);
    protected abstract void publishNewElementsAdded();
    protected abstract Queue<TasckType> getQueue();
    protected abstract void processElement(TasckType element);

    public void offerTascks(List<TasckType> elements){
        //agregar a la cola
        elements.forEach(this::offer);
        //publicar nuevos elementos
        publishNewElementsAdded();
    }

    public void offerTasck(TasckType element){
        //agregar a la cola
        offer(element);
        //publicar nuevos elementos
        publishNewElementsAdded();
    }

    public void processQueue(){
        System.out.println("QueueAsyncAbstract.processQueue");
        Queue<TasckType> elements = getQueue();
        System.out.println("elements = " + elements.size());
        for (int i = 0; i < elements.size(); i++) {
            final TasckType tasckType = elements.poll();
            try {
                processElement(tasckType);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al procesar la tarea, se agrega de nuevo en la cola");
                offer(tasckType);
            }
        }
    }




}
