package an.rozhnov.appState.currentState;

import an.rozhnov.app.entity.Particle;

import java.awt.*;

public class AppGlobalState {

    public static int SUBSTEPS = 10;
    public static SpeedMode speedMode = SpeedMode.SLOW;
    public static boolean paused = true;
    public static boolean clearSheduled = false;

    public static DrawingMode drawingMode = DrawingMode.BRUSH;
    public static Point mousePointer = new Point(0,0);
    public static Particle particleBrush;

    public static boolean selectionEnabled = false;
    public static SelectionMode selectionMode = SelectionMode.NORMAL;

    public static boolean drawVectors = false;
    public static boolean drawGrid = false;
    public static boolean drawField = false;
    public static boolean gravityEnabled = false;
}
