package an.rozhnov.app.gui.custom;

import javax.swing.*;

public class NButton extends JButton {

    public NButton () {
        setFocusable(false);
    }

    public NButton (String s) {
        super(s);
        setFocusable(false);
    }
}
