package an.rozhnov.app.entity.particle_aux;

import an.rozhnov.app.entity.Particle;

import java.util.HashMap;
import java.util.Objects;

public class Potential {


    private HashMap<String, Double> rmin;
    private HashMap<String, Double> eps;

    public Potential() {
        this.rmin = new HashMap<>();
        this.eps = new HashMap<>();
    }

    public Potential(HashMap<String, Double> rmin, HashMap<String, Double> eps) {
        this.rmin = rmin;
        this.eps = eps;
    }

    public double calculateLennardJones (String type, double R) {
        double rmin = this.rmin.get(type);
        double eps = this.eps.get(type);

        double sigma_r2 = rmin*rmin / R;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        return 4 * eps * (sigma_r12 - sigma_r6);
    }


    public HashMap<String, Double> getRmin() {
        return rmin;
    }

    public double getRmin(Particle p) {
        return getRmin(p.getLabel().getType());
    }


    public double getRmin(String type) {
        return rmin.get(type);
    }


    public void setRmin(HashMap<String, Double> rmin) {
        this.rmin = rmin;
    }

    public void addRmin(String type, double value) {
        rmin.put(type, value);
    }

    public HashMap<String, Double> getEps() {
        return eps;
    }

    public double getEps(Particle p) {
        return getEps(p.getLabel().getType());
    }

    public double getEps(String type) {
        return eps.get(type);
    }

    public void setEps(HashMap<String, Double> eps) {
        this.eps = eps;
    }

    public void addEps(String type, double value) {
        eps.put(type, value);
    }


    @Override
    public String toString () {
        return "";//String.format(rmin.toString() + " " + eps.toString());
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Potential potential = (Potential) o;
//        return Double.compare(potential.rmin, rmin) == 0 && Double.compare(potential.eps, eps) == 0;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(rmin, eps);
    }
}
