package fi.teamog.steppsapp;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.Date;
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

    /**
     * Gets total amount of steps in the day.
     * @return amount of steps
     */
    public int getDaySteps() {
        int daySteps = 0;
        for (int hour = 0; hour <= 23; hour++) {
            daySteps += this.getHourSteps(hour);
        }
        return daySteps;
    }

    public void updateCurrentHourSteps(int currentStepTotal) {
        int newSteps = currentStepTotal - StepData.getInstance().getLifetimeStepTotal();
        this.addSteps(this.getCurrentHour(), newSteps);

        StepData.getInstance().setLifetimeStepTotal(StepData.getInstance().getLifetimeStepTotal() + newSteps);

        Log.d("STEPS", "lifetime steps: "+StepData.getInstance().getLifetimeStepTotal());
        Log.d("STEPS", "newSteps: "+newSteps);
        Log.d("STEPS", "currentStepTotal: "+currentStepTotal);
        Log.d("STEPS", "hour steps after update: " + this.getHourSteps(this.getCurrentHour()));
    }

    public int getCurrentHour() {
        return Integer.parseInt(StepData.getInstance().hoursOnly.format(new Date()));
    }
}
