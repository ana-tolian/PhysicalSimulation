package an.rozhnov.app.entity.particle_aux;

import an.rozhnov.app.entity.Vector2D;
import an.rozhnov.appState.PredefinedParameters;
import an.rozhnov.appState.currentState.AppGlobalState;

import java.util.Objects;

import static an.rozhnov.appState.currentState.AppGlobalState.simSpeed;
import static java.lang.Math.abs;

public class SpatialVectors {

    public Vector2D r;
    public Vector2D v;
    public Vector2D f;

    public SpatialVectors(Vector2D r, Vector2D v, Vector2D f) {
        this.r = r;
        this.v = v;
        this.f = f;
    }

    public void accelerate (double mass) {
        limit(f, 10.0);
        v.x += f.x / mass * simSpeed.dt();
        v.y += f.y / mass * simSpeed.dt();
        if (AppGlobalState.gravityEnabled) v.y += PredefinedParameters.GRAVITY;

        f.x = 0;
        f.y = 0;
        limit(v, 5.0);
    }

    public void move (double mass) {
        accelerate(mass);

        r.x += v.x * simSpeed.dt();
        r.y += v.y * simSpeed.dt();
    }

    public void applyForces (double fx, double fy) {
        f.x += fx;
        f.y += fy;
    }

    public double calculateLennardJones (Potential potential, double R) {
        double sigma_r2 = potential.getRmin()*potential.getRmin() / R;
        double sigma_r6 = sigma_r2 * sigma_r2 * sigma_r2;
        double sigma_r12 = sigma_r6*sigma_r6;
        double LJ = -4 * potential.getEps() * (sigma_r12 - 2*sigma_r6);

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
        return String.format("x: %4.3f y: %4.3f vx: %2.3f vy: %2.3f", r.x, r.y, v.x, v.y);
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
