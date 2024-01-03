package an.rozhnov.app.kernels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.gui.panels.MainPanel;
import an.rozhnov.app.kernels.etc.RegionMap;
import an.rozhnov.appState.PreInitialisedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.util.ArrayDeque;

public class MainKernel implements Runnable {

    private final MainPanel mainPanel;
    private final GraphicKernel graphicKernel;
    private final MotionKernel motionKernel;

    private final ArrayDeque<Particle> addQueue;
    public final RegionMap regionMap;


    public MainKernel (MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        addQueue = new ArrayDeque<>();
        regionMap = new RegionMap();

        graphicKernel = new GraphicKernel(this, regionMap);
        motionKernel = new MotionKernel(this);

        Thread t = new Thread(this);
        t.start();
    }


    public void run () {
        while (true) {
            long a = System.currentTimeMillis();
            sleep(1);
            addNewParticles();
            mainPanel.setBottomPanelInfoLabel((onPanel() ? regionMap.getObservedParticle() : null));
            graphicKernel.repaint();

            if (AppGlobalState.clearSheduled)
                regionMap.clear();

            if (AppGlobalState.paused)
                continue;

            motionKernel.performImpact();
            motionKernel.moveAll();
            System.out.println(1000/(System.currentTimeMillis() - a));
        }
    }

    private void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewParticle (Particle p) {
        addQueue.add(p);
    }

    private void addNewParticles () {
        for (int i = 0; i < addQueue.size(); i++) {
            Particle p = addQueue.pollFirst();
            regionMap.add(p);
        }
    }

    private boolean onPanel () {
        return AppGlobalState.mousePointer.x >= 0 && AppGlobalState.mousePointer.x < PreInitialisedParameters.SIM_WIDTH
                && AppGlobalState.mousePointer.y >= 0 && AppGlobalState.mousePointer.y < PreInitialisedParameters.SIM_HEIGHT;
    }

    public GraphicKernel getVisualization() {
        return graphicKernel;
    }
}
