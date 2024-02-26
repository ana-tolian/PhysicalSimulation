import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.entity.builders.ParticleDirector;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Test5 {

    public static void main(String[] args) {
        ParticleDirector pd = new ParticleDirector();

        Particle p = pd.createStaticParticle(2, 2);
        double eps = 1;
        double rmin = 1;
        double sigma = rmin / Math.pow(2.0, 1d/6d);
        System.out.println(">>> " + sigma + " " + Math.pow(2.0, 1.0/6.0) + " ");

        double x;
        double y = 3;

//        for (x = 0; x <= 3; x += 0.01) {
//            double rx = p.getX() - x;
//            double ry = p.getY() - y;
//            double R = (rx * rx  +  ry * ry);
//            double invR = 1 / R;
//            System.out.println(p.calculateLennardJones(sigma, eps, invR) + " " + sqrt(R));
//        }

        System.out.println(calculateLennardJones2(sigma, eps, 1.0/(pow(sigma - 0.1, 2.0))));
        System.out.println(calculateLennardJones(sigma, eps, 1.0/(pow(sigma - 0.1, 2.0))));
        System.out.println(p.calculateLennardJones(sigma, eps, 1.0/(pow(sigma - 0.1, 2.0))));

//        double R = 1d;
//        double invR = 1 / R;
//        System.out.println(p.calculateLennardJones(sigma, eps, invR));
//        System.out.println(calculateLennardJones(sigma, eps, invR));
//        System.out.println(sigma*sigma * invR);
//        System.out.println(sigma*sigma*sigma*sigma * invR*invR);
//        System.out.println(sigma*sigma*sigma*sigma*sigma*sigma * invR*invR*invR);
//        System.out.println(sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma * invR*invR*invR*invR*invR*invR);
//        double rrr = 2* sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma*sigma * invR*invR*invR*invR*invR*invR - sigma*sigma*sigma*sigma*sigma*sigma * invR*invR*invR;
//        System.out.println(rrr);
//        System.out.println(24 * eps * 1 * rrr);
//        System.out.println(calculateLennardJones2(sigma, eps, invR));
    }

    public static double calculateLennardJones (double sigma, double eps, double invR) {
        double sigma_r2 = sigma*sigma * invR;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6 * sigma_r6;
        double LJ = 24 * eps * invR * (2*sigma_r12 - sigma_r6);

        if (Double.isNaN(LJ) || LJ > 10e4)
            return 10e4;
//        if (LJ < 0)
//            LJ = -10;
        return LJ;
    }

    public static double calculateLennardJones2 (double sigma, double eps, double invR) {
        double sigma_r2 = sigma*sigma * invR;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6 * sigma_r6;
        double LJ = 24 * eps * invR * (2*sigma_r12 - sigma_r6);

        System.out.println("======");
        System.out.println(sigma_r2);
        System.out.println(sigma_r6);
        System.out.println(sigma_r12);
        System.out.println(LJ);
        System.out.println("======");

        if (Double.isNaN(LJ) || LJ > 10e4)
            return 10e4;
//        if (LJ < 0)
//            LJ = -10;
        return LJ;
    }
}
