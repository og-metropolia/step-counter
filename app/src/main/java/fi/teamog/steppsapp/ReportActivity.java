package fi.teamog.steppsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.time.LocalDate;

public class ReportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        StepData stepData = StepData.getInstance();
        LocalDate today = LocalDate.now();
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        StepData.getInstance().getPeriodSteps(StepData.getInstance().getIsoDateFormat().format(threeDaysAgo), StepData.getInstance().getIsoDateFormat().format(today));
    }
}