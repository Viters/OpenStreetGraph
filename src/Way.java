import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for operations on Open Street Map street data.
 * It does not hold actual Node data, only referenced to it via ID.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class Way {
    private long id;
    private ArrayList<Node> connectedNodes;
    private double distance;
    private String type;
    private String name;
    private boolean isRoundabout;
    /**
     * Street types that will NOT be deleted while Map filtering.
     *
     * @see <a href="https://wiki.openstreetmap.org/wiki/Pl:Map_Features">Open Street Map documentation</a>
     */
    private static List<String> allowedTypes = Arrays.asList(
            "motorway", "trunk", "primary", "secondary",
            "tertiary", "unclassified", "residential", "motorway_link",
            "trunk_link", "primary_link", "secondary_link",
            "tertiary_link", "living_street", "pedestrian"
    );

    Way() {
        connectedNodes = new ArrayList<>();
        distance = 0;
        name = "Bez nazwy";
        isRoundabout = false;
    }

    void setId(long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }

    double getDistance() {
        return distance;
    }

    /**
     * Calculates distance between two nodes using haversine formula.
     *
     * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">Haversine formula</a>
     */
    void setDistance() {
        double tempDist = 0;

        for (int i = 0; i < connectedNodes.size() - 1; ++i) {
            double lon1 = connectedNodes.get(i).getLon();
            double lat1 = connectedNodes.get(i).getLat();
            double lon2 = connectedNodes.get(i + 1).getLon();
            double lat2 = connectedNodes.get(i + 1).getLat();

            double latDiff = lat2 - lat1;
            double lonDiff = lon2 - lon1;
            double latDistance = Math.toRadians(latDiff);
            double lngDistance = Math.toRadians(lonDiff);

            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1))
                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            tempDist += c;
        }

        this.distance = 6371 * tempDist * 1000;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    ArrayList<Node> getConnectedNodes() {
        return connectedNodes;
    }

    Node getFirstNode() {
        return connectedNodes.get(0);
    }

    Node getLastNode() {
        return connectedNodes.get(connectedNodes.size() - 1);
    }

    static List<String> getAllowedTypes() {
        return allowedTypes;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    boolean isRoundabout() {
        return isRoundabout;
    }

    void setRoundabout(boolean roundabout) {
        isRoundabout = roundabout;
    }
}
