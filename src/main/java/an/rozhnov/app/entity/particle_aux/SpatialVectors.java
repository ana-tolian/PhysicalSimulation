package an.rozhnov.app.entity.particle_aux;

import an.rozhnov.app.entity.Vector2D;
import an.rozhnov.appState.PredefinedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.util.Objects;

import static an.rozhnov.appState.currentState.AppGlobalState.speedMode;
import static java.lang.Math.abs;

public class SpatialVectors {

    public Vector2D r;
    public Vector2D v;
    public Vector2D a;
    public Vector2D f;

    public SpatialVectors(Vector2D r, Vector2D v, Vector2D a, Vector2D f) {
        this.r = r;
        this.v = v;
        this.a = a;
        this.f = f;
    }

    public void calculateAcceleration(double mass) {
        if (mass != 0) {
            if (AppGlobalState.gravityEnabled)
                f.y += PredefinedParameters.GRAVITY * mass;

            a.x = f.x / mass * speedMode.dt();
            a.y = f.y / mass * speedMode.dt();
        }

        f.x = 0;
        f.y = 0;
    }

    public void move (double mass) {
        calculateAcceleration(mass);

        r.x += v.x * speedMode.dt() * speedMode.dt();
        r.y += v.y * speedMode.dt() * speedMode.dt();
    }

    public void applyForces (double fx, double fy) {
        f.x += fx;
        f.y += fy;
    }

    public double calculateLennardJones (Potential potential, double R) {
        double sigma_r2 = potential.getRmin()*potential.getRmin() / R;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        double LJ = 4 * potential.getEps() * (sigma_r12 - 2*sigma_r6);

        if (Double.isNaN(LJ))
            return -10.0;
        if (Math.abs(LJ) > 10)
            LJ = 10 * (LJ < 0 ? -1 : 1);
        return LJ;
    }

    private void limit (Vector2D vector2D, double limit) {
        vector2D.x = limit(vector2D.x, limit);
        vector2D.y = limit(vector2D.y, limit);
    }

    private double limit (double c, double limit) {
        if (abs(c) > limit || Double.isNaN(c) || Double.isInfinite(c)) {
            if (limit == 5.0)
                System.out.println(c);
            double temp = c;
            c = limit;

            if (temp < 0)
                c *= -1;
        }
        return c;
    }

    public Vector2D getR() {
        return r;
    }

    public void setR(Vector2D r) {
        this.r = r;
    }

    public Vector2D getV() {
        return v;
    }

    public void setV(Vector2D v) {
        this.v = v;
    }

    public Vector2D getF() {
        return f;
    }

    public void setF(Vector2D f) {
        this.f = f;
    }

    @Override
    public String toString () {
        return String.format("x: %4.3f y: %4.3f vx: %2.3f vy: %2.3f fx: %2.3f fy: %2.3f", r.x, r.y, v.x, v.y, f.x, f.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpatialVectors that = (SpatialVectors) o;
        return Objects.equals(r, that.r) && Objects.equals(v, that.v) && Objects.equals(f, that.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, v, f);
    }
}
