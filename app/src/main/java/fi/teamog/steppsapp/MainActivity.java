package fi.teamog.steppsapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
     * If accees continue
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
        /**
         * Calling the TextView that we made in activity_main.xml
         * by the id given to that TextVie
         */
        tv_steps = (TextView)  findViewById (R.id.tv_steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (ContextCompat.checkSelfPermission(
                /**
                 * This sensor requires permission android.permission.ACTIVITY_RECOGNITION.
                 */
                this, Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }

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
        // If you unregister hardware will stop detecting steps
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            /**
             * Current steps are calculated by taking the difference of total steps
             * and previous steps
             */
            tv_steps.setText(String.valueOf((int)(event.values[0])));
            /**
             * This will set steps to tv_steps
             */
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}