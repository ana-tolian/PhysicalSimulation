import java.awt.*;

public class Test2 {

    public static void main(String[] args) {
        double rmin1 = 0.3;
        double eps1 = 200;
        double rmin2 = 1;
        double eps2 = 0;

        int px1 = 15;
        int py1 = 15;
        for (int px2 = 10; px2 < 20; px2++)
            for (int py2 = 10; py2 < 20; py2++) {
                double rx = px1 - px2;
                double ry = py1 - py2;

                double R = rx * rx + ry * ry;
                double invr = 1 / Math.sqrt(R);
                rx *= invr;
                ry *= invr;

                double PLJ = calculateLennardJones(rmin1, eps1, R);
                System.out.println(px2 + " " + py2 + " " + rx * PLJ + " " + ry * PLJ);
            }

        System.out.println("\n==========\n");

        int px2 = 15;
        int py2 = 15;
        for (px1 = 10; px1 < 20; px1++)
            for (py1 = 10; py1 < 20; py1++) {
                double rx = px2 - px1;
                double ry = py2 - py1;

                double R = rx * rx + ry * ry;
                double invr = 1 / Math.sqrt(R);
                rx *= invr;
                ry *= invr;

                double PLJ = calculateLennardJones(rmin2, eps2, R);
                System.out.println(px1 + " " + py1 + " " + rx * PLJ + " " + ry * PLJ);
            }
    }

    public static double calculateLennardJones (double rmin, double eps, double R) {
        double sigma_r2 = rmin*rmin / R;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        double LJ = 4 * eps * (sigma_r12 - 2*sigma_r6);

        if (Double.isNaN(LJ))
            return -10.0;
        if (Math.abs(LJ) > 10)
            LJ = 10 * (LJ < 0 ? -1 : 1);
        return LJ;
    }
}
