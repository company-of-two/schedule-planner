package schedulePlanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Map implements ImportExport {

    private HashMap<String, Venue> venues;
    private HashMap<String, Integer> venueIds;
    private ArrayList<ArrayList<Integer> > distancesMatrix;

    //
    private int venuesCount;

    public Map() {
        this.venues = new HashMap<String, Venue>();
        this.venueIds = new HashMap<String, Integer>();
        this.distancesMatrix = new ArrayList<ArrayList<Integer>>();
        this.venuesCount = 0;
    }

    public Venue getVenue(String venueName) {
        return venues.get(venueName);
    }

    public int getDistance(String from, String to) {
        Integer fromId = venueIds.get(from);
        Integer toId = venueIds.get(to);

        if (fromId == null || toId == null) {
            throw new RuntimeException("One of the venues hasn't been defined");
        }

        return distancesMatrix.get(fromId).get(toId);
    }

    private Venue createVenue(String venueName) {
        venueIds.put(venueName, venuesCount++);
        return new Venue(venueName);
    }

    public void fromXML(String inputFilePath) {

        try {

            File xmlFile = new File(inputFilePath);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            NodeList edgesNodes = document.getElementsByTagName(XMLTags.EDGE);

            for (int i = 0; i < edgesNodes.getLength(); i++) {
                Node edgeNode = edgesNodes.item(i);
                if (edgeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element edge = (Element) edgeNode;

                    String from = edge.getAttribute(XMLAttributes.EDGE_FROM);
                    String to = edge.getAttribute(XMLAttributes.EDGE_TO);
                    int travelTime = Integer.parseInt(edge.getAttribute(XMLAttributes.EDGE_TRAVEL_TIME));

                    // Create the venues if they do not exist yet
                    if (getVenue(from) == null) {
                        this.venues.put(from, createVenue(from));
                    }

                    if (getVenue(to) == null) {
                        this.venues.put(to, createVenue(to));
                    }

                    int fromId = this.venueIds.get(from);
                    int toId = this.venueIds.get(to);

                    this.distancesMatrix.
                    this.distancesMatrix.get(fromId).ensureCapacity(venuesCount);

                }

            }

        } catch (Exception e) {

        }

    }

    public void toXML(String outputFilePath) {

    }

    public void fromJSON(String inputFilePath) {

    }

    public void toJSON(String outputFilePath) {

    }
}
