package an.rozhnov.app.gui.panels.upperPanel;

import an.rozhnov.app.gui.custom.NButton;
import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static an.rozhnov.appState.PredefinedParameters.UPPER_TOOL_PANEL_BACKGROUND;

public class ToolsPanel extends NPanel implements ActionListener {

    private final NButton fillRectToolButton;
    private final NButton rectToolButton;
    private final NButton fillCircleToolButton;
    private final NButton circleToolButton;
    private final NButton brushToolButton;

    public ToolsPanel () {
        this.setLayout(new FlowLayout());
        this.setBackground(UPPER_TOOL_PANEL_BACKGROUND);

        fillRectToolButton = new NButton("F.Rect");
        fillRectToolButton.setActionCommand("F.Rect");
        fillRectToolButton.addActionListener(this);
        fillRectToolButton.setBackground(new Color(113, 208, 168));

        rectToolButton = new NButton("Rect");
        rectToolButton.setActionCommand("Rect");
        rectToolButton.addActionListener(this);
        rectToolButton.setBackground(new Color(113, 208, 168));

        fillCircleToolButton = new NButton("F.Circle");
        fillCircleToolButton.setActionCommand("F.Circle");
        fillCircleToolButton.addActionListener(this);
        fillCircleToolButton.setBackground(new Color(113, 208, 168));

        circleToolButton = new NButton("Circle");
        circleToolButton.setActionCommand("Circle");
        circleToolButton.addActionListener(this);
        circleToolButton.setBackground(new Color(113, 208, 168));

        brushToolButton = new NButton("Brush");
        brushToolButton.setActionCommand("Brush");
        brushToolButton.addActionListener(this);
        brushToolButton.setBackground(new Color(113, 208, 168));

        this.add(fillRectToolButton);
        this.add(rectToolButton);
        this.add(fillCircleToolButton);
        this.add(circleToolButton);
        this.add(brushToolButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Brush"))
            AppGlobalState.drawingMode = DrawingMode.BRUSH;

        else if (e.getActionCommand().equals("F.Rect"))
            AppGlobalState.drawingMode = DrawingMode.FILLED_RECTANGLE;

        else if (e.getActionCommand().equals("Rect"))
            AppGlobalState.drawingMode = DrawingMode.RECTANGLE;

        else if (e.getActionCommand().equals("F.Circle"))
            AppGlobalState.drawingMode = DrawingMode.FILLED_CIRCLE;

        else if (e.getActionCommand().equals("Circle"))
            AppGlobalState.drawingMode = DrawingMode.CIRCLE;
    }
}
