package an.rozhnov.app.kernels.drivers;

import an.rozhnov.app.entity.builders.ParticleDirector;
import an.rozhnov.app.kernels.etc.QueueControl;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.awt.*;
import java.awt.geom.Ellipse2D;


public class DrawingDriver {

    private QueueControl queue;

    public DrawingDriver (QueueControl queue) {
        this.queue = queue;
    }


    public void fillRectangle (int x1, int y1, int x2, int y2) {
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x, y));
    }

    public void rectangle (int x1, int y1, int x2, int y2) {
        for (int x = x1; x <= x2; x++) {
            queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x, y1));
            queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x, y2));
        }

        for (int y = y1 + 1; y < y2; y++) {
            queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x1, y));
            queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x2, y));
        }

    }

    public void fillOval (int x1, int y1, int x2, int y2) {
        Ellipse2D oval = new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1);
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                if (oval.contains(x, y))
                    queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x, y));
    }

    public void oval (int x1, int y1, int r) {
        double x, y;
        double minAngle = Math.acos(1 - 1/r);

        for (double angle = 0; angle <= 360; angle += minAngle) {
            x = r * Math.cos(angle);
            y = r * Math.sin(angle);
            queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x1 + x, y1 + y));
        }
    }

    public void draw (int x1, int y1) {
        queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x1, y1));
    }
}
