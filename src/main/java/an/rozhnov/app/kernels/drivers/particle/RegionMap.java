package an.rozhnov.app.kernels.drivers.particle;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.drawing.ScalableGraphics;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static an.rozhnov.appState.currentState.AppGlobalState.mousePointer;


public class RegionMap {

    private final int squareSideLength = 4;
    private final int squareDim = (int) (Math.log(squareSideLength) / Math.log(2));
    private final int sqX = ScalableGraphics.LOGICAL_WIDTH >> squareDim;
    private final int sqY = ScalableGraphics.LOGICAL_HEIGHT >> squareDim;

    private final int size = sqX * sqY;

    private final ArrayList<HashSet<Particle>> regions;
    private final HashSet<Particle> particles;


    public RegionMap () {
        particles = new HashSet<>();
        regions = new ArrayList<>();
        build();
    }

    private void build () {
        for (int i = 0; i < size; i++)
            regions.add(new HashSet<>());
    }

    public void add (Particle p) {
        particles.add(p);
        regions.get(toRegionIndex(p)).add(p);
    }

    public void remove (Particle p) {
        particles.remove(p);
        regions.get(toRegionIndex(p)).remove(p);
    }

    public void updateRegion (Particle p) {
        int index = toRegionIndex(p);
        if (!inBorders(index))
            return;

        regions.get(index).remove(p);
        regions.get(index).add(p);
    }

    public int toRegionIndex (Particle p) {
        return toRegionIndex((int) p.getX(), (int) p.getY());
    }

    public int toRegionIndex (int x, int y) {
        return (x >> squareDim) + (y >> squareDim) * sqX;
    }

    public Rectangle findByCoords (int x, int y) {
        return toCoords(toRegionIndex(x, y));
    }

    public Rectangle toCoords (int index) {
        int x = index % sqX;
        int y = index / sqX;
        return new Rectangle(x, y, x + squareDim, y + squareDim);
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
                indexes.add(toRegionIndex(x, y));

        for (int i = 0; i < indexes.size(); i++) {
            HashSet<Particle> region = getParticlesInRegion(indexes.get(i));

            for (Particle p : region)
                if (r.contains(new Point((int) p.getX(), (int) p.getY())))
                    area.add(p);
        }

        return area;
    }

    public Particle getObservedParticle () {
        Particle particle = null;
        for (Particle p : getParticlesInRegion(toRegionIndex(mousePointer.x, mousePointer.y))) {
            if (((int) (p.getX())) == mousePointer.x && ((int) (p.getY())) == mousePointer.y) {
                particle = p;
            }
        }
        return particle;
    }

    public HashSet<Particle> findAllNeighbours (Particle p) {
        int index = toRegionIndex(p);

        boolean inBorders = inBorders(index);
        if (!inBorders)
            return new HashSet<>();

        boolean north = index - sqX >= 0;
        boolean south = index + sqX < size - 1;
        boolean west = index % sqX != 0;
        boolean east = index - 1 % sqX != 0;

        HashSet<Particle> neighbouringParticles = new HashSet<>(index);

        if (north)
            unite(neighbouringParticles, regions.get(index - sqX));

        if (south)
            unite(neighbouringParticles, regions.get(index + sqX));

        if (west) {
            unite(neighbouringParticles, regions.get(index - 1));

            if (north)   // North-West
                unite(neighbouringParticles, regions.get(index - sqX - 1));
            if (south)   // South-West
                unite(neighbouringParticles, regions.get(index + sqX - 1));
        }

        if (east) {
            unite(neighbouringParticles, regions.get(index + 1));

            if (north)   // North-East
                unite(neighbouringParticles, regions.get(index - sqX + 1));
            if (south)   // South-East
                unite(neighbouringParticles, regions.get(index + sqX + 1));
        }

//        System.out.println(sqX + " " + sqY + " " + index);
//        System.out.println(neighbouringParticles.size() + " " + regions.get(index - sqX).size() + " " + regions.get(index + sqX).size()
//                + " " + regions.get(index - 1).size() + " " + regions.get(index + 1).size() + " " + regions.get(index - sqX - 1).size() + " "
//                + regions.get(index - sqX + 1).size() + " " + regions.get(index + sqX + 1).size());
//        System.out.println(regions.get(index - sqX).size() + regions.get(index + sqX).size()
//                + regions.get(index - 1).size() + regions.get(index + 1).size() + regions.get(index - sqX - 1).size()
//                + regions.get(index - sqX + 1).size() + regions.get(index + sqX + 1).size());

        return neighbouringParticles;
    }

    private void unite(HashSet<Particle> neighbouringParticles, HashSet<Particle> temp) {
        Collections.addAll(neighbouringParticles, temp.toArray(new Particle[0]));
    }

    public void clear () {
        for (int i = 0; i < size; i++)
            regions.get(i).clear();
        particles.clear();
        AppGlobalState.clearSheduled = false;
    }

    private boolean inBorders (int index) {
        return index >= 0 && index < size - 1;
    }

    public HashSet<Particle> getParticles() {
        return particles;
    }

    public int getSize() {
        return size;
    }

    public int getSqX() {
        return sqX;
    }

    public int getSqY() {
        return sqY;
    }

    public int getSquareSideLength() {
        return squareSideLength;
    }

}
