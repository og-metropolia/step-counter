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
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

/**
 * @author Yamir Haque
 * @author Adrian Gashi
 * Added SensorEventListener the MainActivity class
 * Implement all the members in the class MainActivity
 * after adding SensorEventListener
 */

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps;
    SensorManager sensorManager;
    boolean running = false;

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
     * Calling the TextView that we made in activity_main.xml
     * by the id given to that TextVie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_steps = (TextView)  findViewById (R.id.tv_steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }

    }

    /**
     * Returns the number of steps taken by the user since the last reboot while activated
     */
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    /**
     * Current steps are calculated by taking the difference of total steps
     * and previous steps
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {

            tv_steps.setText(String.valueOf((int)(event.values[0])));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}