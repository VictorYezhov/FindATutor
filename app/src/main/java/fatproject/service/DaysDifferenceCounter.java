package fatproject.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 25.05.2018.
 */
public class DaysDifferenceCounter implements DifferenceCounter{


    private DifferenceCounter nextStage;


    public DaysDifferenceCounter() {
        this.nextStage =  new HoursDifferenceCounter();
    }

    @Override
    public TimeUnit countDifference(long current, long timestampOfAppointment) {

        long diffDays = (timestampOfAppointment - current)/ (24 * 60 * 60 * 1000);
        if(diffDays <= 0){
            return nextStage.countDifference(current, timestampOfAppointment);
        }
        return TimeUnit.DAYS;
    }
}
