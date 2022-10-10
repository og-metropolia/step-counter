package fi.teamog.steppsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        StepData.getInstance().addDay("2022-09-29");
        Day day = StepData.getInstance().getDay("2022-09-29");
        day.addSteps(6,3000);
        day.addSteps(8,2500);
        StepData.getInstance().saveData(this);
        StepData.getInstance().loadPreviousData(this);

        StepData.getInstance().addDay("2022-10-09");
        Day day2 = StepData.getInstance().getDay("2022-10-09");
        day.addSteps(6,1000);
        day.addSteps(8,2500);
        StepData.getInstance().saveData(this);
        StepData.getInstance().loadPreviousData(this);

        StepData.getInstance().addDay("2022-10-10");
        Day day3 = StepData.getInstance().getDay("2022-10-10");
        day.addSteps(6,1000);
        day.addSteps(8,2500);
        StepData.getInstance().saveData(this);
        StepData.getInstance().loadPreviousData(this);

        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String threeDaysAgo = LocalDate.now().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String thirtyDaysAgo = LocalDate.now().minusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE);

        TextView tvThreeDayAvg = findViewById(R.id.TextViewThirtyDaysValue);
        tvThreeDayAvg.setText(String.valueOf(StepData.getInstance().getPeriodSteps(threeDaysAgo, today)/3));

        TextView tvThirtyDayAvg = findViewById(R.id.TextViewLastThreeDaysValue);
        tvThirtyDayAvg.setText(String.valueOf(StepData.getInstance().getPeriodSteps(thirtyDaysAgo, today)/30));
    }
}