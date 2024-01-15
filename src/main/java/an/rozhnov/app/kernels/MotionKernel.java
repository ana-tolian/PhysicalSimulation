package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;


public class MotionKernel {

    private final int NUMBER_OF_THREAD_WORKERS = 8;
    private final int LOG_THREAD_WORKERS = (int) (Math.log(NUMBER_OF_THREAD_WORKERS) / Math.log(2));

    private final RegionMap regionMap;
    private final Thread[] threads;
    private final ThreadWorker[] workers;


    public MotionKernel (RegionMap regionMap) {
        this.regionMap = regionMap;
        this.threads = new Thread[NUMBER_OF_THREAD_WORKERS];
        this.workers = new ThreadWorker[NUMBER_OF_THREAD_WORKERS];

        for (int i = 0; i < NUMBER_OF_THREAD_WORKERS; i++)
            workers[i] = new ThreadWorker(regionMap);
    }


    public void performImpact () {
        prepareForWork(regionMap.getParticles().size());
        startThreads();
        waitForThreads();
    }

    public void moveAll () {
        int oldIndex;
        for (Particle p : regionMap.getParticles()) {
            oldIndex = regionMap.to1DIndex(p);
            p.move();

            regionMap.updateField(oldIndex, p);
        }
    }

    private void prepareForWork (int size) {
        int beginIndex, endIndex;
        int indexWorkload = size >> LOG_THREAD_WORKERS;

        for (int i = 0; i < NUMBER_OF_THREAD_WORKERS; i++) {
            beginIndex = i * indexWorkload;
            endIndex = Math.min(beginIndex + indexWorkload, size);
            workers[i].setBounds(beginIndex, endIndex);
            threads[i] = new Thread(workers[i]);
        }
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
