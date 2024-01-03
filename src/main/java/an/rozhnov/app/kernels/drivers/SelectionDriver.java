package an.rozhnov.app.kernels.drivers;


import an.rozhnov.app.entity.Particle;

import java.util.HashSet;

public class SelectionDriver {

    private HashSet<Particle> selected;

    public SelectionDriver () {
        selected = new HashSet<>();
    }

    public void addParticle (Particle p) {
        selected.add(p);
    }
}
