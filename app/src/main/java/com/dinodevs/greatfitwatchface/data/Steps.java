package com.dinodevs.greatfitwatchface.data;

/**
 * Steps data
 */

public class Steps {

    private final int steps;
    private final int target;

    public Steps(int steps, int target) {
        this.steps = steps;
        this.target = target;
    }

    public int getSteps() {
        return steps;
    }

    public int getTarget() {
        return target;
    }

    public String getStepsMetric(double step_length) {
        double distance = (((double) this.steps) * step_length/ 100.0d) ;
        if(distance<1000){
            return distance+" m";
        }
        return String.format(distance/1000d < 100 ? "%.2f" : "%.1f", distance/1000d)+" km";
    }

    public String getStepsImperial(double step_length) {
        double distance = ((((double) this.steps) * step_length) / 100000.0d) * 0.621371d;
        if(distance<0.18939){
            return Math.round(distance*5280)+" ft";
        }
        return String.format(distance < 100 ? "%.2f" : "%.1f", distance)+" mi";
    }

}
