package com.vova_cons.tg_bot.test;

import com.vova_cons.tg_bot.TgBot;

import java.io.File;
import java.util.Scanner;

/**
 * Created by anbu on 14.08.20.
 **/
public class TestMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            String id = scanner.nextLine();;
            String token = scanner.nextLine();
            TgBot bot = new TgBot(id, token);
            bot.registerContext(new StartContext());
            bot.registerContext(new MainContext());
            bot.registerContext(new CustomContext());
            bot.setDefaultContext(ContextType.START);
            bot.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
