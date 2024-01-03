package an.rozhnov.app.gui.panels.upperPanel;

import an.rozhnov.app.gui.custom.NButton;
import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsPanel extends NPanel implements ActionListener {

    private final NButton fillRectToolButton;
    private final NButton rectToolButton;
    private final NButton brushToolButton;

    public ToolsPanel () {
        this.setLayout(new FlowLayout());

        fillRectToolButton = new NButton("F.Rect");
        fillRectToolButton.setActionCommand("F.Rect");
        fillRectToolButton.addActionListener(this);

        rectToolButton = new NButton("Rect");
        rectToolButton.setActionCommand("F.Rect");
        rectToolButton.addActionListener(this);

        brushToolButton = new NButton("Brush");
        brushToolButton.setActionCommand("Brush");
        brushToolButton.addActionListener(this);

        this.add(fillRectToolButton);
        this.add(rectToolButton);
        this.add(brushToolButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Brush"))
            AppGlobalState.drawingMode = DrawingMode.BRUSH;

        else if (e.getActionCommand().equals("F.Rect"))
            AppGlobalState.drawingMode = DrawingMode.FILLED_RECTANGLE;

        else if (e.getActionCommand().equals("Circle"))
            AppGlobalState.drawingMode = DrawingMode.FILLED_CIRCLE;
    }
}
