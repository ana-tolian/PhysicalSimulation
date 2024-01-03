package an.rozhnov.app.kernels;

import an.rozhnov.app.gui.panels.MainPanel;
import an.rozhnov.app.kernels.drivers.DrawingDriver;
import an.rozhnov.app.kernels.etc.QueueControl;
import an.rozhnov.app.kernels.etc.RegionMap;
import an.rozhnov.appState.PredefinedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;


public class MainKernel implements Runnable {

    private final MainPanel mainPanel;
    private final GraphicKernel graphicKernel;
    private final MotionKernel motionKernel;

    private final QueueControl queue;
    private final DrawingDriver drawingDriver;
    public final RegionMap regionMap;


    public MainKernel (MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        regionMap = new RegionMap();
        queue = new QueueControl(regionMap);
        drawingDriver = new DrawingDriver(queue);

        graphicKernel = new GraphicKernel(drawingDriver, regionMap);
        motionKernel = new MotionKernel(this);

        Thread t = new Thread(this);
        t.start();
    }


    public void run () {
        while (true) {
            long a = System.currentTimeMillis();
            sleep(1);
            queue.addNewParticles();
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

    private boolean onPanel () {
        return AppGlobalState.mousePointer.x >= 0 && AppGlobalState.mousePointer.x < PredefinedParameters.SIM_WIDTH
                && AppGlobalState.mousePointer.y >= 0 && AppGlobalState.mousePointer.y < PredefinedParameters.SIM_HEIGHT;
    }

    public GraphicKernel getVisualization() {
        return graphicKernel;
    }
}
