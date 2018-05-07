package fatproject.Helpers;

import com.google.android.gms.common.data.DataBufferObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Victor on 07.05.2018.
 */

public class MessageUpdateQueue implements DataBufferObserver.Observable {


    private static Set<String> queue;

    private static MessageUpdateQueue instance;
    private static List<DataBufferObserver> observers;

    private MessageUpdateQueue(){
        queue = new HashSet<>(10);
        observers = new ArrayList<>();

    }
    public static MessageUpdateQueue getInstance(){
        if(instance == null){
            instance = new MessageUpdateQueue();
            return  instance;
        }else {
            return instance;
        }
    }

    public void add(String s){
        queue.add(s);
        notifyObservers();
    }

    public  boolean contains(String id){
        boolean res = queue.contains(id);
        return  res;
    }
    public  int getSize(){
        return queue.size();
    }
    public void delete(String id){
        queue.remove(id);
    }

    @Override
    public void addObserver(DataBufferObserver dataBufferObserver) {
        observers.add(dataBufferObserver);
    }

    @Override
    public void removeObserver(DataBufferObserver dataBufferObserver) {
        observers.remove(dataBufferObserver);
    }

    public void notifyObservers(){
        for(DataBufferObserver ob:observers){
            ob.onDataChanged();
        }
    }
}