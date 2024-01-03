package an.rozhnov.app.entity;

import an.rozhnov.app.entity.particle_aux.Label;
import an.rozhnov.app.entity.particle_aux.PhyParams;
import an.rozhnov.app.entity.particle_aux.Potential;
import an.rozhnov.app.entity.particle_aux.SpatialVectors;

import java.awt.*;

public class Particle {

    private Label label;
    private SpatialVectors vectors;
    private Potential potential;
    private PhyParams phyParams;

    public double rx = 0;
    public double ry = 0;


    public Particle(Label label, SpatialVectors vectors, Potential potential, PhyParams phyParams) {
        this.label = label;
        this.vectors = vectors;
        this.potential = potential;
        this.phyParams = phyParams;
    }

    public void draw (Graphics2D g2) {
        draw(g2, 1.0);
    }

    public void draw (Graphics2D g2, double scale) {
        g2.setColor(phyParams.getColor());
        g2.fillOval((int) (vectors.r.x - getRadius()), (int) (vectors.r.y - getRadius()), (int) (getRadius() * scale), (int) (getRadius() * scale));
    }

    public double calculateLennardJones (double R) {
        return vectors.calculateLennardJones(potential, R);
    }

    public void moveBack () {
        vectors.move(phyParams.getMass() * -1);
    }

    public void move () {
        vectors.move(phyParams.getMass());
    }

    public void bounceOffHorizontalWall () {
        vectors.v.y = -vectors.v.y;
    }

    public void bounceOffVerticalWall () {
        vectors.v.x = -vectors.v.x;
    }

    public double getX() {
        return vectors.getR().x;
    }

    public double getY() {
        return vectors.getR().y;
    }

    public double getRadius () {
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
