package an.rozhnov.app.kernels.drivers.selection;


import an.rozhnov.app.entity.Particle;

import java.util.HashSet;

public class SelectionDriver {

    private HashSet<Particle> selected;

    public SelectionDriver () {
        selected = new HashSet<>();
    }

    public void addAllParticlesFromArea (int x1, int y1, int x2, int y2) {

    }

    public void addParticle (Particle p) {
        selected.add(p);
    }

    public void clearSelected () {
        selected.clear();
    }
}
