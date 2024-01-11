package an.rozhnov.app.entity.builders;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.entity.Vector2D;
import an.rozhnov.app.entity.particle_aux.Label;
import an.rozhnov.app.entity.particle_aux.PhyParams;
import an.rozhnov.app.entity.particle_aux.Potential;
import an.rozhnov.app.entity.particle_aux.SpatialVectors;

import java.awt.*;

public class ParticleBuilder {

    private Label label = new Label("Unknown", "Unk");
    private SpatialVectors vectors = new SpatialVectors(new Vector2D(0,0), new Vector2D(0,0), new Vector2D(0,0), new Vector2D(0,0));
    private Potential potential = new Potential(0,0);
    private PhyParams phyParams = new PhyParams(283, 1, 1, Color.WHITE);


    public void setVectors (double x, double y, double vx, double vy, double ax, double ay, double fx, double fy) {
        this.vectors = new SpatialVectors(new Vector2D(x, y), new Vector2D(vx, vy), new Vector2D(ax, ay), new Vector2D(fx, fy));
    }

    public void applyForce (double fx, double fy) {
        this.vectors.getF().sum(new Vector2D(fx, fy));
    }

    public void setLabel (String name, String type) {
        this.label = new Label(name, type);
    }

    public void setLabel (Label label) {
        this.label = label;
    }

    public void setPotential (double rmin, double eps) {
        this.potential = new Potential(rmin, eps);
    }

    public void setPotential (Potential potential) {
        this.potential = potential;
    }

    public void setPhyParams(double t, double mass, int radius, Color color) {
        this.phyParams = new PhyParams(t, mass, radius, color);
    }

    public void setPhyParams(PhyParams params) {
        this.phyParams = params;
    }


    public  void setParamByText (String param, String value) {
        if (param.equals("name")) {
            label.setName(value.trim());
        }

        if (param.equals("type")) {
            label.setType(value.trim());
        }

        if (param.equals("x")) {
            vectors.getR().x = Double.parseDouble(value.trim());
        }

        if (param.equals("y")) {
            vectors.getR().y = Double.parseDouble(value.trim());
        }

        if (param.equals("vx")) {
            vectors.getV().x = Double.parseDouble(value.trim());
        }

        if (param.equals("vy")) {
            vectors.getV().y = Double.parseDouble(value.trim());
        }

        if (param.equals("radius")) {
            phyParams.setRadius(Integer.parseInt(value.trim()));
        }

        if (param.equals("mass")) {
            phyParams.setMass(Double.parseDouble(value.trim()));
        }

        if (param.equals("temperature")) {
            phyParams.setTemperature(Double.parseDouble(value.trim()));
        }

        if (param.equals("eps")) {
            potential.setEps(Double.parseDouble(value.trim()));
        }

        if (param.equals("rmin")) {
            potential.setRmin(Double.parseDouble(value.trim()));
        }

        if (param.equals("color")) {
            String strValues = value.substring(value.indexOf('(') + 1, value.indexOf(')'));
            String[] colorValues = strValues.split(",");

            phyParams.setColor(new Color(Integer.parseInt(colorValues[0].trim()),
                                         Integer.parseInt(colorValues[1].trim()),
                                         Integer.parseInt(colorValues[2].trim())));
        }
    }

    public Particle createParticle () {
        return new Particle(label, vectors, potential, phyParams);
    }
}
