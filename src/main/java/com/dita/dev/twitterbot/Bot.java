package com.dita.dev.twitterbot;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Bot {

    public static void main(String[] args) {
        ConfigurationBuilder builer = new ConfigurationBuilder();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("*********************")
                .setOAuthConsumerSecret("******************************************")
                .setOAuthAccessToken("**************************************************")
                .setOAuthAccessTokenSecret("******************************************");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        // Rest of the code
    }
}
