import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
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
			/* feel free to use this code for testing purposes
			System.out.println("Network: " + people);
			for (String person : people) {
				List<String> friends = this.graph.getAdjacentVerticesOf(person);
				Collections.sort(friends);
				System.out.println(person + "'s friends: " + friends);
			}
			*/
		} catch (Exception e) {
			System.out.println("Error: An unexpected exception occurred");
		}
	}

  @Override
  public int averageFriendsPerPerson() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Set<String> mutualFriends(String person1, String person2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String socialButterfly() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String influencer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<String> haveSeenMeme(String person, int days) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<String> youMayKnow(String person) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isFriendGroup(Set<String> people) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sixDegreesOfSeparation() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<String> socialLadder(String person1, String person2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String glue(Set<String> people) {
    // TODO Auto-generated method stub
    return null;
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
      for (int j = 0; j < jsonFriends.size();j++) {
        friends[j] = (String) jsonFriends.get(j);
      }
      people[i].setFriends(friends);
      i++;
    }
  
    return people;
  }

	// TODO: add graph algorithm methods from SocialNetworkADT
}
