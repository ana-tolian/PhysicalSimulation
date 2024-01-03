package an.rozhnov.app.kernels.etc;

import an.rozhnov.app.entity.Particle;

import java.util.ArrayDeque;

public class QueueControl {

    private final ArrayDeque<Particle> addQueue;

    public QueueControl () {
        addQueue = new ArrayDeque<>();
    }

    public void add (Particle p) {
        addQueue.add(p);
    }

    public ArrayDeque<Particle> getQueue () {
        return addQueue;
    }
}
