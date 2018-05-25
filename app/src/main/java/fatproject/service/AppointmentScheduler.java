package fatproject.service;

import java.util.concurrent.*;

/**
 * Created by Victor on 25.05.2018.
 */
public class AppointmentScheduler {


    private static ScheduledExecutorService scheduler;
    private static Checker checker;
    private static AppointmentScheduler instance;

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
        if(scheduler != null)
            scheduler.shutdown();
        scheduler = Executors.newScheduledThreadPool(1);
        checker = Checker.getInstance();
        scheduler.scheduleAtFixedRate(checker, delay, Checker.getPeriod(), Checker.getTimeUnit());
    }

    public static void run(){
        if(scheduler != null)
            scheduler.shutdown();
        scheduler = Executors.newScheduledThreadPool(1);
        checker = Checker.getInstance();
        scheduler.scheduleAtFixedRate(checker, delay, Checker.getPeriod(), Checker.getTimeUnit());
    }

    public static void speedUp(){
        System.out.println("Speed up! "+ Checker.getTimeUnit().name());
        scheduler.shutdown();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(checker, delay, Checker.getPeriod(), Checker.getTimeUnit());
    }










}
