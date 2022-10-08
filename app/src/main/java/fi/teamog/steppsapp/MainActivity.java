package fi.teamog.steppsapp;

import android.Manifest;
import android.content.Context;
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
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Yamir Haque
 * @author Adrian Gashi
 * Added SensorEventListener the MainActivity class
 * Implement all the members in the class MainActivity
 * after adding SensorEventListener
 */

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    /**
     * Asks for permission
     * If access continue
     * Else denied toasts text; "Permission denied!".
     */
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();

                    // Permission is granted. Continue the action or workflow in your
                    // app.
                }
            });

    TextView tv_steps;
    /**
     * Adding a context of SENSOR_SERVICE aas Sensor Manager
     */
    SensorManager sensorManager;

    /**
     * Creating a variable which will give the running status
     * and initially given the boolean value as false
     */
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("STEPS", "--------------------------onCreate()--------------------------");

        StepData.getInstance().loadPreviousData(this);

        tv_steps = (TextView)  findViewById (R.id.tv_steps);
        tv_steps.setText(String.valueOf(StepData.getInstance().getToday().getDaySteps()));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }
        StepData.getInstance().setLatestDateToToday();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        /**
         * Returns the number of steps taken by the user since the last reboot while activated
         */
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            /**
             * This will give a toast message to the user if there is no sensor in the device
             */
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        StepData.getInstance().saveData(this);
        // If you unregister hardware will stop detecting steps
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("STEPS", "-----------------------onSensorChanged()-----------------------");
        if (running) {
            int steps = 0;
            steps = (int)(event.values[0]);

            if (StepData.getInstance().getLifetimeStepTotal() == 0) {
                StepData.getInstance().setLifetimeStepTotal(steps);
                return;
            }

            if (StepData.getInstance().checkForNewDay()) {
                    Log.d("STEPS", "new day");
                Toast.makeText(this, "new day", Toast.LENGTH_SHORT).show();
                    StepData.getInstance().setLatestDateToToday();
                    steps = 0;
            }

            tv_steps.setText(String.valueOf(StepData.getInstance().getToday().getDaySteps()));

            StepData.getInstance().getToday().updateCurrentHourSteps(steps);
            StepData.getInstance().saveData(this);

            Toast.makeText(this, "new steps", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}