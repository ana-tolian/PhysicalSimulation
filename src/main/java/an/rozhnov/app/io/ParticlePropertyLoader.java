package an.rozhnov.app.io;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.entity.builders.ParticleBuilder;
import an.rozhnov.appState.PreInitialisedParameters;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ParticlePropertyLoader {

    public static final HashMap<String, Particle> PALETTE = new HashMap<>();


    public ParticlePropertyLoader () {

    }

    public void loadPalette () {

        try {
            DocumentBuilder documentBuilder;
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(PreInitialisedParameters.PARTICLES_PATH);

            Node root = document.getDocumentElement();
            NodeList particles = root.getChildNodes();

            for (int i = 0; i < particles.getLength(); i++) {
                Node particle = particles.item(i);
                String type = "null";

                if (particle.getNodeType() != Node.TEXT_NODE) {
                    ParticleBuilder builder = new ParticleBuilder();
                    NodeList attributes = particle.getChildNodes();

                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attribute = attributes.item(j);

                        if (attribute.getChildNodes().item(0) == null)
                            continue;
                        builder.setParamByText(attribute.getNodeName(), attribute.getTextContent());

                        if (attribute.getNodeName().equals("type"))
                            type = attribute.getTextContent();
                    }

                    Particle p = builder.createParticle();
                    if (!type.equals("null"))
                        PALETTE.put(type, p);
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
