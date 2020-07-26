package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import edu.princeton.cs.algs4.ST;
import org.apache.commons.math3.geometry.partitioning.utilities.AVLTree;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private Map<Point, Long> pointToNode = new HashMap<>();
    private KDTree kd;
    private MyTrieSet trie = new MyTrieSet();
    private Map<String, List<Node>> nameToNodes = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        List<Node> nodes = this.getNodes();


        //create a list of Points for kdTree, and map points to nodes
        List<Point> points = new LinkedList<>();
        for (Node n : nodes) {
            //only consider vertices that have neighbors
            if (!this.neighbors(n.id()).isEmpty()) {
                Point p = new Point(n.lon(), n.lat());
                points.add(p);
                pointToNode.put(p, n.id());
            }
            if (n.name() != null) {
                String cleanName = cleanString(n.name());

                //add to trie set
                trie.add(cleanName);

                //multiple locations may share the same name
                if (!nameToNodes.containsKey(cleanName)) {
                    List<Node> nameNodes = new LinkedList<>(); //name appears the first time
                    nameNodes.add(n);
                    nameToNodes.put(cleanName, nameNodes);
                } else {
                    List<Node> nodesWithSameName = nameToNodes.get(cleanName); //find locations with the same name, update the List<Node>
                    nodesWithSameName.add(n);
                    nameToNodes.put(cleanName, nodesWithSameName);
                }
            }
        }
        kd = new KDTree(points);
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nearestPoint = kd.nearest(lon, lat);
        return pointToNode.get(nearestPoint);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> toReturn = new LinkedList<>();
        List<String> cleanNames = trie.keysWithPrefix(prefix);
        for (String s: cleanNames) {
            List<Node> nodes = nameToNodes.get(s);
            for (Node n: nodes) {
                toReturn.add(n.name());
            }
        }
        return toReturn;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanLocationName = cleanString(locationName);
        List<Map<String, Object>> toReturn = new LinkedList<>();
        List<Node> nodes = nameToNodes.get(cleanLocationName);
        for (Node n: nodes) {
            Map<String, Object> map = new HashMap<>();
            map.put("lat", n.lat());
            map.put("lon", n.lon());
            map.put("name", n.name());
            map.put("id", n.id());
            toReturn.add(map);
        }
        return toReturn;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
