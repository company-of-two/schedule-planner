package schedulePlanner;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
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

    private int venuesCount;
    private int nextVenueId;

    public Map() {
        this.venues = new HashMap<String, Venue>();
        this.venueIds = new HashMap<String, Integer>();
        this.venuesCount = 0;
        this.nextVenueId = 0;
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
        this.venueIds.put(venueName, nextVenueId++);
        return new Venue(venueName);
    }

    public void fromXML(String inputFilePath) {

        try {

            File xmlFile = new File(inputFilePath);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element mapElement = document.getDocumentElement();

            if (!mapElement.getTagName().equals(XMLTags.MAP)) {
                throw new RuntimeException("Wrong file");
            }

            this.venuesCount = Integer.parseInt(mapElement.getAttribute(XMLAttributes.MAP_VENUES_COUNT));

            this.distancesMatrix = new ArrayList<ArrayList<Integer>>(
                    Collections.nCopies(this.venuesCount, new ArrayList<Integer>(Collections.nCopies(this.venuesCount, -1)))
            );

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

                    // Set the travel time between the two venues
                    distancesMatrix.get(fromId).set(toId, travelTime);

                    // If the return travel time hasn't been set, set it so that the travel times are symmetrical
                    if (distancesMatrix.get(toId).get(fromId) == -1) {
                        distancesMatrix.get(toId).set(fromId, travelTime);
                    }

                }

            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

    }

    public void toXML(String outputFilePath) {

    }

    public void fromJSON(String inputFilePath) {

    }

    public void toJSON(String outputFilePath) {

    }
}
