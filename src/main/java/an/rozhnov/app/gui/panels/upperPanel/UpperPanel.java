package an.rozhnov.app.gui.panels.upperPanel;

import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.appState.PredefinedParameters;

import java.awt.*;


public class UpperPanel extends NPanel {

    private final SpeedPanel speedPanel;
    private final ToolsPanel toolsPanel;

    public UpperPanel () {
        this.setLayout(new FlowLayout());
        this.setBackground(PredefinedParameters.UPPER_PANEL_BACKGROUND);

        speedPanel = new SpeedPanel();
        toolsPanel = new ToolsPanel();

        this.add(toolsPanel);
        this.add(speedPanel);
    }
}
