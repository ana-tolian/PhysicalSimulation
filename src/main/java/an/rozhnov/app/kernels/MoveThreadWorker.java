package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;

public class MoveThreadWorker extends ThreadWorker implements Runnable {

    private final RegionMap regionMap;
    private double dt;


    public MoveThreadWorker(RegionMap regionMap) {
        this.regionMap = regionMap;
    }

    @Override
    public void run() {
        int oldIndex;
        for (Particle p : regionMap.getParticles()) {
            oldIndex = regionMap.to1DIndex(p);
            p.move(dt);

            regionMap.updateField(oldIndex, p);
        }
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

}
