package project;

import java.io.IOException;

public class Twitter {

    public static void main(String[] args) throws IOException {
        Tweet tweet= new Tweet(140);
        tweet.readTweetFromFile("JessicasTweet.txt"); //text input
        tweet.writeTweetToFile("PublicTweet.txt"); //writing text input to a secondary .txt file
    }

}