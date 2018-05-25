package fatproject.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 25.05.2018.
 */
public class SecondsDifferenceCounter implements DifferenceCounter {


    private DifferenceCounter differenceCounter;

    public SecondsDifferenceCounter() {
        differenceCounter = null;
    }

    @Override
    public TimeUnit countDifference(long current, long timestampOfAppointment) {
        long diffSec = (timestampOfAppointment - current) / (1000);

        if(diffSec <= 0){
            return null;
        }
        return TimeUnit.SECONDS;
    }
}
