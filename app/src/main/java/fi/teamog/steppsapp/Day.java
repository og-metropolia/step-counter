package fi.teamog.steppsapp;

import java.util.HashMap;

/**
 * Stores steps of a day hourly in a HashMap.
 * @author Leo Härkönen
 */
public class Day {
    private HashMap<Integer, Integer> stepsByHour = new HashMap<>();

    public Day() {

    }

    /**
     * Gets amount of steps for the given hour.
     * @param hour hour of the day, value between 0-23
     * @return amount of steps
     */
    public int getHourSteps(int hour) {
        return stepsByHour.getOrDefault(hour, 0);
    }

    /**
     * Adds steps to the given hour's previous amount.
     * @param hour hour of the day, value between 0-23
     * @param newSteps steps that will be added
     */
    public void addSteps(int hour, int newSteps) {
        stepsByHour.put(hour, stepsByHour.getOrDefault(hour, 0) + newSteps);
    }
}
