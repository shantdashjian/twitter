package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweet {
	private List<String> messages;
	private int tweetMaxLength;
	public static final int TAG_LENGTH = 17;

	/**
	 * No-arg Constructor instantiates the array list and
	 * sets the max tweet length to 140
	 */
	public Tweet() {
		messages = new ArrayList<>();
		this.tweetMaxLength = 140;
	}

	/**
	 * 1-arg constructor
	 * @param tweetMaxLength
	 */
	public Tweet(int tweetMaxLength) {
		this();
		this.tweetMaxLength = tweetMaxLength;
	}

	public void setTweetMaxLength(int tweetMaxLength) {
		this.tweetMaxLength = tweetMaxLength;
	}

	public int getTweetMaxLength() {
		return tweetMaxLength;
	}

	/**
	 * Reads the tweet from a file and breaks it into smaller pieces
	 * @param fileName
	 * @param tag
	 */
	public void readTweetFromFile(String fileName, boolean tag) {
		int finalMaxTweetLength = getTweetMaxLength();
		/*
		 * tag flag, when true, means keep space to tag each tweet piece with
		 * something like (Tweet 1 of 3)
		 */
		if (tag) {
			finalMaxTweetLength = finalMaxTweetLength -  TAG_LENGTH;
		}
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() <= finalMaxTweetLength) {
					messages.add(line);
				} else {
					do {
						String first140Characters = line.substring(0, finalMaxTweetLength);
						String remainingPartAfter140Characters = line.substring(finalMaxTweetLength);
						Pattern beginWithNonWordCharacterPattern = Pattern.compile("^\\W.+$");
						Matcher beginWithNonWordCharacterMatcher = beginWithNonWordCharacterPattern
								.matcher(remainingPartAfter140Characters);
						Pattern endWithNonWordCharacterPattern = Pattern.compile("^.+\\W$");
						Matcher endWithNonWordCharacterdMatcher = endWithNonWordCharacterPattern
								.matcher(first140Characters);

						if (beginWithNonWordCharacterMatcher.find() || endWithNonWordCharacterdMatcher.find()) {
							messages.add(first140Characters);
							line = remainingPartAfter140Characters;
						} else {

							Pattern removeIncompleteWordFromTheEndPattern = Pattern.compile("^(.+\\W)(.+)$");
							Matcher removeIncompleteWordFromTheEndMatcher = removeIncompleteWordFromTheEndPattern
									.matcher(first140Characters);
							removeIncompleteWordFromTheEndMatcher.find();
							first140Characters = removeIncompleteWordFromTheEndMatcher.group(1);
							line = removeIncompleteWordFromTheEndMatcher.group(2) + remainingPartAfter140Characters;
							messages.add(first140Characters);
						}

					} while (line.length() > finalMaxTweetLength);

					messages.add(line);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	/**
	 * Reads tweet from file and tags each piece with something
	 * like (Tweet 1 of 3)
	 * @param fileName
	 */
	public void readTweetFromFileAndTag(String fileName) {
		int tweetLengthMinusTagLength = tweetMaxLength - TAG_LENGTH;
		readTweetFromFile(fileName, true);
		if (tweetLengthMinusTagLength > 2) {
			tagMessages();
		}
	}

	/**
	 * tags each piece with something like (Tweet 1 of 3)
	 */
	public void tagMessages() {
		for (int i = 0; i < messages.size(); i++) {
			messages.set(i, messages.get(i) + " (Tweet " + (i+1) + " of " + messages.size() + ")");
		}

	}

	/**
	 * Writes tweets to a new file
	 * @param fileName
	 * @throws IOException
	 */
	public void writeTweetToFile(String fileName) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
		for (String message : messages) {
			printWriter.println(message);
		}
		printWriter.close();
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

}