package an.rozhnov.app.entity.particle_aux;

import java.awt.*;
import java.util.Objects;

public class PhyParams {

    private double temperature;
    private double mass;
    private double radius;
    private Color color;

    public PhyParams(double temperature, double mass, double radius, Color color) {
        this.temperature = temperature;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("temp: %4.2f", temperature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhyParams phyParams = (PhyParams) o;
        return Double.compare(phyParams.temperature, temperature) == 0 && Double.compare(phyParams.mass, mass) == 0 && Double.compare(phyParams.radius, radius) == 0 && Objects.equals(color, phyParams.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, mass, radius, color);
    }
}
