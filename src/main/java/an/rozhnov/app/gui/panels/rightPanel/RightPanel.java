package an.rozhnov.app.gui.panels.rightPanel;


import an.rozhnov.app.gui.custom.NButton;
import an.rozhnov.app.gui.custom.NPanel;
import an.rozhnov.app.io.ParticlePropertyLoader;
import an.rozhnov.appState.PredefinedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class RightPanel extends NPanel implements ActionListener {

    private NButton[] buttons;

    public RightPanel () {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setPreferredSize(new Dimension(25, PredefinedParameters.REAL_SIM_HEIGHT));
        this.setBackground(PredefinedParameters.RIGHT_PANEL_BACKGROUND);

        int i = 0;
        Set<String> keySet = ParticlePropertyLoader.PALETTE.keySet();
        AppGlobalState.particleBrush = ParticlePropertyLoader.PALETTE.get(keySet.toArray()[0]);

        buttons = new NButton[keySet.size()];
        for (String type : keySet) {
            buttons[i] = new NButton(type);
            buttons[i].setBackground(ParticlePropertyLoader.PALETTE.get(type).getPhyParams().getColor());
            buttons[i].setActionCommand(type);
            buttons[i].addActionListener(this);
            this.add(buttons[i]);
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AppGlobalState.particleBrush = ParticlePropertyLoader.PALETTE.get(e.getActionCommand());
    }
}
