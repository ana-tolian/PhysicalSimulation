package an.rozhnov.app.kernels.etc;

import an.rozhnov.app.entity.Particle;

import java.util.ArrayDeque;


public class QueueControl {

    private final ArrayDeque<Particle> addQueue;
    private final RegionMap regionMap;

    public QueueControl (RegionMap regionMap) {
        this.regionMap = regionMap;
        addQueue = new ArrayDeque<>();
    }

    public void add (Particle p) {
        addQueue.add(p);
    }

    public Particle poll () {
        return addQueue.pollFirst();
    }

    public void addNewParticles () {
        for (int i = 0; i < addQueue.size(); i++) {
            regionMap.add(addQueue.pollFirst());
        }
    }

    public ArrayDeque<Particle> getQueue () {
        return addQueue;
    }
}
