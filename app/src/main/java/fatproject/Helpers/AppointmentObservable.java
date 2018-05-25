package fatproject.Helpers;

import java.util.ArrayList;
import java.util.List;

public interface AppointmentObservable {
    List<AppointmentObserver> observers = new ArrayList<>();

    default void notifyObservers(Long id){
        for (AppointmentObserver o:
             observers) {
            o.update(id);
        }
    }

    default void registerObserver(AppointmentObserver observer){observers.add(observer);}
}
