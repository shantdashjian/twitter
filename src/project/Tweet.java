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
	private  int tweetMaxLength;

	public Tweet() {
		messages = new ArrayList<>();
		this.tweetMaxLength = 140;
	}

	public Tweet(int tweetMaxLength) {
		this();
		this.tweetMaxLength = tweetMaxLength;
	}

	public void setTweetMaxLength(int tweetMaxLength){
		this.tweetMaxLength = tweetMaxLength;
	}

	public int getTweetMaxLength() {
		return tweetMaxLength;
	}

	public void readTweetFromFile(String fileName) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() <= getTweetMaxLength()) {
					messages.add(line);
				} else {
					do {
						String first140Characters = line.substring(0, getTweetMaxLength());
						String remainingPartAfter140Characters = line.substring(getTweetMaxLength());
						Pattern beginWithNonWordCharacterPattern = Pattern.compile("^\\W.+$");
						Matcher beginWithNonWordCharacterMatcher = beginWithNonWordCharacterPattern.
													matcher(remainingPartAfter140Characters);
						Pattern endWithNonWordCharacterPattern = Pattern.compile("^.+\\W$");
						Matcher endWithNonWordCharacterdMatcher = endWithNonWordCharacterPattern.
													matcher(first140Characters);

						if (beginWithNonWordCharacterMatcher.find() ||
								endWithNonWordCharacterdMatcher.find()){
							messages.add(first140Characters);
							line = remainingPartAfter140Characters;
						} else {

							Pattern removeIncompleteWordFromTheEndPattern = Pattern.compile("^(.+\\W)(.+)$");
							Matcher removeIncompleteWordFromTheEndMatcher =
									removeIncompleteWordFromTheEndPattern.matcher(first140Characters);
							removeIncompleteWordFromTheEndMatcher.find();
							first140Characters = removeIncompleteWordFromTheEndMatcher.group(1);
							line = removeIncompleteWordFromTheEndMatcher.group(2)
									+ remainingPartAfter140Characters;
							messages.add(first140Characters);
						}

					} while (line.length() > getTweetMaxLength());

					messages.add(line);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}

	}

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

	public List<String> getMessages(){
		return messages;
	}

	public void display() {
		System.out.println(messages);
	}
}