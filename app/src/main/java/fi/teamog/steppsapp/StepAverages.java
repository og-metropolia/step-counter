package fi.teamog.steppsapp;

import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class for calculating step averages.
 * @author Taavi Nätynki
 */
public class StepAverages {

    private static final StepAverages ourInstance = new StepAverages();
    public static StepAverages getInstance() {
        return ourInstance;
    }

    private StepAverages() {
    }

    /**
     * Gets steps within the last 3 days.
     * @return amount of steps within the last 3 days
     */
    public int getThreeDayAverage() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String threeDaysAgo = LocalDate.now().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        return StepData.getInstance().getPeriodSteps(threeDaysAgo, today) / 3;
    }

    /**
     * Gets steps within the last 30 days.
     * @return amount of steps within the last 30 days
     */
    public int getThirtyDayAverage() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String thirtyDaysAgo = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE);
        return StepData.getInstance().getPeriodSteps(thirtyDaysAgo, today) / 30;
    }
}
