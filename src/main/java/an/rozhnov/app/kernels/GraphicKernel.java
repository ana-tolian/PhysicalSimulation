package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.entity.builders.ParticleDirector;
import an.rozhnov.app.kernels.etc.Camera;
import an.rozhnov.app.kernels.etc.PredefinedColors;
import an.rozhnov.app.kernels.etc.RegionMap;
import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import static an.rozhnov.appState.PreInitialisedParameters.*;
import static an.rozhnov.appState.currentState.AppGlobalState.drawField;
import static an.rozhnov.appState.currentState.AppGlobalState.particleBrush;


public class GraphicKernel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final Camera camera;
    private final MainKernel mainKernel;
    private final RegionMap regionMap;
    private final HashSet<Particle> particles;

    private boolean drag = false;
    private double mx1;
    private double mx2;
    private double my1;
    private double my2;


    public GraphicKernel (MainKernel mainKernel, RegionMap regionMap) {
        setPreferredSize(new Dimension(SIM_WIDTH, SIM_HEIGHT));
        setMaximumSize(new Dimension(SIM_WIDTH, SIM_HEIGHT));

        this.camera = new Camera();
        this.mainKernel = mainKernel;
        this.regionMap = regionMap;
        this.particles = mainKernel.regionMap.getParticles();

        setFocusable(true);
        requestFocus();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }


    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        setupGraphics(g2);
        drawPanel(g2);
        drawGrid(g2);
        drawInfo(g2);

        for (Particle p : particles) {
            drawVector(g2, p);
            drawField(g2, p);

//            g2.drawLine((int)p.getX(), (int)p.getY(), (int) (p.rx + p.getX()), (int) (p.ry + p.getY()));
//            g2.setColor(Color.CYAN);
//            g2.drawString("" + mainKernel.regionMap.toRegionIndex((int) p.getX(), (int) p.getY()), (int) p.getX(), (int) p.getY() - 10);
            camera.draw(g2, p);
        }
        if (AppGlobalState.drawingMode != DrawingMode.BRUSH && drag) {
            predrawShape(g2);
        }
    }

    private void setupGraphics (Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    private void drawPanel (Graphics2D g2) {
        g2.setColor(PredefinedColors.SIM_PANEL_BACKGROUND);
        g2.fillRect(0, 0, SIM_WIDTH, SIM_HEIGHT);
    }

    private void drawInfo (Graphics2D g2) {
        g2.setColor(PredefinedColors.TEXT_INFO_COLOR);
        g2.setFont(new Font("Courier New", Font.PLAIN, 15));
        g2.drawString("Current sim speed: " + ((AppGlobalState.paused) ?  "PAUSED" : AppGlobalState.simSpeed), 10, 20);
        g2.drawString("Particles: " + particles.size(), 10, 35);
    }

    private void drawVector (Graphics2D g2, Particle p) {
        if (!AppGlobalState.drawVectors)
            return;
        g2.setColor(PredefinedColors.VECTOR_COLOR);
        g2.drawLine((int) p.getX(), (int) p.getY(), (int) (2*p.getVectors().v.x + p.getX()), (int) (2*p.getVectors().v.y + p.getY()));
    }

    private void drawGrid (Graphics2D g2) {
        if (!AppGlobalState.drawGrid)
            return;

        g2.setColor(PredefinedColors.GRID_COLOR);

        for (int i = regionMap.getSquareSideLength(); i < SIM_WIDTH; i += regionMap.getSquareSideLength())
            g2.drawLine(i, 0, i, SIM_HEIGHT);

        for (int i = regionMap.getSquareSideLength(); i < SIM_HEIGHT; i += regionMap.getSquareSideLength())
            g2.drawLine(0, i, SIM_WIDTH, i);
    }

    private void drawField (Graphics2D g2, Particle p) {
        if (!AppGlobalState.drawField)
            return;

        double f;
        for (int i = 1; i <= 8; i++) {
            f = p.calculateLennardJones(i);

            if (f < 0)
                g2.setColor(PredefinedColors.NEGATIVE_FIELD_COLOR);
            else if (f == 0)
                g2.setColor(PredefinedColors.NEUTRAL_FIELD_COLOR);
            else
                g2.setColor(PredefinedColors.POSITIVE_FIELD_COLOR);
        g2.drawOval((int) (p.getX() - p.getRadius() - i / 2), (int) (p.getY() - p.getRadius() - i / 2), (int) (p.getRadius() + i), (int) (p.getRadius() + i));

        }
    }

    private void predrawShape (Graphics2D g) {
        g.setColor(particleBrush.getPhyParams().getColor());

        if (AppGlobalState.drawingMode == DrawingMode.FILLED_RECTANGLE) {
            g.fillRect((int) mx1, (int) my1, (int) (mx2 - mx1), (int) (my2 - my1));
        } else if (AppGlobalState.drawingMode == DrawingMode.RECTANGLE) {
            g.drawRect((int) mx1, (int) my1, (int) (mx2 - mx1), (int) (my2 - my1));
        } else if (AppGlobalState.drawingMode == DrawingMode.FILLED_CIRCLE) {
            g.fillOval((int) mx1, (int) my1, (int) (mx2 - mx1), (int) (my2 - my1));
        } else if (AppGlobalState.drawingMode == DrawingMode.CIRCLE) {
            g.drawOval((int) mx1, (int) my1, (int) (mx2 - mx1), (int) (my2 - my1));
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        mx1 = e.getX();
        my1 = e.getY();

        if (AppGlobalState.drawingMode == DrawingMode.BRUSH) {
            Particle p = new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, mx1, my1);
            mainKernel.addNewParticle(p);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        drag = true;
        mx2 = e.getX();
        my2 = e.getY();

        if (AppGlobalState.drawingMode == DrawingMode.BRUSH) {
            Particle p = new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, mx2, my2);
            mainKernel.addNewParticle(p);

        } else if (AppGlobalState.drawingMode == DrawingMode.FILLED_RECTANGLE) {
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        AppGlobalState.mousePointer.x = e.getX() + 1;
        AppGlobalState.mousePointer.y = e.getY() + 1;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        drag = false;

        if (AppGlobalState.drawingMode == DrawingMode.FILLED_RECTANGLE) {
            mx2 = e.getX();
            my2 = e.getY();

            for (int x = (int) mx1; x <= mx2; x++)
                for (int y = (int) my1; y <= my2; y++) {
                    Particle p = new ParticleDirector().createParticleFromBrush(AppGlobalState.particleBrush, x, y);
                    mainKernel.addNewParticle(p);
                }

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (-e.getWheelRotation() > 0) {
            camera.setScale(1.0);
        } else if (-e.getWheelRotation() < 0) {
            camera.setScale(-1.0);
        }
    }
}

