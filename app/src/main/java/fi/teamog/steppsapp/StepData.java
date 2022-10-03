package fi.teamog.steppsapp;

import java.util.HashMap;

/**
 * Singleton that stores data in Day objects in a HashMap
 * @author Leo Härkönen
 */
public class StepData {
    private static final StepData ourInstance = new StepData();
    private static HashMap<String, Day> days = new HashMap<>();

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
}
