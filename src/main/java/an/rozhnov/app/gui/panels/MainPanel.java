package an.rozhnov.app.gui.panels;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.app.gui.panels.bottomPanel.BottomPanel;
import an.rozhnov.app.gui.panels.rightPanel.RightPanel;
import an.rozhnov.app.gui.panels.upperPanel.UpperPanel;
import an.rozhnov.app.kernels.MainKernel;
import an.rozhnov.app.kernels.GraphicKernel;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.awt.*;

public class MainPanel extends NPanel {

    private UpperPanel upperPanel;
    private BottomPanel bottomPanel;
    private RightPanel rightPanel;
    private MainKernel mainKernel;
    private GraphicKernel visualization;


    public MainPanel () {
        this.setLayout(new BorderLayout());

        upperPanel = new UpperPanel();

        bottomPanel = new BottomPanel();

        rightPanel = new RightPanel();

        mainKernel = new MainKernel(this);
        visualization = mainKernel.getVisualization();

        this.add("North", upperPanel);
        this.add("Center", visualization);
        this.add("East", rightPanel);
        this.add("South", bottomPanel);
    }


    public void setBottomPanelInfoLabel (Particle p) {
        if (p != null && p.getLabel().getType() != null)
            bottomPanel.setInfo(p.getLabel() + " " + p.getVectors() + " " + p.getPhyParams() + " " + p.getPotential());
        else bottomPanel.setInfo(AppGlobalState.mousePointer.x, AppGlobalState.mousePointer.y);
    }
}
