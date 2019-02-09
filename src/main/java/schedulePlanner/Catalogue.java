package schedulePlanner;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Catalogue implements ImportExport {

    private HashMap<String, Event> events;
    private Map map;

    public Catalogue(HashMap<String, Event> events, Map map) {
        this.events = events;
        this.map = map;
    }

    public Catalogue(HashMap<String, Event> events) {
        this.events = events;
    }

    public Catalogue(Map map) {
        this.map = map;
        this.events = new HashMap<String, Event>();
    }


    public Catalogue() {
        this.events = new HashMap<String, Event>();
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Event getEvent(String eventName) {
        return this.events.get(eventName);
    }

    public void fromXML(String inputFilePath) {
        // There is no way to assign venues to sessions if map hasn't been created and set yet
        if (this.map == null) {
            throw new RuntimeException("Map has to be set first in order to generate Catalogue from an XML file");
        }

        try {

            File xmlFile = new File(inputFilePath);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            NodeList sessionsNodeList = document.getElementsByTagName(XMLTags.SESSION);

            for (int i = 0; i < sessionsNodeList.getLength(); i++) {
                Element sessionElement = (Element) sessionsNodeList.item(i);

                LocalDateTime dateTime = LocalDateTime.parse(sessionElement.getAttribute(XMLAttributes.SESSION_DATETIME));
                int duration = Integer.parseInt(sessionElement.getAttribute(XMLAttributes.SESSION_DURATION));
                String venueName = sessionElement.getAttribute(XMLAttributes.SESSION_VENUE);
                String name = sessionElement.getAttribute(XMLAttributes.SESSION_NAME);

                Session session = new Session(this.map.getVenue(venueName), dateTime, duration);

                Event event = this.getEvent(name);
                if (event == null) {
                    event = new Event(name);
                    this.events.put(name, event);
                }

                event.addSession(session);

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
