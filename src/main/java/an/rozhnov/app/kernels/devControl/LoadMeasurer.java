package an.rozhnov.app.kernels.devControl;

import an.rozhnov.app.entity.Particle;
import java.util.HashSet;

public class LoadMeasurer {

    private int i = 0;
    private long cycleCount = 0l;
    private long innerCycleCount = 0l;
    private long opAccelerated = 0l;

    private long timeMark = 0l;
    private HashSet<Particle> particles;


    public LoadMeasurer (HashSet<Particle> particles) {
        this.particles = particles;
    }

    public void newCycle () {
        cycleCount++;
    }

    public void newInnerCycle () {
        innerCycleCount++;
    }

    public void newOperation () {
        opAccelerated++;
    }

    public double meanOperationsForCycle () {
        if (cycleCount == 0) return 0;
        return opAccelerated / cycleCount;
    }

    public double meanOperationsForInnerCycle () {
        if (innerCycleCount == 0) return 0;
        return opAccelerated / innerCycleCount;
    }

    private void firstTimeMark () {
        timeMark = System.currentTimeMillis();
    }

    private void secondTimeMark () {
        timeMark = System.currentTimeMillis() - timeMark;
    }

    public void startMeasuring () {
        firstTimeMark();
    }

    public void endMeasuring () {
        secondTimeMark();
        i++;

        if (i % 10 == 0)
            printLoadInfo();

        cycleCount = 0;
        innerCycleCount = 0;
        opAccelerated = 0;
    }

    public void printLoadInfo () {
        System.out.println();
        System.out.println("Current amount of particles: " + particles.size());
        System.out.println("Time passed for the last cycle: " + String.format("%.3f", timeMark/1000d) + " sec");
        System.out.println("Amount of inner cycles: " + String.format("%d", innerCycleCount) + " ops");
        System.out.println("Amount of neighbours observed per inner cycle: " + String.format("%d", opAccelerated) + " ngbrs");
//        System.out.println("Average amount of operations observed per cycle: " + String.format("%.0f", meanOperationsForCycle()) + " ops");
//        System.out.println("Average amount of neighbours observed per inner cycle: " + String.format("%.0f", meanOperationsForInnerCycle()) + " ngbrs");
    }

}
