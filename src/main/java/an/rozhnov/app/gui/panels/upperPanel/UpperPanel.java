package an.rozhnov.app.gui.panels.upperPanel;

import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.appState.PredefinedParameters;

import java.awt.*;

import static an.rozhnov.appState.PredefinedParameters.UPPER_PANEL_BACKGROUND;


public class UpperPanel extends NPanel {

    private final NPanel null1;
    private final NPanel null2;
    private final SpeedPanel speedPanel;
    private final ToolsPanel toolsPanel;

    public UpperPanel () {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(PredefinedParameters.UPPER_PANEL_BACKGROUND);

        speedPanel = new SpeedPanel();
        toolsPanel = new ToolsPanel();

        null1 = new NPanel();
        null1.setBackground(UPPER_PANEL_BACKGROUND);
        null2 = new NPanel();
        null2.setBackground(UPPER_PANEL_BACKGROUND);

        this.add(null1);
        this.add(toolsPanel);
        this.add(null2);
        this.add(speedPanel);
    }
}
