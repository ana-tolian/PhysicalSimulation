package an.rozhnov.app.kernels.drivers;

public class FPSController {

    private static long firstMark;
    public static long fps = 100;

    public static void startMeasuring () {
        firstMark = System.currentTimeMillis();
    }

    public static void stopMeasuring () {
        fps = (fps + 1000 / (System.currentTimeMillis() - firstMark)) >> 1;
    }

    public static int getSleepingTime () {
        if (fps < 30)
            return 1;
        if (fps < 50)
            return 10;
        return 20;
    }

    public static long getFPS () {
        return fps;
    }
}
