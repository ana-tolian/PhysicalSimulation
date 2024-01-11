package an.rozhnov.app.gui.menuBar;

import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.SpeedMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationMenu extends JMenu {

    public SimulationMenu () {
        super("Симуляция");

        JMenuItem item1 = new JMenuItem("Приостановить");
        item1.setActionCommand("pause");
        item1.setAccelerator(KeyStroke.getKeyStroke(' '));
        item1.addActionListener(new SimMenuActionPerform());

        JMenuItem item2 = new JMenuItem("Медленно");
        item2.setActionCommand("1");
        item2.setAccelerator(KeyStroke.getKeyStroke('1'));
        item2.addActionListener(new SimMenuActionPerform());

        JMenuItem item3 = new JMenuItem("Нормально");
        item3.setActionCommand("2");
        item3.setAccelerator(KeyStroke.getKeyStroke('2'));
        item3.addActionListener(new SimMenuActionPerform());

        JMenuItem item4 = new JMenuItem("Быстро");
        item4.setActionCommand("3");
        item4.setAccelerator(KeyStroke.getKeyStroke('3'));
        item4.addActionListener(new SimMenuActionPerform());

        JMenuItem item5 = new JMenuItem("Очень быстро");
        item5.setActionCommand("4");
        item5.setAccelerator(KeyStroke.getKeyStroke('4'));
        item5.addActionListener(new SimMenuActionPerform());

        JMenuItem item6 = new JMenuItem("Очистить симуляцию");
        item6.setActionCommand("clear");
        item6.addActionListener(new SimMenuActionPerform());

        JCheckBoxMenuItem item7 = new JCheckBoxMenuItem("Включить гравитацию");
        item7.setSelected(false);
        item7.setActionCommand("gravity");
        item7.addActionListener(new SimMenuActionPerform());

        add(item1);
        addSeparator();
        add(item2);
        add(item3);
        add(item4);
        add(item5);
        addSeparator();
        add(item6);
        addSeparator();
        add(item7);
    }

    private class SimMenuActionPerform implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("pause"))
                AppGlobalState.paused = !AppGlobalState.paused;
            if (e.getActionCommand().equals("1"))
                AppGlobalState.speedMode = SpeedMode.SLOW;
            if (e.getActionCommand().equals("2"))
                AppGlobalState.speedMode = SpeedMode.MEDIUM;
            if (e.getActionCommand().equals("3"))
                AppGlobalState.speedMode = SpeedMode.FAST;
            if (e.getActionCommand().equals("4"))
                AppGlobalState.speedMode = SpeedMode.VERY_FAST;
            if (e.getActionCommand().equals("clear"))
                AppGlobalState.clearSheduled = true;
            if (e.getActionCommand().equals("gravity"))
                AppGlobalState.gravityEnabled = !AppGlobalState.gravityEnabled;
        }
    }
}
