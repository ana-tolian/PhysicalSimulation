package an.rozhnov.app.io;

import an.rozhnov.app.entity.Particle;
import an.rozhnov.app.entity.builders.ParticleBuilder;
import an.rozhnov.appState.PredefinedParameters;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class ParticlePropertyLoader {

    public static final HashMap<String, Particle> PALETTE = new HashMap<>();


    public ParticlePropertyLoader () {

    }

    public void loadPalette () {

        try {
            DocumentBuilder documentBuilder;
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(PredefinedParameters.PARTICLES_PATH);

            Node root = document.getDocumentElement();
            NodeList particles = root.getChildNodes();

            // Go through the list of particles
            for (int i = 0; i < particles.getLength(); i++) {
                Node particle = particles.item(i);
                HashMap<String, Double> rmin = new HashMap<>();
                HashMap<String, Double> eps = new HashMap<>();
                String type = "null";

                if (particle.getNodeType() != Node.TEXT_NODE) {
                    ParticleBuilder builder = new ParticleBuilder();
                    NodeList attributes = particle.getChildNodes();

                    // Parsing particle parameters
                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attribute = attributes.item(j);

                        if (attribute.getChildNodes().item(0) == null)
                            continue;
                        builder.setParamByText(attribute.getNodeName(), attribute.getTextContent());

                        if (attribute.getNodeName().equals("type"))
                            type = attribute.getTextContent();

                        // Parsing potential parameters of particle
                        if (attribute.getNodeName().equals("potential")) {
                            NodeList potentialRecords = attribute.getChildNodes();
                            String potentialType = "null";

                            for (int k = 0; k < potentialRecords.getLength(); k++) {
                                NodeList potentialRecord = potentialRecords.item(k).getChildNodes();

                                for (int m = 0; m < potentialRecord.getLength(); m++) {
                                    Node potentialParam = potentialRecord.item(m);

                                    if (potentialParam.getChildNodes().item(0) == null)
                                        continue;

                                    if (potentialParam.getNodeName().equals("type"))
                                        potentialType = potentialParam.getTextContent();

                                    if (potentialParam.getNodeName().equals("rmin") && !potentialType.equals("null"))
                                        rmin.put(potentialType, Double.parseDouble(potentialParam.getTextContent()));
                                    else if (potentialParam.getNodeName().equals("eps") && !potentialType.equals("null"))
                                        eps.put(potentialType, Double.parseDouble(potentialParam.getTextContent()));
                                }
                            }
                        }
                    }

                    builder.setPotential(rmin, eps);
                    Particle p = builder.createParticle();
                    if (!type.equals("null"))
                        PALETTE.put(type, p);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
