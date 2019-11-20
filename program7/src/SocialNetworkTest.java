import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

/**
 * JUnit test class to test class Graph that implements GraphADT interface.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocialNetworkTest {
  private SocialNetwork emptyNetwork;
  private SocialNetwork lgNetwork;
  private SocialNetwork mdNetwork;
  private SocialNetwork smNetwork;

  @Rule
  public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

  @Before
  public void setUp() throws Exception {
    // you may use any or all of the provided json files for JUnit testing
    this.emptyNetwork = new SocialNetwork("social-network-empty.json");
    this.smNetwork = new SocialNetwork("social-network-sm.json");
    this.mdNetwork = new SocialNetwork("social-network-md.json");
    this.lgNetwork = new SocialNetwork("social-network-lg.json");

  }

  @After
  public void tearDown() throws Exception {
    this.emptyNetwork = this.smNetwork = this.mdNetwork = this.lgNetwork = null;
  }

  @Test
  public final void test00_socialButterflyValid() {
    try {
      assertEquals(
          "test00: failed - expected: \"Lilly\" returned: \"" + smNetwork.socialButterfly() + "\"",
          true, smNetwork.socialButterfly().equals("Lilly"));

      assertEquals("test00: failed - expected: \"Addison\" returned: \""
          + mdNetwork.socialButterfly() + "\"", true,
          mdNetwork.socialButterfly().equals("Addison"));

      assertEquals(
          "test00: failed - expected: \"Alisha\" returned: \"" + lgNetwork.socialButterfly() + "\"",
          true, lgNetwork.socialButterfly().equals("Alisha"));
    } catch (Exception e) {
      fail("test00: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test
  public final void test01_socialButterflyEmptyNetwork() {
    try {
      assertEquals(
          "test01: failed - expected: \"\" returned: \"" + emptyNetwork.socialButterfly() + "\"",
          true, emptyNetwork.socialButterfly().equals(""));
    } catch (Exception e) {
      fail("test01: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test02_avgFriendsValid() {
    try {
      assertEquals(2, smNetwork.averageFriendsPerPerson(),
          "test02: failed - expected: 2 returned: \"" + smNetwork.averageFriendsPerPerson() + "\"");

      assertEquals(3, mdNetwork.averageFriendsPerPerson(),
          "test02: failed - expected: 3 returned: \"" + mdNetwork.averageFriendsPerPerson() + "\"");

      assertEquals(3, lgNetwork.averageFriendsPerPerson(),
          "test02: failed - expected: 3 returned: \"" + lgNetwork.averageFriendsPerPerson() + "\"");

      assertEquals(0, emptyNetwork.averageFriendsPerPerson(),
          "test02: failed - expected: 3 returned: \"" + emptyNetwork.averageFriendsPerPerson()
              + "\"");

    } catch (Exception e) {
      fail("test02: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test
  public final void test03_influencerValid() {
    try {
      assertEquals(
          "test03: failed - expected: \"Lilly\" returned: \"" + smNetwork.influencer() + "\"", true,
          smNetwork.influencer().equals("Lilly"));

      assertEquals(
          "test03: failed - expected: \"Alex\" returned: \"" + mdNetwork.influencer() + "\"", true,
          mdNetwork.influencer().equals("Alex"));

      assertEquals(
          "test03: failed - expected: \"Margot\" returned: \"" + lgNetwork.influencer() + "\"",
          true, lgNetwork.influencer().equals("Margot"));

      assertEquals(
          "test03: failed - expected: \"\" returned: \"" + emptyNetwork.influencer() + "\"", true,
          emptyNetwork.influencer().equals(""));

    } catch (Exception e) {
      fail("test03: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test
  public final void test04_mutualFriends() {
    try {
      Set<String> testSet = new HashSet<String>();

      assertEquals(
          "test04: failed, expected empty set, recieved: "
              + emptyNetwork.mutualFriends("Lilly", "Scott"),
          true, emptyNetwork.mutualFriends("Lilly", "Scott").equals(testSet));

      assertEquals(
          "test04: failed, expected empty set, recieved: "
              + smNetwork.mutualFriends("Lilly", "Scott"),
          true, smNetwork.mutualFriends("Lilly", "Scott").equals(testSet));

      assertEquals(
          "test04: failed, expected empty set, recieved: " + smNetwork.mutualFriends("Lilly", ""),
          true, smNetwork.mutualFriends("Lilly", "").equals(testSet));

      testSet.add("Edward");
      testSet.add("Addison");
      testSet.add("Jess");


      assertEquals(
          "test04: failed, expected [\"Edward\", \"Addison\",\"Jess\"], recieved: "
              + mdNetwork.mutualFriends("Alex", "Bailey"),
          true, mdNetwork.mutualFriends("Alex", "Bailey").equals(testSet));

      testSet = new HashSet<String>();
      testSet.add("Cory");
      testSet.add("Carly");
      testSet.add("Alexis");

      assertEquals(
          "test04: failed, expected [\"Cory\", \"Carly\",\"Alexis\"], recieved: "
              + lgNetwork.mutualFriends("Drew", "Megan"),
          true, lgNetwork.mutualFriends("Drew", "Megan").equals(testSet));

      testSet = null;

    } catch (Exception e) {
      fail("test04: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test(timeout = 600000)
  public final void test05_haveSeenMeme() {

    try {
      Set<String> testSet = new HashSet<String>();

      assertEquals(
          "test05: failed, expected empty set, recieved: " + emptyNetwork.haveSeenMeme("Test", 3),
          true, emptyNetwork.haveSeenMeme("Test", 3).equals(testSet));

      assertEquals("test05: failed, expected empty set, recieved: " + smNetwork.haveSeenMeme("", 1),
          true, smNetwork.haveSeenMeme("", 1).equals(testSet));

      assertEquals(
          "test05: failed, expected empty set, recieved: " + smNetwork.haveSeenMeme("Aaron", 0),
          true, smNetwork.haveSeenMeme("Aaron", 0).equals(testSet));

      testSet.add("Aaron");

      assertEquals(
          "test05: failed, expected [\"Aaron\"], recieved: " + smNetwork.haveSeenMeme("Aaron", 1),
          true, smNetwork.haveSeenMeme("Aaron", 1).equals(testSet));

      testSet.add("Lilly");

      assertEquals(
          "test05: failed, expected [\"Aaron\",\"Lilly\"], recieved: "
              + smNetwork.haveSeenMeme("Aaron", 2),
          true, smNetwork.haveSeenMeme("Aaron", 2).equals(testSet));

      testSet.add("Scott");

      assertEquals(
          "test05: failed, expected [\"Aaron\",\"Lilly\", \"Scott\"], recieved: "
              + smNetwork.haveSeenMeme("Aaron", 3),
          true, smNetwork.haveSeenMeme("Aaron", 3).equals(testSet));

      testSet.add("Malika");

      assertEquals(
          "test05: failed, expected [\"Aaron\",\"Lilly\",\"Scott\",\"Malika\"], recieved: "
              + smNetwork.haveSeenMeme("Aaron", 4),
          true, smNetwork.haveSeenMeme("Aaron", 4).equals(testSet));

      testSet = new HashSet<String>();
      testSet.add("Edward");
      testSet.add("Bailey");
      testSet.add("Alex");
      testSet.add("Addison");

      assertEquals(
          "test05: failed, expected [\"Edward\",\"Bailey\",\"Alex\",\"Addison\"], recieved: "
              + mdNetwork.haveSeenMeme("Edward", 2),
          true, mdNetwork.haveSeenMeme("Edward", 2).equals(testSet));

      testSet.add("Jess");
      testSet.add("Mel");
      assertEquals(
          "test05: failed, expected [\"Edward\",\"Bailey\",\"Alex\",\"Addison\",\"Jess\",\"Mel\"], recieved: "
              + mdNetwork.haveSeenMeme("Edward", 3),
          true, mdNetwork.haveSeenMeme("Edward", 3).equals(testSet));

      testSet = new HashSet<String>();
      testSet.add("Sasha");
      testSet.add("Evan");
      testSet.add("Matt");
      testSet.add("Kerry");
      testSet.add("Todd");

      assertEquals(
          "test05: failed, expected [\"Sasha\", \"Evan\",\"Matt\",\"Kerry\",\"Todd\"], recieved: "
              + lgNetwork.haveSeenMeme("Sasha", 4),
          true, lgNetwork.haveSeenMeme("Sasha", 4).equals(testSet));

      testSet = null;

    } catch (Exception e) {
      fail("test05: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test
  public final void test06_youMayKnow() {
    try {
      Set<String> testSet = new HashSet<String>();

      assertEquals(
          "test06: failed, expected empty set, recieved: " + emptyNetwork.youMayKnow("Test"), true,
          emptyNetwork.youMayKnow("Test").equals(testSet));

      assertEquals("test06: failed, expected empty set, recieved: " + smNetwork.youMayKnow(""),
          true, smNetwork.youMayKnow("").equals(testSet));


      testSet.add("Malika");

      assertEquals(
          "test06: failed, expected [\"Malika\"], recieved: " + smNetwork.youMayKnow("Lilly"), true,
          smNetwork.youMayKnow("Lilly").equals(testSet));

      testSet = new HashSet<String>();
      testSet.add("Bailey");
      testSet.add("Alex");

      assertEquals("test06: failed, expected "+testSet+", recieved: " + mdNetwork.youMayKnow("Riley"),
          true, mdNetwork.youMayKnow("Riley").equals(testSet));

      testSet = new HashSet<String>();
      testSet.add("Jennifer");

      assertEquals(
          "test06: failed, expected "+testSet+", recieved: " + lgNetwork.youMayKnow("Mason"),
          true, lgNetwork.youMayKnow("Mason").equals(testSet));

      testSet = null;

    } catch (Exception e) {
      fail("test06: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test
  public final void test07_isFriendsGroup() {
    try {

      HashSet<String> testSet = new HashSet<String>();
      assertEquals(
          "test07: failed, expected false, receieved: " + emptyNetwork.isFriendGroup(testSet),
          false, emptyNetwork.isFriendGroup(testSet));

      assertEquals("test07: failed, expected false, receieved: " + smNetwork.isFriendGroup(testSet),
          false, smNetwork.isFriendGroup(testSet));

      testSet.add("Aaron");
      assertEquals(
          "test07: failed, expected false, receieved: " + emptyNetwork.isFriendGroup(testSet),
          false, emptyNetwork.isFriendGroup(testSet));

      assertEquals("test07: failed, expected false, receieved: " + smNetwork.isFriendGroup(testSet),
          false, smNetwork.isFriendGroup(testSet));

      testSet.add("Lilly");
      assertEquals(
          "test07: failed, expected false, receieved: " + emptyNetwork.isFriendGroup(testSet),
          false, emptyNetwork.isFriendGroup(testSet));

      assertEquals("test07: failed, expected true, receieved: " + smNetwork.isFriendGroup(testSet),
          true, smNetwork.isFriendGroup(testSet));

      testSet.add("Scott");
      assertEquals(
          "test07: failed, expected false, receieved: " + emptyNetwork.isFriendGroup(testSet),
          false, emptyNetwork.isFriendGroup(testSet));

      assertEquals("test07: failed, expected false, receieved: " + smNetwork.isFriendGroup(testSet),
          false, smNetwork.isFriendGroup(testSet));


    } catch (Exception e) {
      fail("test07: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test
  public final void test08_sixDegrees() {
    try {
      assertEquals("test08: failed, expected true", true, emptyNetwork.sixDegreesOfSeparation());
      assertEquals("test08: failed, expected true", true, smNetwork.sixDegreesOfSeparation());
      assertEquals("test08: failed, expected true", true, mdNetwork.sixDegreesOfSeparation());
      assertEquals("test08: failed, expected false", false, lgNetwork.sixDegreesOfSeparation());
    } catch (Exception e) {
      fail("test08: failed - unexpected exception occurred: " + e.getMessage());
    }
  }

  @Test(timeout = 60000)
  public final void test09_socialLadder() {
    try {
      ArrayList<String> testList = new ArrayList<String>();
      assertEquals(
          "test09: failed, expected [], returned: " + emptyNetwork.socialLadder("Test1", "Test2"),
          true, emptyNetwork.socialLadder("Test1", "Test2").equals(testList));

      testList.add("Aaron");
      testList.add("Lilly");
      testList.add("Scott");
      assertEquals(
          "test09: failed, expected [\"Aaron\",\"Lilly\",\"Scott\"], returned: "
              + smNetwork.socialLadder("Aaron", "Scott"),
          true, smNetwork.socialLadder("Aaron", "Scott").equals(testList));

      testList = new ArrayList<String>();
      testList.add("Mel");
      testList.add("Addison");
      testList.add("Alex");
      testList.add("Jess");
      testList.add("Riley");
      testList.add("Daniel");
      assertEquals(
          "test09: failed, expected "+testList+", returned: "
              + mdNetwork.socialLadder("Mel", "Daniel"),
          true, mdNetwork.socialLadder("Mel", "Daniel").equals(testList));
      testList = null;
    } catch (Exception e) {
      fail("test09: failed - unexpected exception occurred: " + e.getMessage());
    }
  }
  @Test(timeout = 60000)
  public final void test10_socialLadder() {
    try {
      HashSet<String> testSet = new HashSet<String>();
      assertEquals(
          "test10: failed, expected empty string, returned: " + emptyNetwork.glue(testSet),
          true, emptyNetwork.glue(testSet).equals(""));
      

      testSet.add("Jess");
      testSet.add("Riley");
      testSet.add("Daniel");
      assertEquals(
          "test10: failed, expected empty string, returned: " + emptyNetwork.glue(testSet),
          true, emptyNetwork.glue(testSet).equals(""));
      
      assertEquals(
          "test10: failed, expected Riley, returned: " + mdNetwork.glue(testSet),
          true, mdNetwork.glue(testSet).equals("Riley"));
      
      testSet.add("Alex");
      
      assertEquals(
          "test10: failed, expected empty string, returned: " + emptyNetwork.glue(testSet),
          true, emptyNetwork.glue(testSet).equals("Jess"));

      testSet = null;
    } catch (Exception e) {
      fail("test10: failed - unexpected exception occurred: " + e.getMessage());
    }
  }
}

