package an.rozhnov.appState.currentState;

public enum SpeedMode {
    SLOW(0.01),
    MEDIUM(0.02),
    FAST(0.03),
    VERY_FAST(0.04);

    private double dt;

    SpeedMode(double dt) {
        this.dt = dt;
    }

    public double dt() {
        return dt;
    }

    public static SpeedMode dt (int i) {
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
