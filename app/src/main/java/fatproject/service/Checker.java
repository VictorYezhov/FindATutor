package fatproject.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import java.util.concurrent.TimeUnit;

import fatproject.entity.Appointment;

/**
 * Created by Victor on 25.05.2018.
 */
public class Checker implements Runnable {

    private static List<Appointment> appointments;
    private static int period;
    private static TimeUnit timeUnit;
    private static volatile Checker instance;
    private static DaysDifferenceCounter differenceCounter;

    private Checker() {
        period = 1;
        timeUnit = TimeUnit.DAYS;
        differenceCounter = new DaysDifferenceCounter();
    }

    public static Checker getInstance() {
        Checker localInstance = instance;
        if (localInstance == null) {
            synchronized (Checker.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Checker();
                }
            }
        }
        return localInstance;
    }

    public static void reset() {
        period = 1;
        timeUnit = TimeUnit.DAYS;
    }


    public List<Appointment> getAppointments() {
        return appointments;
    }

    public static void setAppointments(List<Appointment> appointments) {
        Checker.appointments = appointments;
    }

    public static int getPeriod() {
        return period;
    }

    public static void setPeriod(int period) {
        Checker.period = period;
    }

    public static TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public static void setTimeUnit(TimeUnit timeUnit) {
        Checker.timeUnit = timeUnit;
    }

    @Override
    public void run() {
        Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        System.out.println("CHECKING:  "+ currentTimeStamp);
            for (int i = 0; i< appointments.size(); i++) {
                TimeUnit newUnit = differenceCounter.countDifference(currentTimeStamp.getTime(), appointments.get(i).getTimeFor().getTime());
                if (newUnit == null){
                    System.out.println("NOTIFICATION");
                    appointments.remove(i);
                    reset();
                    AppointmentScheduler.run();
                }
                else if(newUnit != timeUnit){
                    timeUnit = newUnit;
                    AppointmentScheduler.speedUp();
                }
            }
    }

}

