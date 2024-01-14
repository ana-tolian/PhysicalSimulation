package an.rozhnov.app.entity;

import an.rozhnov.app.entity.particle_aux.Label;
import an.rozhnov.app.entity.particle_aux.PhyParams;
import an.rozhnov.app.entity.particle_aux.Potential;
import an.rozhnov.app.entity.particle_aux.SpatialVectors;
import an.rozhnov.app.kernels.drivers.drawing.ScalableGraphics;

public class Particle {

    private Label label;
    private SpatialVectors vectors;
    private Potential potential;
    private PhyParams phyParams;


    public Particle(Label label, SpatialVectors vectors, Potential potential, PhyParams phyParams) {
        this.label = label;
        this.vectors = vectors;
        this.potential = potential;
        this.phyParams = phyParams;
    }

    public void draw (ScalableGraphics scalableGraphics) {
        scalableGraphics.setColor(phyParams.getColor());
        scalableGraphics.fillOval((int) (vectors.r.x - getRadius()), (int) (vectors.r.y - getRadius()), (int) (getRadius()), (int) (getRadius()));
    }

    public double calculateLennardJones (Particle anotherParticle, double R) {
        return vectors.calculateLennardJones(this.potential.getRmin(anotherParticle), this.potential.getEps(anotherParticle), R);
    }

    public void moveBack () {
        vectors.move(phyParams.getMass() * -1);
    }

    public void move () {
        vectors.move(phyParams.getMass());
    }

    public double getX() {
        return vectors.getR().x;
    }

    public double getY() {
        return vectors.getR().y;
    }

    public int getRadius () {
        return phyParams.getRadius();
    }


    public Label getLabel() {
        return label;
    }

    public SpatialVectors getVectors() {
        return vectors;
    }

    public Potential getPotential() {
        return potential;
    }

    public PhyParams getPhyParams() {
        return phyParams;
    }
}
