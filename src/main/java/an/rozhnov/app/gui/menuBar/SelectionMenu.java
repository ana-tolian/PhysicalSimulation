package an.rozhnov.app.gui.menuBar;

import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;
import an.rozhnov.appState.currentState.SelectionMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionMenu extends JMenu {

    public SelectionMenu () {
        super("Выделить");

        SelectionMenuActionPerform actionListener = new SelectionMenuActionPerform();

        JCheckBoxMenuItem item0 = new JCheckBoxMenuItem("Выделение");
        item0.setSelected(false);
        item0.setActionCommand("Select");
        item0.addActionListener(actionListener);

        JMenuItem item3 = new JMenuItem("Выбрать всё");
        item3.setActionCommand("SelAll");
        item3.addActionListener(actionListener);

        JMenuItem item4 = new JMenuItem("Отменить выделение");
        item4.setActionCommand("CancelSel");
        item4.addActionListener(actionListener);

        JRadioButtonMenuItem item5 = new JRadioButtonMenuItem  ("Обычный");
        item5.setSelected(true);
        item5.setActionCommand("Norm");
        item5.addActionListener(actionListener);

        JRadioButtonMenuItem item6 = new JRadioButtonMenuItem  ("Сложение");
        item6.setActionCommand("Add");
        item6.addActionListener(actionListener);

        JRadioButtonMenuItem item7 = new JRadioButtonMenuItem  ("Вычитание");
        item7.setActionCommand("Substr");
        item7.addActionListener(actionListener);

        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(item5);
        bg2.add(item6);
        bg2.add(item7);

        JMenu selectionMode = new JMenu("Режим выделения");
        selectionMode.add(item5);
        selectionMode.add(item6);
        selectionMode.add(item7);

        add(item0);
        addSeparator();
        add(item3);
        add(item4);
        addSeparator();
        add(selectionMode);
    }

    private class SelectionMenuActionPerform implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Select"))
                AppGlobalState.selectionEnabled = !AppGlobalState.selectionEnabled;

            if (e.getActionCommand().equals("SelAll"))
                ;//

            if (e.getActionCommand().equals("CancelSel"))
                ;//

            if (e.getActionCommand().equals("Norm"))
                AppGlobalState.selectionMode = SelectionMode.NORMAL;

            if (e.getActionCommand().equals("Add"))
                AppGlobalState.selectionMode = SelectionMode.ADDITION;

            if (e.getActionCommand().equals("Substr"))
                AppGlobalState.selectionMode = SelectionMode.SUBTRACTION;
        }
    }
}
