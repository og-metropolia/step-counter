package fi.teamog.steppsapp;

import android.util.Log;

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
    private static final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
        LocalDate firstDay = LocalDate.parse(firstDateIso);
        LocalDate secondDay = LocalDate.parse(secondDateIso);

        LocalDate startDate;
        LocalDate endDate;

        if (firstDay.compareTo(secondDay) > 0) {
            startDate = secondDay;
            endDate = firstDay;
        } else {
            startDate = firstDay;
            endDate = secondDay;
        }

        LocalDate currentDate = startDate;
        int totalSteps = 0;
        while (endDate.compareTo(currentDate) >= 0) {
            totalSteps += StepData.getInstance().getDay(currentDate.toString()).getDaySteps();
            currentDate = currentDate.plusDays(1);
        }

        return totalSteps;
    }

}
