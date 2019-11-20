import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Represents a social network.
 */
public class SocialNetwork implements SocialNetworkADT {
  private Graph<String> graph;
  private static String filename;

  /**
   * Constructs a social network from a json file.
   * 
   * @param filename json file
   */
  public SocialNetwork(String filename) {
    SocialNetwork.filename = filename;

    this.graph = new Graph<String>();
    try {
      Person[] persons = parseJSON();
      constructGraph(persons);
      List<String> people = this.graph.getAllVertices();
      Collections.sort(people);
      /*
       * feel free to use this code for testing purposes System.out.println("Network: " + people);
       * for (String person : people) { List<String> friends =
       * this.graph.getAdjacentVerticesOf(person); Collections.sort(friends);
       * System.out.println(person + "'s friends: " + friends); }
       */
    } catch (FileNotFoundException e) {
      System.out.println("Error: the filename \"" + filename + "\" could not be found");
    } catch (Exception e) {
      System.out.println("Error: An unexpected exception occurred");
    }
  }

  @Override
  public int averageFriendsPerPerson() {

    return Math.round((float) graph.size() / graph.order());
  }

  @Override
  public Set<String> mutualFriends(String person1, String person2) {
    HashSet<String> out = new HashSet<String>(graph.getAdjacentVerticesOf(person1));
    out.retainAll(new HashSet<String>(graph.getAdjacentVerticesOf(person2)));

    return out;
  }

  @Override
  public String socialButterfly() {
    String hasMaxFriends = "";
    int maxFriendCount = 0;
    ArrayList<String> friends = null;
    for (String person : graph.getAllVertices()) {
      friends = (ArrayList<String>) graph.getAdjacentVerticesOf(person);
      if ((maxFriendCount < friends.size())
          || (maxFriendCount == friends.size() && hasMaxFriends.compareTo(person) > 0)) {
        maxFriendCount = friends.size();
        hasMaxFriends = person;
      }
    }
    return hasMaxFriends;
  }

  @Override
  public String influencer() {
    String hasMaxInfluence = "";
    int maxInfluence = 0;
    ArrayList<String> friends = null;

    for (String person : graph.getAllVertices()) {
      friends = (ArrayList<String>) graph.getAdjacentVerticesOf(person);
      int influence = 0;
      HashSet<String> influences = new HashSet<String>();
      influences.add(person);
      influences.addAll(friends);
      for (String friendsFriend : friends) {
        influences.addAll(graph.getAdjacentVerticesOf(friendsFriend));
      }
      influence = influences.size();
      if ((maxInfluence < influence)
          || (maxInfluence == influence && hasMaxInfluence.compareTo(person) > 0)) {
        maxInfluence = influence;
        hasMaxInfluence = person;
      }

    }
    return hasMaxInfluence;
  }

  @Override
  public Set<String> haveSeenMeme(String person, int days) {
    HashSet<String> out = new HashSet<String>();
    LinkedList<String> q = new LinkedList<String>();
    if (days > 0 && graph.getAllVertices().contains(person)) {
      q.add(person);
      for (int day = 1; day <= days; day++) {
        q.removeAll(out);
        int stop = q.size();
        for (int i = 0; i < stop; i++) {
          String p = q.remove();
          q.addAll(graph.getAdjacentVerticesOf(p));
          out.add(p);
        } // end person loop
      } // end days loop
    } // end if days > 0 && contains(person)
    return out;
  }

  @Override
  public Set<String> youMayKnow(String person) {
    HashSet<String> out = new HashSet<String>();
    for (String friend : graph.getAdjacentVerticesOf(person)) {
      out.addAll(graph.getAdjacentVerticesOf(friend));
    }
    out.removeAll(graph.getAdjacentVerticesOf(person));
    out.remove(person);
    return out;
  }

