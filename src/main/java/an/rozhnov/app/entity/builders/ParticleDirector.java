package an.rozhnov.app.entity.builders;

import an.rozhnov.app.entity.Particle;

import java.awt.*;

public class ParticleDirector {

    private ParticleBuilder particleBuilder;

    public ParticleDirector () {
        this.particleBuilder = new ParticleBuilder();
    }

    public Particle createRandomParticle () {
        return createMovingParticle(Math.random() * 1000, Math.random() * 1000,
                Math.random() * (0.5 - (-0.5)) + (-0.5), Math.random() * (0.5 - (-0.5)) + (-0.5));
    }

    public Particle createStaticParticle (double x, double y) {
        particleBuilder.setLabel("Unknown", "unk");
        particleBuilder.setVectors(x, y, 0, 0, 0, 0);
        return particleBuilder.createParticle();
    }

    public Particle createRandomMovingParticle (double x, double y) {
        return createMovingParticle(x, y, Math.random() * (0.5 - (-0.5)) + (-0.5), Math.random() * (0.5 - (-0.5)) + (-0.5));
    }

    public Particle createMovingParticle (double x, double y, double vx, double vy) {
        particleBuilder.setLabel("Unknown", "unk");
        particleBuilder.setVectors(x, y, vx, vy, 0, 0);
        return particleBuilder.createParticle();
    }

    public Particle createParticleForPalette (int radius, double mass, double t, double eps, double rmin, Color color) {
        particleBuilder.setLabel("Unknown", "unk");
        particleBuilder.setPhyParams(t, mass, radius, color);
        particleBuilder.setPotential(rmin, eps);
        return particleBuilder.createParticle();
    }

    public Particle createParticleFromBrush (Particle p, double x, double y) {
        particleBuilder.setLabel(p.getLabel());
        particleBuilder.setVectors(x + p.getPhyParams().getRadius(), y + p.getPhyParams().getRadius(), 0, 0, 0, 0);
        particleBuilder.setPhyParams(p.getPhyParams());
        particleBuilder.setPotential(p.getPotential());
        return particleBuilder.createParticle();
    }

    public Particle createMovingParticleFromBrush (Particle p, double x, double y, double vx, double vy) {
        particleBuilder.setLabel(p.getLabel());
        particleBuilder.setVectors(x + p.getPhyParams().getRadius(), y + p.getPhyParams().getRadius(), vx, vy, 0, 0);
        particleBuilder.setPhyParams(p.getPhyParams());
        particleBuilder.setPotential(p.getPotential());
        return particleBuilder.createParticle();
    }

}
