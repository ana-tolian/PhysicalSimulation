package an.rozhnov.app.gui.menuBar;

import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;
import an.rozhnov.appState.currentState.SimSpeed;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ProgramMenuBar extends JMenuBar {

    public ProgramMenuBar () {
        add(createFileMenu());
        add(createEditMenu());
        add(createSelectionMenu());
        add(createSimulationMenu());
        add(createSettingsMenu());
    }

    private JMenu createFileMenu () {
        JMenu file = new JMenu("Файл");
        JMenuItem item1 = new JMenuItem("Открыть");
        JMenuItem item2 = new JMenuItem("Сохранить");

        file.add(item1);
        file.addSeparator();
        file.add(item2);

        return file;
    }

    private JMenu createEditMenu () {
        JMenu edit = new JMenu("Рисование");
        JMenuItem item1 = new JMenuItem("Сплошной прямоуг.");
        item1.setActionCommand("frect");
        item1.addActionListener(new EditMenuActionPerform());

        JMenuItem item2 = new JMenuItem("Прямоугольник");
        item2.setActionCommand("rect");
        item2.addActionListener(new EditMenuActionPerform());

        JMenuItem item3 = new JMenuItem("Сплошная окруж.");
        item3.setActionCommand("fcircle");
        item3.addActionListener(new EditMenuActionPerform());

        JMenuItem item4 = new JMenuItem("Окружность");
        item4.setActionCommand("circle");
        item4.addActionListener(new EditMenuActionPerform());

        JMenuItem item5 = new JMenuItem("Кисть");
        item5.setActionCommand("brush");
        item5.setAccelerator(KeyStroke.getKeyStroke('d'));
        item5.addActionListener(new EditMenuActionPerform());

        edit.add(item1);
        edit.add(item2);
        edit.add(item3);
        edit.add(item4);
        edit.addSeparator();
        edit.add(item5);

        return edit;
    }

    private JMenu createSelectionMenu () {
        JMenu select = new JMenu("Выделить");
        JRadioButtonMenuItem item1 = new JRadioButtonMenuItem("Прямоугольное выделение");
        JRadioButtonMenuItem item2 = new JRadioButtonMenuItem("Свободное выделение");
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(item1);
        bg1.add(item2);

        JMenuItem item3 = new JMenuItem("Выбрать всё");
        JMenuItem item4 = new JMenuItem("Отменить выделение");

        JMenu selectionMode = new JMenu("Режим выделения");
        JRadioButtonMenuItem item5 = new JRadioButtonMenuItem  ("Обычный"); item5.setSelected(true);
        JRadioButtonMenuItem item6 = new JRadioButtonMenuItem  ("Сложение");
        JRadioButtonMenuItem item7 = new JRadioButtonMenuItem  ("Вычитание");
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(item5);
        bg2.add(item6);
        bg2.add(item7);

        selectionMode.add(item5);
        selectionMode.add(item6);
        selectionMode.add(item7);

        select.add(item1);
        select.add(item2);
        select.addSeparator();
        select.add(item3);
        select.add(item4);
        select.addSeparator();
        select.add(selectionMode);

        return select;
    }

    private JMenu createSimulationMenu () {
        JMenu simulation = new JMenu("Симуляция");
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

        simulation.add(item1);
        simulation.addSeparator();
        simulation.add(item2);
        simulation.add(item3);
        simulation.add(item4);
        simulation.add(item5);
        simulation.addSeparator();
        simulation.add(item6);
        simulation.addSeparator();
        simulation.add(item7);

        return simulation;
    }

    private JMenu createSettingsMenu () {
        JMenu settings = new JMenu("Настройки отображения");
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

        settings.add(item1);
        settings.add(item3);
        settings.add(item2);

        return settings;
    }

    private class EditMenuActionPerform implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("brush"))
                AppGlobalState.drawingMode = DrawingMode.BRUSH;
            if (e.getActionCommand().equals("rect"))
                AppGlobalState.drawingMode = DrawingMode.RECTANGLE;
            if (e.getActionCommand().equals("circle"))
                AppGlobalState.drawingMode = DrawingMode.CIRCLE;
            if (e.getActionCommand().equals("frect"))
                AppGlobalState.drawingMode = DrawingMode.FILLED_RECTANGLE;
            if (e.getActionCommand().equals("fcircle"))
                AppGlobalState.drawingMode = DrawingMode.FILLED_CIRCLE;
        }
    }

    private class SimMenuActionPerform implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("pause"))
                AppGlobalState.paused = !AppGlobalState.paused;
            if (e.getActionCommand().equals("1"))
                AppGlobalState.simSpeed = SimSpeed.SLOW;
            if (e.getActionCommand().equals("2"))
                AppGlobalState.simSpeed = SimSpeed.MEDIUM;
            if (e.getActionCommand().equals("3"))
                AppGlobalState.simSpeed = SimSpeed.FAST;
            if (e.getActionCommand().equals("4"))
                AppGlobalState.simSpeed = SimSpeed.VERY_FAST;
            if (e.getActionCommand().equals("clear"))
                AppGlobalState.clearSheduled = true;
            if (e.getActionCommand().equals("gravity"))
                AppGlobalState.gravityEnabled = !AppGlobalState.gravityEnabled;
        }
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
