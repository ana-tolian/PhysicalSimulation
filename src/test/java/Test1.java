import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.drawing.ScalableGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Test1 {

    private final int squareSideLength = 5;
    private final int sqX = ScalableGraphics.LOGICAL_WIDTH / squareSideLength;
    private final int sqY = ScalableGraphics.LOGICAL_HEIGHT / squareSideLength;

    private final int size = sqX * sqY;

    private final ArrayList<HashSet<Particle>> field;
    private final HashSet<Particle> particles;


    public Test1 () {
        field = new ArrayList<>(size);
        particles = new HashSet<>();

        for (int i = 0 ; i < field.size(); i++)
            field.add(new HashSet<>());
    }

    public int toRegionIndex (Particle p) {
        return toRegionIndex((int) p.getX(), (int) p.getY());
    }

    public int toRegionIndex (int x, int y) {
        return (x / squareSideLength) + (y / squareSideLength) * sqX;
    }

    public Rectangle findByCoords (int x, int y) {
        return toCoords(toRegionIndex(x, y));
    }

    public Rectangle toCoords (int index) {
        int x = index % sqX;
        int y = index / sqX;
        return new Rectangle(x, y, x + squareSideLength, y + squareSideLength);
    }
}
