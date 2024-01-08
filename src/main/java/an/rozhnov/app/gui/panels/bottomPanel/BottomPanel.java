package an.rozhnov.app.gui.panels.bottomPanel;

import an.rozhnov.app.gui.custom.NPanel;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends NPanel {

    private String info = "x: %d y: %d";
    private JLabel infoLabel;

    public BottomPanel () {
        setPreferredSize(new Dimension(400, 30));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        add(infoLabel);
    }

    public void setInfo (int x, int y) {
        infoLabel.setText(String.format(this.info, x, y));
    }

    public void setInfo (String info) {
        infoLabel.setText(info);
    }

}
