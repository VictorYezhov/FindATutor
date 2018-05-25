package fatproject.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 25.05.2018.
 */
public class MinutesDifferenceCounter implements DifferenceCounter {

    private DifferenceCounter nextStage;

    public MinutesDifferenceCounter() {
        nextStage = new SecondsDifferenceCounter();
    }

    @Override
    public TimeUnit countDifference(long current, long timestampOfAppointment) {
        long diffMinutes = (timestampOfAppointment - current) / (60 * 1000);
        if(diffMinutes <= 0){
            return nextStage.countDifference(current, timestampOfAppointment);
        }
        return TimeUnit.MINUTES;
    }
}
