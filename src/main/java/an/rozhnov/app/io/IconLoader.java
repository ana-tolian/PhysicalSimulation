package an.rozhnov.app.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class IconLoader {

    public static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();

    public static BufferedImage getImage (String name) {
        BufferedImage image;

        if (!IMAGES.containsKey(name)) {
            try {
                image = ImageIO.read(new File(name));
                IMAGES.put(name, image);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return image;
        }
        return IMAGES.get(name);
    }
}
