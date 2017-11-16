package com.dita.dev.twitterbot;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Bot {

    public static void main(String[] args) {
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
    
    
    public static class TweetListener implements StatusListener {

        @Override
        public void onStatus(Status status) {
            
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice sdn) {
            
        }

        @Override
        public void onTrackLimitationNotice(int i) {
            
        }

        @Override
        public void onScrubGeo(long l, long l1) {
            
        }

        @Override
        public void onStallWarning(StallWarning sw) {
            
        }

        @Override
        public void onException(Exception excptn) {
            
        }
        
    }
}
