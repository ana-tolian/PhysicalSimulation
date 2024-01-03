package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.devControl.LoadMeasurer;
import an.rozhnov.app.kernels.etc.RegionMap;


public class MotionKernel {

    private final LoadMeasurer measurer;
    private final MainKernel mainKernel;
    private final RegionMap regionMap;


    public MotionKernel (MainKernel mainKernel) {
        this.mainKernel = mainKernel;
        this.regionMap = mainKernel.regionMap;
        measurer = new LoadMeasurer(regionMap.getParticles());
    }


    public void performImpact () {
        measurer.startMeasuring();

        for (Particle p1 : regionMap.getParticles()) {
//            if (p1.getX() <= 0 || p1.getX() + p1.getRadius() * 2 >=  SIM_WIDTH) {
//                p1.moveBack();
//                p1.bounceOffVerticalWall();
//            }
//            if (p1.getY() <= 0 || p1.getY() + p1.getRadius() * 2 >= SIM_HEIGHT) {
//                p1.moveBack();
//                p1.bounceOffHorizontalWall();
//            }

            measurer.newInnerCycle();

            for (Particle p2 : regionMap.findAllNeighbours(p1)) {
                if (p1.equals(p2))
                    continue;
                measurer.newOperation();

                double rx = p2.getX() - p1.getX();
                double ry = p2.getY() - p1.getY();

                double R = rx * rx + ry * ry;
                double invr = 1 / Math.sqrt(R);
                rx *= invr;
                ry *= invr;

                double PLJ = p1.calculateLennardJones(R);
                p1.getVectors().applyForces(rx * PLJ, ry * PLJ);
            }
        }

//        measurer.endMeasuring();
    }

    public void moveAll () {
        for (Particle p : regionMap.getParticles()) {
            p.move();
            regionMap.updateRegion(p);
        }
    }
}
