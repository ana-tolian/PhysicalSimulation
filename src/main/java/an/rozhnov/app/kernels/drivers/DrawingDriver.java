package an.rozhnov.app.kernels.drivers;

import an.rozhnov.app.entity.builders.ParticleDirector;
import an.rozhnov.app.kernels.etc.QueueControl;
import an.rozhnov.appState.currentState.AppGlobalState;


public class DrawingDriver {

    private QueueControl queue;

    public DrawingDriver (QueueControl queue) {
        this.queue = queue;
    }


    public void rectangle (int x1, int y1, int x2, int y2) {
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x, y));
    }

//    public void oval (int x1, int y1, int x2, int y2) {
//        for (int x = x1; x <= x2; x++)
//            for (int y = y1; y <= y2; y++)
//                queue.add(new ParticleDirector().createParticleFromBrush(Parameters.particleBrush, x, y));
//    }

    public void draw (int x1, int y1) {
        queue.add(new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x1, y1));
    }
}
