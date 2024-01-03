package an.rozhnov.app.entity.particle_aux;

import java.util.Objects;

public class Potential {

    private double rmin;
    private double eps;

    public Potential(double rmin, double eps) {
        this.rmin = rmin;
        this.eps = eps;
    }

    public double calculateLennardJones (double R) {
        double sigma_r2 = rmin*rmin / R;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        return 4 * eps * (sigma_r12 - sigma_r6);
    }


    public double getRmin() {
        return rmin;
    }

    public void setRmin(double rmin) {
        this.rmin = rmin;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    @Override
    public String toString () {
        return String.format("rmin: %2.2f eps: %2.2f", rmin, eps);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Potential potential = (Potential) o;
        return Double.compare(potential.rmin, rmin) == 0 && Double.compare(potential.eps, eps) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rmin, eps);
    }
}
