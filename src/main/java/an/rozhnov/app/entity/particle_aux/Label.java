package an.rozhnov.app.entity.particle_aux;

import java.util.Objects;

public class Label {

    private String name;
    private String type;

    public Label(String name, String type) {
        this.name = name;

        if (type.length() > 3)
            this.type = type.substring(0, 3);
        else
            this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString () {
        return name + " [" + type + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return Objects.equals(name, label.name) && Objects.equals(type, label.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
