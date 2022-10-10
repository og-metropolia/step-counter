package fi.teamog.steppsapp;

import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StepAverages {

    private static final StepAverages ourInstance = new StepAverages();
    public static StepAverages getInstance() {
        return ourInstance;
    }

    private StepAverages() {
    }
    public int getThreeDayAverage() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String threeDaysAgo = LocalDate.now().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        return StepData.getInstance().getPeriodSteps(threeDaysAgo, today) / 3;
    }

    public int getThirtyDayAverage() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String thirtyDaysAgo = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE);
        return StepData.getInstance().getPeriodSteps(thirtyDaysAgo, today) / 30;
    }
}
