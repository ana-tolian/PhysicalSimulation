public class Test3 {

    public static void main(String[] args) {
        double[][] field = new double[50][50];
        double eps = 10e6;
        double rmin = 2;
        double sigma = rmin / Math.pow(2, 1/6);

        for (double R = 5; R >= 0; R -= 0.02) {
            System.out.println(String.format("%8.3f \t %4.2f", calculateLennardJones(sigma, eps, 1/R), Math.sqrt(R)));
        }

        double R = 0;
        System.out.println(String.format("%8.3f \t %4.2f", calculateLennardJones(sigma, eps, 1/R), 0.0));
    }

    public static double calculateLennardJones (double rmin, double eps, double invR) {
        double sigma_r2 = rmin*rmin * invR;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        double LJ = 4 * eps * (sigma_r12 - sigma_r6);     ////////

        if (Double.isNaN(LJ) || LJ > 10e3)
            return 10e3;
//        if (LJ < 0)
//            LJ = -10;
        return LJ;
    }
}
