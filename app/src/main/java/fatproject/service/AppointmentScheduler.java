package fatproject.service;

import java.util.concurrent.*;

/**
 * Created by Victor on 25.05.2018.
 */
public class AppointmentScheduler {


    private static ScheduledExecutorService scheduler;
    private static Checker checker;
    private static AppointmentScheduler instance;
    private static ScheduledFuture<?> task;

    private final static int delay = 0;

    private AppointmentScheduler(){

    }

    public static AppointmentScheduler getInstance() {
        AppointmentScheduler localInstance = instance;
        if (localInstance == null) {
            synchronized (AppointmentScheduler.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AppointmentScheduler();
                }
            }
        }
        return localInstance;
    }


    public static  void init(){
        scheduler = Executors.newScheduledThreadPool(1);
        checker = Checker.getInstance();
        task = scheduler.scheduleAtFixedRate(checker, delay, Checker.getPeriod(), Checker.getTimeUnit());
    }

    public static void reInit(){
        if (task != null)
        {
            task.cancel(true);
        }
        Checker.reset();
        checker = Checker.getInstance();
        task = scheduler.scheduleAtFixedRate(checker, delay, Checker.getPeriod(), Checker.getTimeUnit());
    }

    public static void speedUp(){
        System.out.println("Speed up! "+ Checker.getTimeUnit().name());
        if (task != null)
        {
            task.cancel(true);
        }
        task = scheduler.scheduleAtFixedRate(checker, delay, Checker.getPeriod(), Checker.getTimeUnit());
    }

}
