package fatproject.Helpers;

import com.google.android.gms.common.data.DataBufferObserver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Victor on 17.05.2018.
 */

public class ContractsQueue  implements ContractsQueueObservable{


    private static Set<String> queue = new HashSet<>();
    private static ContractsQueue instance;

    private ContractsQueue(){
    }

    public static ContractsQueue getInstance(){
        if(instance == null){
            instance = new ContractsQueue();
            return  instance;
        }else {
            return instance;
        }
    }

    public void add(String s){
        queue.add(s);
        notiifyObservers();
    }

    public  boolean contains(String id){
        boolean res = queue.contains(id);
        return  res;
    }
    public String pop(){
        Iterator<String> iter = queue.iterator();
        if(iter.hasNext()) {
            String s = iter.next();
            queue.remove(s);
            return  s;
        }else
            return null;
    }
    public  int getSize(){
        return queue.size();
    }
    public void delete(String id){
        queue.remove(id);
    }


}
