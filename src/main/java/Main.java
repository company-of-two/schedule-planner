import schedulePlanner.Catalogue;
import schedulePlanner.Map;

public class Main {
    public static void main(String[] args) {

        Map map = new Map();
        map.fromXML("datasets/lfs_map.xml");

        Catalogue catalogue = new Catalogue(map);
        catalogue.fromXML("datasets/lfs_session_list.xml");

    }
}
