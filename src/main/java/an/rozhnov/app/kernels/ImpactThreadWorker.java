package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;


public class ImpactThreadWorker extends ThreadWorker implements Runnable {

    private final RegionMap regionMap;


    public ImpactThreadWorker(RegionMap regionMap) {
        this.regionMap = regionMap;
    }

    @Override
    public void run() {
        for (int i = beginIndex; i < endIndex; i++) {
            performImpact(regionMap.getParticles().get(i));
        }
    }

    private void performImpact (Particle p1) {
        // Look for any neighbours in the square centered on p1.
        // We are only looking for particles that right and down from particle p1
        for (int x = (int) p1.getX(); x < p1.getX() + 2; x++) {

            for (int y = (int) p1.getY(); y < p1.getY() + 2; y++) {

                for (Particle p2 : regionMap.findByCoords(x, y)) {

                    if (p1.equals(p2))
                        continue;

                    double rx = p1.getX() - p2.getX();
                    double ry = p1.getY() - p2.getY();

                    if (Math.abs(rx) < 10e-5)
                        rx += 10e-3 * (Math.random() - 0.5);
                    if (Math.abs(ry) < 10e-5)
                        ry += 10e-3 * (Math.random() - 0.5);

                    double R = rx * rx + ry * ry;
                    double invR = 1 / R;

                    double PLJ_p1_to_p2 = p1.calculateLennardJones(p2, invR);    // potential well of p1 that impacts on p2
                    double PLJ_p2_to_p1 = p2.calculateLennardJones(p1, invR);    // potential well of p2 that impacts on p1

                    p2.getVectors().applyForces(rx * PLJ_p1_to_p2, ry * PLJ_p1_to_p2);      // apply force from p1 to p2
                    p1.getVectors().applyForces(-rx * PLJ_p2_to_p1, -ry * PLJ_p2_to_p1);    // apply force from p2 to p1
                }
            }
        }

    }
}
