package es.LBA97.PSourceInverted.data;

/**
 * Calories data type
 */

public class Calories {

    private final int calories;

    public Calories(float calories) {
        this.calories = Math.round(calories);
    }

    public int getCalories() {
        return calories;
    }
}
