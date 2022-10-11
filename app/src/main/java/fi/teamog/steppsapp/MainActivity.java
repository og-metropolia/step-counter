package fi.teamog.steppsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

/**
 * Main class for application
 * @author Yamir Haque
 */

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TabLayout tabLayout;
    private Intent intentMain;
    TextView tvSteps;
    SensorManager sensorManager;
    boolean isMoving = false;

    /**
     * Asks for permission
     * If access continue
     * Else denied toasts text; "Permission denied!".
     */
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            });

    /**
     * Its onCreate method in MainActivity class.
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StepData.getInstance().loadPreviousData(this);

        tvSteps = findViewById (R.id.tv_steps);
        tvSteps.setText(String.valueOf(StepData.getInstance().getToday().getDaySteps()));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }

        StepData.getInstance().updateLatestDate();

    }

    /**
     * Its onResume method in MainActivity class.
     */
    @Override
    protected void onResume() {
        super.onResume();
        isMoving = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Its onPause method in MainActivity class.
     */
    @Override
    protected void onPause() {
        super.onPause();
        isMoving = false;
        StepData.getInstance().saveData(this);
        // If you unregister hardware will stop detecting steps
        //sensorManager.unregisterListener(this);
    }

    /**
     * Activates when step sensor triggers
     * @param event SensorEvent that returns step count
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isMoving) {
            int steps = (int)(event.values[0]);

            if (StepData.getInstance().getStepsSinceReboot() == 0) {
                StepData.getInstance().setStepsSinceReboot(steps);
                return;
            }

            if (StepData.getInstance().checkIfNewDate()) {
                StepData.getInstance().updateLatestDate();
                steps = 0;
            }

            tvSteps.setText(String.valueOf(StepData.getInstance().getToday().getDaySteps()));
            StepData.getInstance().getToday().updateCurrentHourSteps(steps);
            StepData.getInstance().saveData(this);
        }
    }

    /**
     * Triggers when accuracy changes
     * @param sensor step counter sensor
     * @param i current accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /**
     * Triggers when button is pressed
     * @param v view of the button that was pressed
     */
    public void buttonPressed(View v) {

        switch (v.getId()) {
            case R.id.buttonReport:
                Intent intentR = new Intent(this, ReportActivity.class);
                startActivity(intentR);
                break;
            case R.id.buttonDiary:
                Intent intentD = new Intent(this, DiaryActivity.class);
                startActivity(intentD);
                break;
        }
    }
}