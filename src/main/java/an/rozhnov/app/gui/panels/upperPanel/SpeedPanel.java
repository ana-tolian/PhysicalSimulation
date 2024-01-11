package an.rozhnov.app.gui.panels.upperPanel;

import an.rozhnov.app.gui.custom.NButton;
import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.SpeedMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpeedPanel extends NPanel implements ActionListener {

    private final NButton pauseButton;
    private final NButton speed0;
    private final NButton speed1;
    private final NButton speed2;
    private final NButton speed3;


    public SpeedPanel () {
        this.setLayout(new FlowLayout());

        pauseButton = new NButton("||");
        pauseButton.addActionListener(this);
        pauseButton.setActionCommand("pause");

        speed0 = new NButton("1");
        pauseButton.setActionCommand("0");
        speed0.addActionListener(this);

        speed1 = new NButton("2");
        pauseButton.setActionCommand("1");
        speed1.addActionListener(this);

        speed2 = new NButton("3");
        pauseButton.setActionCommand("2");
        speed2.addActionListener(this);

        speed3 = new NButton("4");
        pauseButton.setActionCommand("3");
        speed3.addActionListener(this);

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
