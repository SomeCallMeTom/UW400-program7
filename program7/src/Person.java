/**
 * Represents the model of the input json file. A Person object stores the person's name and their friends' names.
 */
public class Person {
	private String name; 
	private String[] friends; 

	public String getName() { return name; }
	public String[] getFriends() { return friends; }
	public void setName(String name) { this.name = name; }
	public void setFriends(String[] friends) { this.friends = friends; }
}
