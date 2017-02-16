package project;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TweetTests {
	private Tweet tweet;

	@Before
	public void setUp() throws Exception {
		tweet = new Tweet(10);
	}

	@After
	public void tearDown() throws Exception {
		tweet = null;
	}

	@Test
	public void test_readTweetFromFile_testFile_should_get_broken_into_3_tweets() {
		tweet.setTweetMaxLength(10);
        tweet.readTweetFromFile("testFile21Characters.txt", false);
        String expectedTweet1 = "My name ";
        String expectedTweet2 = "isn't ";
        String expectedTweet3 = "Jessica";
        int expectedOutcome = 3;
        assertEquals(expectedOutcome, tweet.getMessages().size());
        assertEquals(expectedTweet1, tweet.getMessages().get(0));
        assertEquals(expectedTweet2, tweet.getMessages().get(1));
        assertEquals(expectedTweet3, tweet.getMessages().get(2));
	}

	@Test
	public void test_readTweetFromFile_testFile_should_get_broken_into_2_tweets() {
		tweet.setTweetMaxLength(10);
        tweet.readTweetFromFile("testFile20Characters.txt", false);
        String expectedTweet1 = "My name is";
        String expectedTweet2 = " not Jessi";
        int expectedOutcome = 2;
        assertEquals(expectedOutcome, tweet.getMessages().size());
        assertEquals(expectedTweet1, tweet.getMessages().get(0));
        assertEquals(expectedTweet2, tweet.getMessages().get(1));
	}

	@Test
	public void test_tagMessages_testFile_should_get_broken_into_3_tweets() {
		tweet.setTweetMaxLength(37);
		List<String> messages = new ArrayList<>();
		messages.add("I am making a huge ");
		messages.add("announcement this ");
		messages.add("week.");
		tweet.setMessages(messages);
        String expectedTweet1 = "I am making a huge  (Tweet 1 of 3)";
        String expectedTweet2 = "announcement this  (Tweet 2 of 3)";
        String expectedTweet3 = "week. (Tweet 3 of 3)";
        tweet.tagMessages();
        int expectedOutcome = 3;
        assertEquals(expectedOutcome, tweet.getMessages().size());
        assertEquals(expectedTweet1, tweet.getMessages().get(0));
        assertEquals(expectedTweet2, tweet.getMessages().get(1));
        assertEquals(expectedTweet3, tweet.getMessages().get(2));
	}

	@Test
	public void test_readTweetFromFileAndTag_testFile_should_get_broken_into_3_tweets() {
		tweet.setTweetMaxLength(37);
		tweet.readTweetFromFileAndTag("testFile42Characters.txt");
        String expectedTweet1 = "I am making a huge  (Tweet 1 of 3)";
        String expectedTweet2 = "announcement this  (Tweet 2 of 3)";
        String expectedTweet3 = "week. (Tweet 3 of 3)";
        int expectedOutcome = 3;
        assertEquals(expectedOutcome, tweet.getMessages().size());
        assertEquals(expectedTweet1, tweet.getMessages().get(0));
        assertEquals(expectedTweet2, tweet.getMessages().get(1));
        assertEquals(expectedTweet3, tweet.getMessages().get(2));
	}

}
