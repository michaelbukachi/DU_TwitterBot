package com.dita.dev.twitterbot;
/*
Brian Kamau
 */
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.logging.Level;

public class Bot {
    private final String CONSUMER_KEY ="WRLDGFBLa5xVRyCKoGUSMosp4";
    private final String CONSUMER_KEY_SECRET="mK0nKAmVVjG9MTOqsspOrpbgYHoSHbJCLU4FlXbtpRzDLGAFzu";
    private final String ACCESS_TOKEN ="939222012609925120-cnzVyBniwpe4D4Smwhbc4trhBtAz1PN";
    private final String ACCESS_TOKEN_SECRET="od2XxN4BgF4IBdLS4k4hKJYQWBsF7L7YcuJpN7rCOzgja";
    private File logFile;
    private BufferedWriter logFileWriter;

    private Twitter twitter;
    private AccessToken accesstoken;

    public Bot(){
        this.twitter = new TwitterFactory().getInstance();

        logFile = new File("./log");
        try{
            logFileWriter = new BufferedWriter(new FileWriter(logFile));
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        try{
            this.start();
        }catch (TwitterException ex){
            try{
                logFileWriter.append(ex.getMessage());
                logFileWriter.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }


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
    public void retweetByHash(String[] hash){
        final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer(CONSUMER_KEY,CONSUMER_KEY_SECRET);
        twitterStream.setOAuthAccessToken(accesstoken);

        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                try{
                    String stat = status.getText();
                    if(!status.isRetweet()){
                        if("en".equals(status.getLang())){

                            System.out.println(status.getText()+":@"+status.getUser().getScreenName());
                            logFileWriter.append(stat);
                            logFileWriter.flush();
                        }
                    }

                }catch (IOException ex){
                    ex.printStackTrace();
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
        twitterStream.filter(new FilterQuery().track(hash));    }
    
    

}
