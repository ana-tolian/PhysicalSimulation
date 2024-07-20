package an.rozhnov.app.gui.panels.upperPanel;

import an.rozhnov.app.gui.custom.NButton;
import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.SpeedMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static an.rozhnov.appState.PredefinedParameters.UPPER_TOOL_PANEL_BACKGROUND;

public class SpeedPanel extends NPanel implements ActionListener {

    private final NButton pauseButton;
    private final NButton speed0;
    private final NButton speed1;
    private final NButton speed2;
    private final NButton speed3;


    public SpeedPanel () {
        this.setLayout(new FlowLayout());
        this.setBackground(UPPER_TOOL_PANEL_BACKGROUND);

        pauseButton = new NButton("||");
        pauseButton.addActionListener(this);
        pauseButton.setActionCommand("pause");
        pauseButton.setBackground(new Color(113, 208, 168));
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 14));
//        pauseButton.setForeground(new Color(155, 70, 108));

        speed0 = new NButton(">");
        speed0.setActionCommand("0");
        speed0.addActionListener(this);
        speed0.setBackground(new Color(113, 208, 168));
        speed0.setFont(new Font("Courier New", Font.BOLD, 14));

        speed1 = new NButton(">>");
        speed1.setActionCommand("1");
        speed1.addActionListener(this);
        speed1.setBackground(new Color(113, 208, 168));
        speed1.setFont(new Font("Courier New", Font.BOLD, 14));

        speed2 = new NButton(">>>");
        speed2.setActionCommand("2");
        speed2.addActionListener(this);
        speed2.setBackground(new Color(113, 208, 168));
        speed2.setFont(new Font("Courier New", Font.BOLD, 14));

        speed3 = new NButton(">>>>");
        speed3.setActionCommand("3");
        speed3.addActionListener(this);
        speed3.setBackground(new Color(113, 208, 168));
        speed3.setFont(new Font("Courier New", Font.BOLD, 14));

        this.add(pauseButton);
        this.add(speed0);
        this.add(speed1);
        this.add(speed2);
        this.add(speed3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("pause")) {
            AppGlobalState.paused = !AppGlobalState.paused;

        } else {
            AppGlobalState.paused = false;
            AppGlobalState.speedMode = SpeedMode.dt(Integer.parseInt(e.getActionCommand()) - 1);
        }
    }

}
