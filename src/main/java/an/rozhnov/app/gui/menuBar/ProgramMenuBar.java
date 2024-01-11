package an.rozhnov.app.gui.menuBar;

import javax.swing.*;


public class ProgramMenuBar extends JMenuBar {

    public ProgramMenuBar () {
        add(createFileMenu());
        add(createEditMenu());
        add(createSelectionMenu());
        add(createSimulationMenu());
        add(createSettingsMenu());
    }

    private JMenu createFileMenu () {
        return new FileMenu();
    }

    private JMenu createEditMenu () {
        return new EditMenu();
    }

    private JMenu createSelectionMenu () {
        return new SelectionMenu();
    }

    private JMenu createSimulationMenu () {
        return new SimulationMenu();
    }

    private JMenu createSettingsMenu () {
        return new SettingsMenu();
    }
}
