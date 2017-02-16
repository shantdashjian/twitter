package project;

import java.io.IOException;
/**
 * This program reads a long tweet from a file and breaks it into
 * 140 or less character long pieces and writes them to a new file
 * It also tags the final messages to show which part in the
 * sequence each is
 *
 * @author Shaun Dashjian
 * @author Stefan Fuller
 * @version 1.0
 */
public class Twitter {

    public static void main(String[] args) throws IOException {
        Tweet tweet= new Tweet(140);
        tweet.readTweetFromFileAndTag("JessicasTweet.txt"); //text input
        tweet.writeTweetToFile("PublicTweet.txt"); //writing text input to a secondary .txt file
    }

}