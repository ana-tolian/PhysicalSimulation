package an.rozhnov.app.kernels.drivers.particle;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.drawing.ScalableGraphics;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import static an.rozhnov.appState.currentState.AppGlobalState.mousePointer;


public class RegionMap {

    private final int squareSideLength = 5;
    private final static int X = ScalableGraphics.LOGICAL_WIDTH;
    private final static int Y = ScalableGraphics.LOGICAL_HEIGHT;

    public final int SIZE = X * Y;

    private final ArrayList<HashSet<Particle>> regions;
    private final ArrayList<Particle> particles;


    public RegionMap () {
        particles = new ArrayList<>(SIZE);
        regions = new ArrayList<>();
        build();
    }

    private void build () {
        for (int i = 0; i < SIZE; i++)
            regions.add(new HashSet<>());
    }

    public void add (Particle p) {
        if(!inBorders(p))
            return;
        particles.add(p);
        regions.get(to1DIndex(p)).add(p);
    }

    public void remove (Particle p) {
        particles.remove(p);
        regions.get(to1DIndex(p)).remove(p);
    }

    public void clearFromField (Particle p) {
        int index = to1DIndex(p);
        if (!inBorders(index))
            return;

        regions.get(index).remove(p);
    }

    public void updateField(int oldIndex, Particle p) {
        if (!inBorders(oldIndex))
            return;

        int newIndex = to1DIndex(p);
        regions.get(oldIndex).remove(p);

        if (!inBorders(newIndex)) return;
        regions.get(newIndex).add(p);
    }

    public int to1DIndex(Particle p) {
        if (p == null)
            return -1;
        return to1DIndex((int) p.getX(), (int) p.getY());
    }

    public int to1DIndex(int x, int y) {
        if (inBorders(x, y))
            return x + y* X;
        return -1;
    }

    public HashSet<Particle> findByCoords (Particle p) {
        return findByCoords((int) p.getX(), (int) p.getY());
    }

    public HashSet<Particle> findByCoords (int x, int y) {
        if (inBorders(x, y))
            return regions.get(to1DIndex(x, y));
        return new HashSet<>();
    }

    public HashSet<Particle> getParticlesInRegion (int index) {
        if (inBorders(index))
            return regions.get(index);
        return new HashSet<>();
    }

    public HashSet<Particle> getParticlesInArea (Rectangle r) {
        int x1 = r.x;
        int y1 = r.y;
        int x2 = r.width;
        int y2 = r.height;

        ArrayList<Integer> indexes = new ArrayList<>();
        HashSet<Particle> area = new HashSet<>();

        for (int x = x1; x < x2; x += squareSideLength)
            for (int y = y1; y < y2; y += squareSideLength)
                indexes.add(to1DIndex(x, y));

        for (Integer index : indexes) {
            HashSet<Particle> region = getParticlesInRegion(index);

            for (Particle p : region)
                if (r.contains(new Point((int) p.getX(), (int) p.getY())))
                    area.add(p);
        }

        return area;
    }

    public Particle getObservedParticle () {
        Particle particle = null;
        for (Particle p : getParticlesInRegion(to1DIndex(mousePointer.x, mousePointer.y))) {
            if (((int) (p.getX())) == mousePointer.x && ((int) (p.getY())) == mousePointer.y) {
                particle = p;
            }
        }
        return particle;
    }

    public void clear () {
        for (int i = 0; i < SIZE; i++)
            regions.get(i).clear();
        particles.clear();
        AppGlobalState.clearSheduled = false;
    }

    public boolean inBorders (int index) {
        return index >= 0 && index < SIZE - 1;
    }

    public boolean inBorders (Particle p) {
        return inBorders((int) p.getX(), (int) p.getY());
    }

    public boolean inBorders (int x, int y) {
        return x >= 0 && x < X - 1 && y >= 0 && y < Y - 1;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public int getSquareSideLength() {
        return squareSideLength;
    }

}
