package com.dita.dev.twitterbot;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
public class Bot {
    private File logFile;
    private BufferedWriter logFileWriter;
    Properties properties = new Properties();
    InputStream input ;
    private Twitter twitter;
    private AccessToken accesstoken;
    ArrayList<String> details = getBotCredentials();
    private final String CONSUMER_KEY = details.get(0);
    private final String CONSUMER_KEY_SECRET=details.get(1);
    private final String ACCESS_TOKEN =details.get(2);
    private final String ACCESS_TOKEN_SECRET=details.get(3);
    public Bot() throws IOException {
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
    public void retweetByHash(String[] hash){
        final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer(CONSUMER_KEY,CONSUMER_KEY_SECRET);
        twitterStream.setOAuthAccessToken(accesstoken);
        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                try{
                    String stat = status.getText();
                    StatusUpdate statusUpdate = new StatusUpdate(stat);
                    twitter.updateStatus(statusUpdate);
                    if(!status.isRetweet()){
                        if("en".equals(status.getLang())){

                            System.out.println(status.getText()+":@"+status.getUser().getScreenName());
                            logFileWriter.append(stat);
                            logFileWriter.flush();
                        }
                    }
                }catch (IOException ex){
                    ex.printStackTrace();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }@Override public void onTrackLimitationNotice(int i) { }@Override public void onScrubGeo(long l, long l1) { }@Override public void onStallWarning(StallWarning stallWarning) { }
            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
        };
        twitterStream.addListener(statusListener);
        twitterStream.filter(new FilterQuery().track(hash));    }
        public  ArrayList<String> getBotCredentials() throws IOException{
            ArrayList<String> botdetails = new ArrayList<String>();
            ArrayList<String> error = new ArrayList<String>();
            String error_String = "Not Able to Find File";
            error.add(error_String);
            try{
                File filename = new File("credentials.properties");
                input = new FileInputStream(filename);
                properties.load(input);
                botdetails.add(properties.getProperty("CONSUMER_KEY"));
                botdetails.add(properties.getProperty("CONSUMER_KEY_SECRET"));
                botdetails.add(properties.getProperty("ACCESS_TOKEN"));
                botdetails.add(properties.getProperty("ACCESS_TOKEN_SECRET"));
                botdetails.add(properties.getProperty("HASH_TAG"));
      }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                if(input!=null){
                    input.close();
                }
            }
        return botdetails;
        }
}
