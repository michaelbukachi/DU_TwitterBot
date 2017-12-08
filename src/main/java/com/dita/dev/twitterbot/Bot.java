package com.dita.dev.twitterbot;
/*
Brian Kamau
 */
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Bot {
    private final String CONSUMER_KEY ="";
    private final String CONSUMER_KEY_SECRET="";
    private final String ACCESS_TOKEN ="";
    private final String ACCESS_TOKEN_SECRET="";
    private File logFile;
    private BufferedWriter logFileWriter;

    private Twitter twitter;
    private AccessToken accesstoken;

    public static void main(String[] args) {

        
    }
    public Bot(){
        this.twitter = new TwitterFactory().getInstance();
    }

    private void start() throws TwitterException{
        this.twitter.setOAuthConsumer(CONSUMER_KEY,CONSUMER_KEY_SECRET);
        accesstoken = new AccessToken(ACCESS_TOKEN,ACCESS_TOKEN_SECRET);
        this.twitter.setOAuthAccessToken(accesstoken);
    }
    public void readTimeLine() throws TwitterException{
        ResponseList<Status> homeList = this.twitter.getHomeTimeline();
        for(Status status: homeList){
            System.out.println("Sent By"+status.getUser().getScreenName()+"-"+status.getUser().getName()+"\n"+status.getText()+"\n");
        }
    }

    public void retweetByID(long twitterID){
        final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        twitterStream.setOAuthConsumer(CONSUMER_KEY,CONSUMER_KEY_SECRET);
        twitterStream.setOAuthAccessToken(accesstoken);

        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                try{
                    System.out.println(status.getText()+":@"+status.getUser().getScreenName());
                    if(!status.isRetweet()){
                        twitter.retweetStatus(status.getId());
                    }

                }catch(TwitterException e){
                    try {
                        logFileWriter.append(e.getMessage());
                        logFileWriter.flush();
                    }catch(IOException ex){
                        ex.printStackTrace();
                        //ex.printStackTrace();
                        //Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();

            }
        };
        twitterStream.addListener(statusListener);
        twitterStream.filter(new FilterQuery().follow(new long[]{twitterID}));

    }
    
    

}
