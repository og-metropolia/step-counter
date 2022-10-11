package fi.teamog.steppsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Activity that collects and displays user's moods.
 * @author Adrian Gashi
 */
public class DiaryActivity extends AppCompatActivity {

    String mood;
    EditText moodNow;
    ArrayList moodList;
    ListView lv;
    private final String FILE_NAME = "mood_data.json";

    /**
     * Its onCreate method in DiaryActivity class.
      * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        moodNow = findViewById(R.id.moodTextMultiLine);

        moodList = new ArrayList<>();

        this.loadPreviousData(this);

        lv = findViewById(R.id.listViewMoods);

        lv.setAdapter(new ArrayAdapter<String>(
                this,  //context (activity instance)
                R.layout.mood_item, //XML item layout
                moodList));
    }
    /**
     * Its onPause method in DiaryActivity class.
     */
    protected void onPause() {
        super.onPause();
        this.saveData(this);
    }

    /**
     * This method creates a list item and saves it
     * @param view
     */
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSaveDiary) {
            mood = moodNow.getText().toString();
            moodList.add(mood);
            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
            moodNow.setText("");
            this.saveData(this);
        }
    }
    /**
     * Saves mood list into a JSON file in internal storage of the phone.
     * @param context context
     */
    public void saveData(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(new Gson().toJson(moodList));
            outputStreamWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads saved mood data from a JSON file in internal storage of the phone.
     * @param context context
     * @return data in JSON format String
     */
    public String readData(Context context) {
        String data = "";
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                data = stringBuilder.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Replaces mood data in memory with the data found on internal storage of the phone.
     * @param context context
     */
    public void loadPreviousData(Context context) {
        TypeToken<ArrayList<String>> token = new TypeToken<ArrayList<String>>(){};
        if (!this.readData(context).equals("")) {
            moodList = new Gson().fromJson(this.readData(context), token.getType());
        } else {
            moodList = new ArrayList<>();
        }
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