package com.vova_cons.tg_bot.message_processor;

import com.vova_cons.tg_bot.TgBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by anbu on 14.08.20.
 **/
public class TelegramMessageProcessor extends TelegramLongPollingBot implements MessageProcessor {
    static {
        ApiContextInitializer.init();
    }
    private final TgBot bot;
    private final String id;
    private final String token;

    public TelegramMessageProcessor(TgBot bot, String id, String token) {
        this.bot = bot;
        this.id = id;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long uid = message.getChatId();
        String text = message.getText();
        try {
            bot.onReceiveMessage(uid, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return id;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void start() {
        //ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendMessage(long uid, String message, Collection<String> commands) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(uid);
        sendMessage.setText(message);

        if (!commands.isEmpty()) {
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboard.add(keyboardRow);
            int count = 0;
            for (String command : commands) {
                count++;
                keyboardRow.add(command);
                if (count % 3 == 0) {
                    keyboardRow = new KeyboardRow();
                    keyboard.add(keyboardRow);
                }
            }
            replyKeyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        try {
            sendApiMethod(sendMessage);
            return true;
        } catch (TelegramApiException e){
            e.printStackTrace();
            return false;
        }
    }
}
