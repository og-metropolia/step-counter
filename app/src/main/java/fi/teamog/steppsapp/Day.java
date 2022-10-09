package fi.teamog.steppsapp;

import java.util.Date;
import java.util.HashMap;

/**
 * Stores steps of a day hourly in a HashMap.
 * @author Leo Härkönen
 */
public class Day {
    private final HashMap<Integer, Integer> stepsByHour = new HashMap<>();

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

    /**
     * Adds new steps to the current hour's total.
     * @param currentLifetimeSteps Current amount of steps from last reboot of the device. This will be compared to the previous total giving the amount of new steps.
     */
    public void updateCurrentHourSteps(int currentLifetimeSteps) {
        int newSteps = currentLifetimeSteps - StepData.getInstance().getStepsSinceReboot();
        this.addSteps(this.getCurrentHour(), newSteps);
        StepData.getInstance().setStepsSinceReboot(currentLifetimeSteps);
    }

    /**
     * Getter for steps within the current hour today.
     * @return amount of steps in the current hour
     */
    public int getCurrentHour() {
        return Integer.parseInt(StepData.getInstance().hoursOnlyFormat.format(new Date()));
    }
}
