package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.kernels.drivers.FPSController;
import an.rozhnov.app.kernels.drivers.drawing.DrawingDriver;
import an.rozhnov.app.kernels.drivers.drawing.PredefinedColors;
import an.rozhnov.app.kernels.drivers.particle.QueueControl;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;
import an.rozhnov.app.kernels.drivers.drawing.ScalableGraphics;
import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;

import static an.rozhnov.appState.PredefinedParameters.*;
import static an.rozhnov.appState.currentState.AppGlobalState.particleBrush;


public class GraphicKernel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final RegionMap regionMap;
    private final DrawingDriver drawingDriver;
    private final ScalableGraphics scalableGraphics;
    private final HashSet<Particle> particles;

    private boolean drag = false;
    private int mx1;
    private int mx2;
    private int my1;
    private int my2;


    public GraphicKernel (DrawingDriver drawingDriver, RegionMap regionMap) {
        setPreferredSize(new Dimension(REAL_SIM_WIDTH, REAL_SIM_HEIGHT));
        setMaximumSize(new Dimension(REAL_SIM_WIDTH, REAL_SIM_HEIGHT));

        this.regionMap = regionMap;
        this.particles = regionMap.getParticles();
        this.drawingDriver = drawingDriver;
        this.scalableGraphics = new ScalableGraphics();

        setFocusable(true);
        requestFocus();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }


    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        scalableGraphics.setGraphics((Graphics2D) g);

        drawPanel();
        drawGrid();
        drawInfo();

        Iterator<Particle> iterator = particles.iterator();
        Particle p;

        while (iterator.hasNext()) {
            p = iterator.next();

            drawVector(p);
            drawField(p);
            p.draw(scalableGraphics);
        }
        if (AppGlobalState.drawingMode != DrawingMode.BRUSH && drag) {
            pre_drawShape();
        }
    }

    private void drawPanel () {
        scalableGraphics.setColor(PredefinedColors.SIM_PANEL_BACKGROUND);
        scalableGraphics.fillRect(0, 0, scalableGraphics.LOGICAL_WIDTH, scalableGraphics.LOGICAL_HEIGHT);
    }

    private void drawInfo () {
        scalableGraphics.setColor(PredefinedColors.TEXT_INFO_COLOR);
        scalableGraphics.setFont(new Font("Courier New", Font.PLAIN, 15));
        scalableGraphics.drawString("Current sim speed: " + ((AppGlobalState.paused) ?  "PAUSED" : AppGlobalState.speedMode), 10, 25);
        scalableGraphics.drawString("Particles: " + particles.size(), 10, 40);
        scalableGraphics.drawString("FPS: " + FPSController.cycleTime, 10, 55);
    }

    private void drawVector (Particle p) {
        if (!AppGlobalState.drawVectors)
            return;
        scalableGraphics.setColor(PredefinedColors.SPEED_VECTOR_COLOR);
        scalableGraphics.drawLine((int) p.getX() - 1, (int) p.getY() - 1, (int) (2*p.getVectors().v.x + p.getX()), (int) (2*p.getVectors().v.y + p.getY()));
        scalableGraphics.setColor(PredefinedColors.FORCE_VECTOR_COLOR);
        scalableGraphics.drawLine((int) p.getX() - 1, (int) p.getY() - 1, (int) (2*p.getVectors().f.x + p.getX()), (int) (2*p.getVectors().f.y + p.getY()));
    }

    private void drawGrid () {
        if (!AppGlobalState.drawGrid)
            return;

        scalableGraphics.setColor(PredefinedColors.GRID_COLOR);
        int length = scalableGraphics.toRealCoord(regionMap.getSquareSideLength());

        for (int i = length; i < ScalableGraphics.LOGICAL_WIDTH; i += length)
            scalableGraphics.drawLine(i, 0, i, ScalableGraphics.LOGICAL_HEIGHT);

        for (int i = length; i < ScalableGraphics.LOGICAL_HEIGHT; i += length)
            scalableGraphics.drawLine(0, i, ScalableGraphics.LOGICAL_WIDTH, i);
    }

    private void drawField (Particle p) {
        if (!AppGlobalState.drawField)
            return;

        double f;
        for (int i = 1; i < 32; i+=2) {
            f = p.calculateLennardJones(p, i);

            if (f < 0)
                scalableGraphics.setColor(PredefinedColors.NEGATIVE_FIELD_COLOR);
            else if (f == 0)
                scalableGraphics.setColor(PredefinedColors.NEUTRAL_FIELD_COLOR);
            else
                scalableGraphics.setColor(PredefinedColors.POSITIVE_FIELD_COLOR);
            scalableGraphics.drawOval(((int) p.getX()) - p.getRadius() - (i >> 1), ((int) p.getY()) - p.getRadius() - (i >> 1), i, i);

        }
    }

    private void pre_drawShape() {
        scalableGraphics.setColor(particleBrush.getPhyParams().getColor());

        if (AppGlobalState.drawingMode == DrawingMode.FILLED_RECTANGLE) {
            scalableGraphics.fillRect(mx1, my1, (mx2 - mx1), (my2 - my1));
        } else if (AppGlobalState.drawingMode == DrawingMode.RECTANGLE) {
            scalableGraphics.drawRect(mx1, my1, (mx2 - mx1), (my2 - my1));
        } else if (AppGlobalState.drawingMode == DrawingMode.FILLED_CIRCLE) {
            scalableGraphics.fillOval(mx1, my1, (mx2 - mx1), (my2 - my1));
        } else if (AppGlobalState.drawingMode == DrawingMode.CIRCLE) {
            scalableGraphics.drawOval(mx1, my1, (mx2 - mx1), (mx2 - mx1));
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        mx1 = ScalableGraphics.toLogicalCoord(e.getX());
        my1 = ScalableGraphics.toLogicalCoord(e.getY());

        if (AppGlobalState.drawingMode == DrawingMode.BRUSH) {
            drawingDriver.draw(mx1, my1);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        drag = true;
        mx2 = ScalableGraphics.toLogicalCoord(e.getX());
        my2 = ScalableGraphics.toLogicalCoord(e.getY());

        if (AppGlobalState.drawingMode == DrawingMode.BRUSH) {
            drawingDriver.draw(mx2, my2);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        AppGlobalState.mousePointer.x = ScalableGraphics.toLogicalCoord(e.getX()) + 1;
        AppGlobalState.mousePointer.y = ScalableGraphics.toLogicalCoord(e.getY()) + 1;
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
        drag = false;
        mx2 = ScalableGraphics.toLogicalCoord(e.getX());
        my2 = ScalableGraphics.toLogicalCoord(e.getY());

        if (AppGlobalState.drawingMode == DrawingMode.FILLED_RECTANGLE)
            drawingDriver.fillRectangle(mx1, my1, mx2, my2);
        else if (AppGlobalState.drawingMode == DrawingMode.FILLED_CIRCLE)
            drawingDriver.fillCircle(mx1, my1, mx2, my2);
        else if (AppGlobalState.drawingMode == DrawingMode.RECTANGLE)
            drawingDriver.rectangle(mx1, my1, mx2, my2);
        else if (AppGlobalState.drawingMode == DrawingMode.CIRCLE)
            drawingDriver.circle(mx1, my1, mx2 - mx1);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
//        if (-e.getWheelRotation() > 0) {
//            camera.setScale(1.0);
//        } else if (-e.getWheelRotation() < 0) {
//            camera.setScale(-1.0);
//        }
    }
}

