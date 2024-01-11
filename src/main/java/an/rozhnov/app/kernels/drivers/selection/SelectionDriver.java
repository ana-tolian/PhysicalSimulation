package an.rozhnov.app.kernels.drivers.selection;


import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;

public class SelectionDriver {

    private HashSet<Particle> selected;
    private RegionMap regionMap;

    public SelectionDriver (RegionMap regionMap) {
        this.selected = new HashSet<>();
        this.regionMap = regionMap;
    }

    public void selectAllParticles () {
        selected.clear();
        Collections.addAll(selected, regionMap.getParticles().toArray(new Particle[0]));
    }

    public void selectParticles (int x1, int y1, int x2, int y2) {
        switch (AppGlobalState.selectionMode) {
            case NORMAL:
                getParticlesFromArea(x1, y1, x2, y2);
                break;
            case ADDITION:
                addParticlesFromArea(x1, y1, x2, y2);
                break;
            case SUBTRACTION:

                break;
        }
    }

    private void getParticlesFromArea (int x1, int y1, int x2, int y2) {
        selected = regionMap.getParticlesInArea(new Rectangle(x1, y1, x2, y2));
    }

    private void addParticlesFromArea (int x1, int y1, int x2, int y2) {
        Collections.addAll(selected, regionMap.getParticlesInArea(new Rectangle(x1, y1, x2, y2)).toArray(new Particle[0]));
    }

    public void addParticle (Particle p) {
        selected.add(p);
    }

    public void clearSelected () {
        selected.clear();
    }
}
