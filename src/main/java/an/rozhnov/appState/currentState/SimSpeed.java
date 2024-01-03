package an.rozhnov.appState.currentState;

public enum SimSpeed {
    SLOW(0.05),
    MEDIUM(0.1),
    FAST(0.2),
    VERY_FAST(0.3);

    private double dt;

    SimSpeed(double dt) {
        this.dt = dt;
    }

    public double dt() {
        return dt;
    }

    public static SimSpeed dt (int i) {
        switch (i) {
            case 0:
                return SLOW;
            case 1:
                return MEDIUM;
            case 2:
                return FAST;
            case 3:
                return VERY_FAST;
        }
        return SLOW;
    }
}
