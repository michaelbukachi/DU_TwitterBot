package com.dita.dev.twitterbot;
import java.io.IOException;
public class Main {
    public static void main(String [] args) throws IOException{
       Bot  bot = new Bot();
       String github_link = "https://github.com/mtotodev05";
       System.out.println("Twitter Bot [By Brian Kamau] @" +github_link);
        //System.out.println(bot.getBotCredentials().toString()); Try to See if Its reading
        bot.retweetByHash(new String[]{bot.getBotCredentials().get(4)}); //Replace with Hash Tag.
    }
}
