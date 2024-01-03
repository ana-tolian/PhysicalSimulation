package an.rozhnov.app.io;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.entity.builders.ParticleBuilder;
import an.rozhnov.app.entity.builders.ParticleDirector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SimLoader {

    private String imgConfig;
    private String imgSrc;
    private HashMap<Color, Particle> possibleParticles;


    public SimLoader (String imgConfig) {
        this.possibleParticles = new HashMap<>();
        this.imgConfig = imgConfig;
        this.imgSrc = getImgSrc();
    }

    public String getImgSrc () {
        String src = "";

        try {
            DocumentBuilder documentBuilder;
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(imgConfig);

            Node root = document.getDocumentElement();

            NodeList particles = root.getChildNodes();
            src = particles.item(0).getTextContent();

            for (int i = 1; i < particles.getLength(); i++) {
                Node particle = particles.item(i);

                if (particle.getNodeType() != Node.TEXT_NODE) {
                    ParticleBuilder builder = new ParticleBuilder();
                    NodeList attributes = particle.getChildNodes();

                    for(int j = 0; j < attributes.getLength(); j++) {
                        Node attribute = attributes.item(j);
                        builder.setParamByText(attribute.getTextContent(), attribute.getChildNodes().item(0).getTextContent());
                    }

                    Particle p = builder.createParticle();
                    possibleParticles.put(p.getPhyParams().getColor(), p);
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return src;
    }

    public ArrayList<Particle> getParticlesFromImage () {
        BufferedImage img = getImageData();
        ArrayList<Particle> particles = new ArrayList<>();
        ParticleDirector director = new ParticleDirector();

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getWidth(); y++) {
                Color color = new Color(img.getRGB(x, y));

                if (!color.equals(Color.WHITE) && possibleParticles.containsKey(color)) {
                    particles.add(director.createParticleFromBrush(possibleParticles.get(color), x, y));
                }
            }
        }
        return particles;
    }

    private BufferedImage getImageData () {
        File imageFile = new File(imgSrc);
        BufferedImage img;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
}

