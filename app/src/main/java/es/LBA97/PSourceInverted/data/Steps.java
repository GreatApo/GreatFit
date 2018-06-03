package es.LBA97.PSourceInverted.data;

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
}
