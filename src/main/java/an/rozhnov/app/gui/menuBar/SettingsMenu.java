package an.rozhnov.app.gui.menuBar;

import an.rozhnov.appState.currentState.AppGlobalState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu extends JMenu {

    public SettingsMenu () {
        super("Настройки отображения");

        JCheckBoxMenuItem item1 = new JCheckBoxMenuItem("Рисовать векторы");
        item1.setSelected(false);
        item1.setActionCommand("vectors");
        item1.addActionListener(new SettMenuActionPerform());

        JCheckBoxMenuItem item2 = new JCheckBoxMenuItem("Сетка");
        item2.setSelected(false);
        item2.setActionCommand("grid");
        item2.addActionListener(new SettMenuActionPerform());

        JCheckBoxMenuItem item3 = new JCheckBoxMenuItem("Поле");
        item3.setSelected(false);
        item3.setActionCommand("field");
        item3.addActionListener(new SettMenuActionPerform());

        add(item1);
        add(item3);
        add(item2);
    }

    private class SettMenuActionPerform implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("vectors"))
                AppGlobalState.drawVectors = !AppGlobalState.drawVectors;
            if (e.getActionCommand().equals("grid"))
                AppGlobalState.drawGrid = !AppGlobalState.drawGrid;
            if (e.getActionCommand().equals("field"))
                AppGlobalState.drawField= !AppGlobalState.drawField;
        }
    }
}