  @Override
  public boolean isFriendGroup(Set<String> people) {
    if (people.size() < 2) {
      return false;
    }
    ArrayList<String> friends;
    HashSet<String> peopleToCheck;
    for (String person : people) {
      friends = new ArrayList<String>(graph.getAdjacentVerticesOf(person));
      peopleToCheck = new HashSet<String>(people);
      peopleToCheck.remove(person);
      if (!friends.containsAll(peopleToCheck)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean sixDegreesOfSeparation() {
    ArrayList<String> vertices = new ArrayList<String>(graph.getAllVertices());
    Collections.sort(vertices);
    HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();

    for (String fromP : vertices) {
      map.put(fromP, new HashMap<String, Integer>());
      for (String toP : vertices) {
        map.get(fromP).put(toP, (int) Double.POSITIVE_INFINITY);
      }
    }
    // set all distances to self to 0
    for (String self : vertices) {
      map.get(self).put(self, 0);
    }
    // set all distances to adjacent to 1
    for (String fromP : vertices) {
      for (String toP : graph.getAdjacentVerticesOf(fromP)) {
        map.get(fromP).put(toP, 1);
      }
    }
    // test for paths between unconnected
    for (String person : vertices) {
      for (String toP : vertices) {
        for (String fromP : vertices) {
          double potential =
              (double) map.get(fromP).get(person) + (double) map.get(person).get(toP);
          if (potential < 0) {
            System.out.println("wrapping problem");
          }
          if (map.get(fromP).get(toP) > potential) {
            map.get(fromP).put(toP, (int) potential);
          }
        }
      }
    }
    // test for any paths >6
    for (String toP : vertices) {
      for (String fromP : vertices) {
        if (map.get(fromP).get(toP) > 6) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public List<String> socialLadder(String person1, String person2) {
    LinkedList<String> out = new LinkedList<String>();
    PriorityQueue<String> q = new PriorityQueue<String>();
    if (!(graph.getAllVertices().contains(person1) && graph.getAllVertices().contains(person2))) {
      return out;
    }
    if (graph.getAdjacentVerticesOf(person1).contains(person2)) {
      out.add(person1);
      out.add(person2);
      return out;
    }
    class VProperties {
      boolean visited;
      int totalWeight;
      String predecessor;

      VProperties() {
        this.visited = false;
        this.totalWeight = (int) Double.POSITIVE_INFINITY;
        this.predecessor = null;
      }
    }

    HashMap<String, VProperties> map = new HashMap<String, VProperties>();
    for (String person : graph.getAllVertices()) {
      map.put(person, new VProperties());
    }
    map.get(person1).totalWeight = 0;
    q.add(person1);
    // starting point has 0 weight
    while (!q.isEmpty()) {
      String person = q.remove();
      for (String friend : graph.getAdjacentVerticesOf(person)) {
        if (map.get(friend).totalWeight > (map.get(person).totalWeight + 1)) {
          map.get(friend).totalWeight = map.get(person).totalWeight + 1;
          map.get(friend).predecessor = person;
        }
        if (!map.get(friend).visited) {
          q.add(friend);
        }
        map.get(person).visited = true;
      }
    }
    out.add(person2);
    String pred = map.get(person2).predecessor;
    while (!pred.equals(person1)) {
      if (pred.equals("")) {
        return new LinkedList<String>();
      }
      out.add(0, pred);
      pred = map.get(pred).predecessor;
    }
    out.add(0, person1);
    return out;
  }

  @Override
  public String glue(Set<String> people) {
    String out = "";
    if (people.size() < 3 || !graph.getAllVertices().containsAll(people)) {
      return out;
    }
    Person[] persons = new Person[people.size()];
    int i = 0;
    for (String name : people) {
      persons[i] = new Person();
      persons[i].setName(name);
      persons[i].setFriends((String[]) graph.getAdjacentVerticesOf(name).toArray());
      i++;
    }
    
      
    Graph<String> subGraph = new Graph<String>();
    for (Person person : persons) {
      subGraph.addVertex(person.getName());
      for (String friend : person.getFriends()) {
        subGraph.addVertex(friend);
        subGraph.addEdge(person.getName(), friend);
      }
    }
    
    
    
    HashSet<String> set = new HashSet<String>();
    LinkedList<String> q = new LinkedList<String>();

    q.add(persons[0].getName());
    for (int j = 1; j <= people.size(); j++) {
      q.removeAll(set);
      int stop = q.size();
      for (int j1 = 0; j1 < stop; j1++) {
        String p = q.remove();
        q.addAll(subGraph.getAdjacentVerticesOf(p));
        set.add(p);
      } // end person loop
    } // end for loop
    if (!(set.size() == people.size())) {
      return out;
    }

    for (Person person : persons) {
      subGraph.removeVertex(person.getName());
      // build DFS, check if contains all elements
    }
    return out;
  }
  }
  /**
   * Construct an undirected graph from array of Person objects.
   * 
   * @param people an array of People objects generated from a json file
   */
  private void constructGraph(Person[] people) {
    for (Person person : people) {
      graph.addVertex(person.getName());
      for (String friend : person.getFriends()) {
        graph.addVertex(friend);
        graph.addEdge(person.getName(), friend);
      }
    }
  }


  /**
   * Parses the input JSON file.
   * 
   * @return array of Person objects which stores information about a single person
   * @throws Exception like FileNotFound, JsonParseException
   */
  private static Person[] parseJSON() throws Exception {
    // array storing the Person objects created from the JSON file to be loaded later in the graph
    Person[] people = null;

    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(new FileReader(filename));

    JSONArray socNet = (JSONArray) json.get("socialNetwork");
    people = new Person[socNet.size()];
    int i = 0;
    for (Object personObj : socNet) {
      JSONObject jsonPerson = (JSONObject) personObj;
      people[i] = new Person();

      String name = (String) jsonPerson.get("name");
      people[i].setName(name);
      JSONArray jsonFriends = (JSONArray) jsonPerson.get("friends");
      String[] friends = new String[jsonFriends.size()];
      for (int j = 0; j < jsonFriends.size(); j++) {
        friends[j] = (String) jsonFriends.get(j);
      }
      people[i].setFriends(friends);
      i++;
    }

    return people;
  }

  public static void main(String args[]) {
    SocialNetwork testSN = new SocialNetwork("social-network-lg.json");
    testSN.sixDegreesOfSeparation();
  }
}
