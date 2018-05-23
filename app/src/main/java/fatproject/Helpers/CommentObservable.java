package fatproject.Helpers;

import java.util.ArrayList;
import java.util.List;

public interface CommentObservable {

    List<CommentObserver> observers = new ArrayList<>();

    default void notifyObservers(String name, String familyName, Long id, Long question_id){
        for(CommentObserver o : observers){
            o.update(name, familyName, id, question_id);
        }
    }



    default void registerObserver(CommentObserver observer){
        observers.add(observer);
    }
}
