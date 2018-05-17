package fatproject.Helpers;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    List<Observer> observers = new ArrayList<>();

    default void notifyObservers(){
        for(Observer o : observers){
            o.update();
        }
    }

    default void registerObserver(Observer observer){
        observers.add(observer);
    }
}
