package an.rozhnov.app.kernels.drivers;

public class FPSController {

    private static long firstMark;
    public static int cycleTime = 1;
    public static int cyclesCount = 0;

    public static void startMeasuring () {
        firstMark = System.currentTimeMillis();
    }

    public static void stopMeasuring () {
        cyclesCount++;
        if (cyclesCount == 20) {
            cyclesCount = 0;
            cycleTime = (cycleTime + (int) (System.currentTimeMillis() - firstMark)) >> 1;
        }
    }

    public static int getSleepingTime () {
        return 1;
//        return Math.max(20 - cycleTime, 0);
    }

    public static long getFPS () {
        return cycleTime;
    }
}
