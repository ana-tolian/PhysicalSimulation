import an.rozhnov.appState.PredefinedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;

public class Test4 {

    static double rx = 10;
    static double ry = 8;

    static double vx = 0;
    static double vy = 2;

    static double ax = 0;
    static double ay = 0;

    static double fx = 0;
    static double fy = 0;


    public static void main(String[] args) {
        double x = 10;
        double y = 10;
        double eps = 1000;
        double rmin = 1;
        double sigma = rmin / Math.pow(2, 1/6);
        double dt = 0.01;

        for (int i = 0; i < 20000; i++) {
            double rxx = x - rx;
            double ryy = y - ry;
//
//            if (Math.abs(rxx) < 10e-5)
//                rxx += 10e-3 * (Math.random() - 0.5);
//            if (Math.abs(ryy) < 10e-5)
//                ryy += 10e-3 * (Math.random() - 0.5);

            double R = rxx * rxx + ryy * ryy;
            double invR = 1 / R;

            double PLJ_p1_to_p2 = calculateLennardJones(sigma, eps, invR);
            applyForces(rxx * PLJ_p1_to_p2, ryy * PLJ_p1_to_p2);
            calculateAccelerationAndVelocity(dt, 1);
            move(dt, 1);

            System.out.println(String.format("%4.3f \t %4.3f \t %4.3f \t %6.3f \t %6.3f \t %4.2f", PLJ_p1_to_p2, rx, ry, vx, vy, Math.sqrt(R)));

        }
    }

    public static void calculateAccelerationAndVelocity(double dt, double mass) {
        if (mass != 0) {
            if (AppGlobalState.gravityEnabled)
                fy += PredefinedParameters.GRAVITY * mass;

            double old_ax = ax;
            double old_ay = ay;
            ax = fx / mass * dt;
            ay = fy / mass * dt;

            // Verlet integration
            vx += dt * 0.5 * (old_ax + ax);
            vy += dt * 0.5 * (old_ay + ay);;
        }
    }

    public static void move (double dt, double mass) {
        calculateAccelerationAndVelocity(dt, mass);
        rx += vx * dt  +  0.5 * ax * dt*dt;
        ry += vy * dt  +  0.5 * ay * dt*dt;

        fx = 0; fy = 0;
    }

    public static void applyForces (double x, double y) {
        fx += x;
        fy += y;
    }

    public static double calculateLennardJones (double rmin, double eps, double invR) {
        double sigma_r2 = rmin*rmin * invR;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        double LJ = -4 * eps * (sigma_r12 - sigma_r6);     ////////

        if (Double.isNaN(LJ) || LJ > 10e4)
            return 10e4;
//        if (LJ < 0)
//            LJ = -10;
        return LJ;
    }
}
