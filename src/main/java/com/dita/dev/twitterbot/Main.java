package com.dita.dev.twitterbot;

import java.io.IOException;

/*
Kamau
 */
public class Main {


    public static void main(String [] args) throws IOException{
       Bot  bot = new Bot();
        //System.out.println(bot.getBotCredentials().toString()); Try to See if Its reading
        bot.retweetByHash(new String[]{"#KenyaYetu"}); //Replace with Hash Tag.

    }
}
