package fatproject.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 25.05.2018.
 */
public interface DifferenceCounter {

    TimeUnit countDifference(long current, long timestampOfAppointment);


}
