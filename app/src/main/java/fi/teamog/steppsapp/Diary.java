package fi.teamog.steppsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Diary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        View saveButton = findViewById(R.id.buttonSaveDiary);

    }
    private void saveData(String value) {
        SharedPreferences prefPut = getSharedPreferences("logData", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putString(getString(R.string.diary_preference_key), value);
        prefEditor.commit();
    }

}