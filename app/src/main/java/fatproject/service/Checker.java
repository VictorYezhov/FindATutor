package fatproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        AppointmentScheduler.reInit();
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
        if(appointments != null) {
            TimeUnit newUnit;
            for (int i = 0; i < appointments.size(); i++) {
                if(appointments.get(i).getTimeFor() != null) {
                    newUnit = differenceCounter.countDifference(currentTimeStamp.getTime(), appointments.get(i).getTimeFor().getTime());
                }else {
                    break;
                }
                if (newUnit == null) {
                    System.out.println("NOTIFICATION");
                    appointments.remove(i);
                    reset();
                    AppointmentScheduler.reInit();
                } else if (newUnit != timeUnit) {
                    timeUnit = newUnit;
                    AppointmentScheduler.speedUp();
                }
            }
        }
    }

}

