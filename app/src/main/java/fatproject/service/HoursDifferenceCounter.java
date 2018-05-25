package fatproject.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 25.05.2018.
 */
public class HoursDifferenceCounter implements DifferenceCounter {

    private DifferenceCounter nextStage;


    public HoursDifferenceCounter() {
        nextStage = new MinutesDifferenceCounter();
    }

    @Override
    public TimeUnit countDifference(long current, long timestampOfAppointment) {
        long diffHours = (timestampOfAppointment - current)/ (60 * 60 * 1000);
        if(diffHours <= 0){
            return nextStage.countDifference(current, timestampOfAppointment);
        }
        return TimeUnit.HOURS;
    }
}
