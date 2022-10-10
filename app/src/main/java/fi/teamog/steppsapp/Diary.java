package fi.teamog.steppsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Diary extends AppCompatActivity {

    String mood;
    EditText moodNow;
    ArrayList moodList;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        moodNow = findViewById(R.id.moodTextMultiLine);

        moodList = new ArrayList<>();

        lv = findViewById(R.id.listViewMoods);

        lv.setAdapter(new ArrayAdapter<String>(
                this,  //context (activity instance)
                R.layout.mood_item, //XML item layout
                moodList));
    }

    public void onClick(View view) {
        if(view.getId() == R.id.buttonSaveDiary) {
            mood = moodNow.getText().toString();
            moodList.add(mood);
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
            moodNow.setText("");
            Log.e("Test", "Nappi toimii");

        }

    }
}