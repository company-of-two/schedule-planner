package schedulePlanner;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Catalogue implements ImportExport {

    private ArrayList<Event> events;
    private Map map;

    public Catalogue(ArrayList<Event> events, Map map) {
        this.events = events;
        this.map = map;
    }

    public Catalogue(ArrayList<Event> events) {
        this.events = events;
    }

    public Catalogue(Map map) {
        this.map = map;
        this.events = new ArrayList<Event>();
    }


    public Catalogue() {
        this.events = new ArrayList<Event>();
    }

    public void setMap(Map map) {
        this.map = map;
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

            NodeList sessionsNode = document.getElementsByTagName(XMLTags.SESSIONS);

            for (int i = 0; i < sessionsNode.getLength(); i++) {
                Node sessionNode = sessionsNode.item(i);
                Session session = new Session();
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
