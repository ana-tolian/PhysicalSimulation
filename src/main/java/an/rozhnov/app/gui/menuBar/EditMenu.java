package an.rozhnov.app.gui.menuBar;

import an.rozhnov.appState.currentState.AppGlobalState;
import an.rozhnov.appState.currentState.DrawingMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenu extends JMenu {

    public EditMenu() {
        super("Рисование");

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

        add(item1);
        add(item2);
        add(item3);
        add(item4);
        addSeparator();
        add(item5);
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
}
