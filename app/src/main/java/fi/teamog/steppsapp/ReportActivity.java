package fi.teamog.steppsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Activity for displaying step averages for 3 and 30 days.
 * @author Taavi Nätynki
 */
public class ReportActivity extends AppCompatActivity {


    @Override
    /**
     * Gets numbers of steps from 30 days and 3 days, and shows the value to the user
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        TextView tvThreeDayAvg = findViewById(R.id.TextViewThirtyDaysValue);
        tvThreeDayAvg.setText(String.valueOf(StepAverages.getInstance().getThreeDayAverage()));

        TextView tvThirtyDayAvg = findViewById(R.id.TextViewLastThreeDaysValue);
        tvThirtyDayAvg.setText(String.valueOf(StepAverages.getInstance().getThirtyDayAverage()));
    }

    /**
     * Implements back button in left up corner.
     * @return true
     */
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}