package fatproject.Helpers;

import java.util.ArrayList;
import java.util.List;

public interface AppointmentObservable {
    List<AppointmentObserver> observers = new ArrayList<>();

    default void notifyObservers(){
        for (AppointmentObserver o:
             observers) {
            o.update();
        }
    }

    default void registerObserver(AppointmentObserver observer){observers.add(observer);}
}
