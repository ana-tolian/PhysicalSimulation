package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.devControl.LoadMeasurer;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;


public class MotionKernel {

    private final LoadMeasurer measurer;
    private final RegionMap regionMap;


    public MotionKernel (MainKernel mainKernel) {
        this.regionMap = mainKernel.regionMap;
        measurer = new LoadMeasurer(regionMap.getParticles());
    }


    public void performImpact () {
        measurer.startMeasuring();

        // Go through all particles on the field
        for (Particle p1 : regionMap.getParticles()) {
            measurer.newInnerCycle();

            // Look for any neighbours in the square centered on p1.
            for (int x = (int) p1.getX() - 2; x < p1.getX() + 2; x++) {

                for (int y = (int) p1.getY() - 2; y < p1.getY() + 2; y++) {

                    for (Particle p2 : regionMap.findByCoords(x, y)) {

                        if (p1.equals(p2))
                            continue;
                        measurer.newOperation();

                        double rx = p1.getX() - p2.getX();
                        double ry = p1.getY() - p2.getY();

                        if (Math.abs(rx) < 10e-5)
                            rx += 10e-3 * (Math.random() - 0.5);
                        if (Math.abs(ry) < 10e-5)
                            ry += 10e-3 * (Math.random() - 0.5);

                        double R = rx * rx + ry * ry;
                        double invr = 1 / Math.sqrt(R);
                        rx *= invr;
                        ry *= invr;

                        double PLJ = p1.calculateLennardJones(p2, R);

                        p2.getVectors().applyForces(rx * PLJ, ry * PLJ);
                    }
                }
            }
        }
        //        measurer.endMeasuring();
    }


    public void moveAll () {
        int oldIndex;
        for (Particle p : regionMap.getParticles()) {
            oldIndex = regionMap.to1DIndex(p);
            p.move();

            regionMap.updateField(oldIndex, p);
        }
    }
}
