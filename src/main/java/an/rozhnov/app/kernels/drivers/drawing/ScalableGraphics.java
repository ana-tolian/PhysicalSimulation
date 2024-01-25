package an.rozhnov.app.kernels.drivers.drawing;

import an.rozhnov.appState.PredefinedParameters;

import java.awt.*;

public class ScalableGraphics {

    private final static int SCALE = 4;
    public final static int SHEAR = (int) (Math.log(SCALE) / Math.log(2));
    public final static int LOGICAL_WIDTH = PredefinedParameters.REAL_SIM_WIDTH / SCALE;
    public final static int LOGICAL_HEIGHT = PredefinedParameters.REAL_SIM_HEIGHT / SCALE;

    private Graphics2D g2;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int w;
    private int h;


    public ScalableGraphics () {}

    public void setGraphics (Graphics2D g2) {
        this.g2 = g2;
        this.g2.setStroke(new BasicStroke(SCALE));
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    public void setColor (Color color) {
        g2.setColor(color);
    }

    public void setFont (Font font) {
        g2.setFont(font);
    }

    public void drawString (String s, int x1, int y1) {
        scaleCoords(x1, y1, 0, 0, 0, 0);
        g2.drawString(s, x1, y1);
    }

    public void drawLine (int x1, int y1, int x2, int y2) {
        scaleCoords(x1, y1, x2, y2,0, 0);
        g2.drawLine(this.x1, this.y1, this.x2, this.y2);
    }

    public void drawRect (int x1, int y1, int w, int h) {
        scaleCoords(x1, y1, 0, 0, w, h);
        g2.drawRect(this.x1, this.y1, this.w, this.h);
    }

    public void fillRect (int x1, int y1, int w, int h) {
        scaleCoords(x1, y1, 0, 0, w, h);
        g2.fillRect(this.x1, this.y1, this.w, this.h);
    }

    public void drawOval (int x1, int y1, int w, int h) {
        scaleCoords(x1, y1, 0, 0, w, h);
        g2.drawOval(this.x1, this.y1, this.w, this.h);
    }

    public void fillOval (int x1, int y1, int w, int h) {
        scaleCoords(x1, y1, 0, 0, w, h);
        g2.fillOval(this.x1, this.y1, this.w, this.h);
    }

    // For particle smooth drawing
    public void fillOval (double x1, double y1, int w, int h) {
        x1 *= SCALE;
        y1 *= SCALE;
        w *= SCALE;
        h *= SCALE;
        g2.fillOval((int) x1, (int) y1, w, h);
    }

    private void scaleCoords (int x1, int y1, int x2, int y2, int w, int h) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.w = w;
        this.h = h;
        scale();
    }

    private void scale () {
        this.x1 = toRealCoord(x1);
        this.y1 = toRealCoord(y1);
        this.x2 = toRealCoord(x2);
        this.y2 = toRealCoord(y2);
        this.w = toRealCoord(w);
        this.h = toRealCoord(h);
    }

    public static int toRealCoord (int c) {
        return c << SHEAR;
    }

    public static int toLogicalCoord (int c) {
        return c >> SHEAR;
    }
}
