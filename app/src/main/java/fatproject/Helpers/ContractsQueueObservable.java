package fatproject.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 17.05.2018.
 */

public interface ContractsQueueObservable {

    List<ContractsQueueObserver> observers = new ArrayList<>();

    default void notiifyObservers(){
        for (ContractsQueueObserver o: observers
             ) {
            o.updateContracts();
        }
    }

    default void changed(){
        for(ContractsQueueObserver o : observers){
            o.dataChanged();
        }
    }
    default void addObserver(ContractsQueueObserver observer){
        observers.add(observer);
    }

    default void removeObserver(ContractsQueueObserver observer){
        observers.remove(observer);
    }

}
