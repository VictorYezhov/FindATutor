package fatproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.util.concurrent.TimeUnit;

import fatproject.Helpers.Listener;
import fatproject.activities.MainAplication;
import fatproject.entity.Appointment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        appointments = new ArrayList<>();
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
        Checker.appointments.clear();
        for(Appointment a : appointments){
            if(!a.isStarted()){
                Checker.appointments.add(a);
            }
        }
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
        System.out.println("CHECKING:\n Current date: "+ currentTimeStamp+"\nCurrent timeunit "+timeUnit);
        List<Appointment> to_remove = new ArrayList<>();
        TimeUnit newTimeUnit = timeUnit;

        for(Appointment appointment : appointments){
            if(appointment.getTimeFor() != null &&  !appointment.isStarted()){
                newTimeUnit = differenceCounter.countDifference(currentTimeStamp.getTime(), appointment.getTimeFor().getTime());

                if(newTimeUnit == null && !appointment.isStarted() && appointment.isAcceeptedByEmployer() && appointment.isAcceptedByEmployee()){
                    appointment.setStarted(true);
                    MainAplication.getServerRequests().startAppointment(appointment.getId()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            System.out.println("Starting appointment");
                            AppointmentScheduler.reInit();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    to_remove.add(appointment);
                }

            }
        }
        if(newTimeUnit != timeUnit && timeUnit != null) {
            timeUnit = newTimeUnit;
            appointments.removeAll(to_remove);
            AppointmentScheduler.speedUp();
        }


        }
    }


