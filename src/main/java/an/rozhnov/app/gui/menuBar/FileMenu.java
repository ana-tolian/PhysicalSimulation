package an.rozhnov.app.gui.menuBar;

import javax.swing.*;

public class FileMenu extends JMenu {

    public FileMenu() {
        super("Файл");

        JMenuItem item1 = new JMenuItem("Открыть");
        JMenuItem item2 = new JMenuItem("Сохранить");

        add(item1);
        addSeparator();
        add(item2);
    }


}
