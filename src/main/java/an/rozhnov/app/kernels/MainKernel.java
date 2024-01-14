package an.rozhnov.app.kernels;

import an.rozhnov.app.gui.panels.MainPanel;
import an.rozhnov.app.kernels.drivers.FPSController;
import an.rozhnov.app.kernels.drivers.drawing.DrawingDriver;
import an.rozhnov.app.kernels.drivers.particle.QueueControl;
import an.rozhnov.app.kernels.drivers.particle.RegionMap;
import an.rozhnov.appState.PredefinedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;


public class MainKernel implements Runnable {

    private final MainPanel mainPanel;
    private final GraphicKernel graphicKernel;
    private final MotionKernel motionKernel;

    private final QueueControl queueControl;
    private final DrawingDriver drawingDriver;
    public final RegionMap regionMap;


    public MainKernel (MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        regionMap = new RegionMap();
        queueControl = new QueueControl(regionMap);
        drawingDriver = new DrawingDriver(queueControl);

        graphicKernel = new GraphicKernel(drawingDriver, regionMap);
        motionKernel = new MotionKernel(this);

        Thread t = new Thread(this);
        t.start();
    }


    public void run () {
        while (true) {
            FPSController.startMeasuring();
            sleep(FPSController.getSleepingTime());
            queueControl.addNewParticles();
            mainPanel.setBottomPanelInfoLabel((onPanel() ? regionMap.getObservedParticle() : null));
            graphicKernel.repaint();

            if (AppGlobalState.clearSheduled)
                regionMap.clear();

            if (AppGlobalState.paused)
                continue;

            motionKernel.performImpact();
            motionKernel.moveAll();
            FPSController.stopMeasuring();
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
        return AppGlobalState.mousePointer.x >= 0 && AppGlobalState.mousePointer.x < PredefinedParameters.REAL_SIM_WIDTH
                && AppGlobalState.mousePointer.y >= 0 && AppGlobalState.mousePointer.y < PredefinedParameters.REAL_SIM_HEIGHT;
    }

    public GraphicKernel getVisualization() {
        return graphicKernel;
    }
}
