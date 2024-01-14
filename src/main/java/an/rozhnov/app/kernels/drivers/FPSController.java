package an.rozhnov.app.kernels.drivers;

public class FPSController {

    private static long firstMark;
    public static int cycleTime = 20;

    public static void startMeasuring () {
        firstMark = System.currentTimeMillis();
    }

    public static void stopMeasuring () {
        cycleTime = (int) (System.currentTimeMillis() - firstMark);
    }

    public static int getSleepingTime () {
        return 1;
//        return Math.max(20 - cycleTime, 0);
    }

    public static long getFPS () {
        return cycleTime;
    }
}
