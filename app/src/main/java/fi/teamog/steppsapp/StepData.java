package fi.teamog.steppsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

/**
 * Singleton that stores data in Day objects in a HashMap
 * @author Leo Härkönen
 */
public class StepData {
    private static final StepData ourInstance = new StepData();
    private static HashMap<String, Day> days = new HashMap<>();
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String FILE_NAME = "step_data.json";

    /**
     * Access to singleton instance of StepData.
     * @return singleton instance of StepData
     */
    public static StepData getInstance() {
        return ourInstance;
    }

    private StepData() {
    }

    /**
     * Adds a day to the HashMap with ISO date as the key and Day object as the value.
     * @param dateIso date in ISO format string, for example "2007-07-17"
     */
    public void addDay(String dateIso) {
        days.put(dateIso, new Day());
    }

    /**
     * Gets a day by ISO date string.
     * @param dateIso date in ISO format string, for example "2007-07-17"
     * @return a Day object or null if object does not exist in the HashMap
     */
    public Day getDay(String dateIso) {
        return days.getOrDefault(dateIso, null);
    }

    /**
     * Adds current day to the HashMap with ISO date as the key and Day object as the value.
     */
    public void addToday() {
        days.put(isoDateFormat.format(new Date()), new Day());
    }

    /**
     * Gets amount of steps between two ISO dates. Chronological order of the two dates given does not matter.
     * @param firstDateIso a date in iso format
     * @param secondDateIso a date in iso format
     * @return amount of steps between the two dates
     */
    public int getPeriodSteps(String firstDateIso, String secondDateIso) {
        LocalDate firstDate = LocalDate.parse(firstDateIso);
        LocalDate secondDate = LocalDate.parse(secondDateIso);

        LocalDate startDate;
        LocalDate endDate;

        if (firstDate.compareTo(secondDate) > 0) {
            startDate = secondDate;
            endDate = firstDate;
        } else {
            startDate = firstDate;
            endDate = secondDate;
        }

        LocalDate currentDate = startDate;
        int totalSteps = 0;
        while (endDate.compareTo(currentDate) >= 0) {
            totalSteps += StepData.getInstance().getDay(currentDate.toString()).getDaySteps();
            currentDate = currentDate.plusDays(1);
        }

        return totalSteps;
    }

    /**
     * Saves step data into a JSON file in internal storage of the phone.
     * @param context context
     */
    public void saveData(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(new Gson().toJson(days));
            outputStreamWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads saved step data from a JSON file in internal storage of the phone.
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
     * Replaces step data in memory with the data found on internal storage of the phone.
     * @param context context
     */
    public void loadPreviousData(Context context) {
        TypeToken<HashMap<String, Day>> token = new TypeToken<HashMap<String, Day>>() {};
        days = new Gson().fromJson(this.readData(context), token.getType());
    }

}
