package an.rozhnov.app.kernels.etc;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.appState.PredefinedParameters;

import java.awt.*;

public class Camera {

    private double scale = 2.0;
    private final int LOGICAL_WIDTH = (int) (PredefinedParameters.SIM_WIDTH / scale);
    private final int LOGICAL_HEIGHT = (int) (PredefinedParameters.SIM_HEIGHT / scale);

    public void draw (Graphics2D g2, Particle p) {
        p.draw(g2, scale);
    }

    public void setScale (double multiplicity) {
        scale += 1.0 * multiplicity;

        if (scale < 1.0)
            scale = 1.0;
    }

    public Point realToLogical (int x, int y) {
        return new Point((int) (x / scale), (int) (y / scale));
    }
}
