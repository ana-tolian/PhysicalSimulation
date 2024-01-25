package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;


public class MotionKernel {

    private final int NUMBER_OF_THREAD_WORKERS = 16;
    private final int LOG_THREAD_WORKERS = (int) (Math.log(NUMBER_OF_THREAD_WORKERS) / Math.log(2));

    private final RegionMap regionMap;
    private final Thread[] threads;
    private final ImpactThreadWorker[] impactWorkers;
    private final MoveThreadWorker[] moveWorkers;


    public MotionKernel (RegionMap regionMap) {
        this.regionMap = regionMap;
        this.threads = new Thread[NUMBER_OF_THREAD_WORKERS];
        this.impactWorkers = new ImpactThreadWorker[NUMBER_OF_THREAD_WORKERS];
        this.moveWorkers = new MoveThreadWorker[NUMBER_OF_THREAD_WORKERS];

        for (int i = 0; i < NUMBER_OF_THREAD_WORKERS; i++) {
            impactWorkers[i] = new ImpactThreadWorker(regionMap);
            moveWorkers[i]   = new MoveThreadWorker(regionMap);
        }
    }


    public void performImpact () {
        prepareImpactWorkers(regionMap.getParticles().size());
        startThreads();
        waitForThreads();

//        for (Particle p : regionMap.getParticles())
//            System.out.println("AAAA " + p.getX() + " " + p.getY() + " " + p.getLabel());
    }

    public void moveAll (double dt) {
        int oldIndex;
        for (Particle p : regionMap.getParticles()) {
            oldIndex = regionMap.to1DIndex(p);
            p.move(dt);

            regionMap.updateField(oldIndex, p);
        }
    }

    private void prepareImpactWorkers(int size) {
        for (int i = 0; i < NUMBER_OF_THREAD_WORKERS; i++) {
            setBounds(impactWorkers[i], size, i);
            threads[i] = new Thread(impactWorkers[i]);
        }
    }

    private void prepareMoveWorkers(int size, double dt) {
        for (int i = 0; i < NUMBER_OF_THREAD_WORKERS; i++) {
            setBounds(moveWorkers[i], size, i);
            moveWorkers[i].setDt(dt);
            threads[i] = new Thread(moveWorkers[i]);
        }
    }

    private void setBounds (ThreadWorker worker, int size, int i) {
        int indexWorkload = size >> LOG_THREAD_WORKERS;

        if (indexWorkload == 0 && size > 0 && i == 0) {
            worker.setBounds(0, size);
            return;
        }

        int beginIndex = i * indexWorkload;
        int endIndex = Math.min(beginIndex + indexWorkload, size);

        worker.setBounds(beginIndex, endIndex);
    }

    private void startThreads () {
        for (Thread thread : threads)
            thread.start();
    }

    private void waitForThreads () {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
