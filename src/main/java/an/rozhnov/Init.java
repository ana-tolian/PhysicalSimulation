package an.rozhnov;

import an.rozhnov.app.gui.menuBar.ProgramMenuBar;
import an.rozhnov.app.gui.panels.MainPanel;
import an.rozhnov.app.io.ParticlePropertyLoader;

import javax.swing.*;

public class Init {

    private static JFrame frame;

    public Init() {
        createUI();
    }

    private static void createUI () {
        frame = new JFrame ("2D Sym");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        new ParticlePropertyLoader().loadPalette();

        frame.setOpacity(1.0f);
        frame.setJMenuBar(new ProgramMenuBar());
        frame.add(new MainPanel());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable () {
            public void run () {
                new Init();
            }
        });

    }

}
