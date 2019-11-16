import java.util.List;
import java.util.Set;

/**
 * Interface for a Social Network class which represents a graph of people in a social network and the connections 
 * between them. This class implements the following graph algorithm methods on the social network. You may add private 
 * helper methods, but you may not add any public methods to any file other than those specified in the ADT. You may 
 * use the Java Stream API.
 */
public interface SocialNetworkADT {
	/**
	 * Return the average number of friends people in the social network have. Use the half round up rounding technique 
	 * (examples: 6.4 rounds to 6, 6.5 rounds to 7, 6.6 rounds to 7). If the network is empty return 0.
	 * @return average number of friends per person
	 */
	public int averageFriendsPerPerson();
	
	/**
	 * Given two people, return their mutual friends. If either person parameter is null or does not exist in the 
	 * network, return an empty Set.
	 * @param person1 first person
	 * @param person2 second person
	 * @return mutual friends
	 */
	public Set<String> mutualFriends(String person1, String person2);
	
	/**
	 * Return the person in the social network who has the most friends. If there is a tie return the social butterfly 
	 * who comes first in alphabetical order. If the network is empty return the empty string "".
	 * @return the social butterfly
	 */
	public String socialButterfly();
	
	/**
	 * Return the person who has the most friends of friends. Include friends in this count as well as friends of 
	 * friends, but be careful not to double-count if a friend is also a friend of a friend. If there is a tie return 
	 * the influencer who comes first in alphabetical order. If the network is empty return the empty string "".
	 * @return the influencer
	 */
	public String influencer();

	/**
	 * Given a person who posts a meme online and assuming that it spreads to all of their friends on the second day, 
	 * all of their friends' friends on the third day, etc., return the set of all people who have seen the meme 
	 * (including the meme's creator) after the provided number of days. In other words, if Katie starts the meme and 
	 * the days parameter is 3, then on Day 1 the set of people who have seen the meme would be Katie. On Day 2, the 
	 * set of people would be Katie and Katie's friends. On Day 3, the set of people would be Katie, Katie's friends, 
	 * and Katie's friends' friends. If the person parameter is null or does not exist in the network, or the number of 
	 * days is less than 1, return an empty Set.
	 * @param person person starting meme
	 * @param days days since meme was posted
	 * @return all people who have seen the meme
	 */
	public Set<String> haveSeenMeme(String person, int days);
	
	/**
	 * Given a person, return a set of all of the people they might know: these are their friends' friends who are not 
	 * already their friends. If the person parameter is null or does not exist in the network, return an empty Set.
	 * @param person person in question
	 * @return set of friends' friends who are not already a friend
	 */
	public Set<String> youMayKnow(String person);
	
	/**
	 * Given a set of people, find out if they are a friend group, i.e., each person in the group is a friend of every 
	 * other person in the group. If the people parameter has a size less than 2, return false.
	 * @param people
	 * @return
	 */
	public boolean isFriendGroup(Set<String> people);
	
	/**
	 * Six degrees of separation is the idea that all people are six, or fewer, social connections away from each 
	 * other. In other words, a chain of "a friend of a friend" statements can be made to connect any two people in a 
	 * maximum of six steps. If the network is empty return true.
	 * @return true if the theory holds for this network, otherwise false
	 */
	public boolean sixDegreesOfSeparation();
	
	/**
	 * Given two people, the first one wanting to be friends with the second, return a list, in order, of the fewest 
	 * amount of people the first person can befriend to get to the second person. If there is a tie between two paths 
	 * choose the path which has the starting person who comes first alphabetically (ex: choose Alex->Jess over 
	 * Bailey->Jess) If either person parameter is null or does not exist in the network, or if there is no path from 
	 * person1 to person2, return an empty List.
	 * @param person1 the person who is seeking to be friends with person2
	 * @param person2 the person with whom person1 is seeking to be friends with
	 * @return the ordered list of people in the social ladder
	 */
	public List<String> socialLadder(String person1, String person2);
	
	/**
	 * Given a set of people, find out if there is one friend which, without that person, the sub-network would fall 
	 * apart (in other words, there would be two separate groups of people with no connection between them). If there 
	 * are multiple people who could be the "glue", return the one that comes first alphabetically. If there is no 
	 * single person that is the "glue" of this set of people, return the empty string "". If the set of people has a 
	 * size less than 3 or is already disconnected, return the empty string "".
	 * @param people
	 * @return glue of the network, without whom, the network would fall apart; null if there is no glue person
	 */
	public String glue(Set<String> people);
}
