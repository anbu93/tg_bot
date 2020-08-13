tg_bot
=============

Based on `org.telegram:telegrambots:3.4`

### Overview
**tg_bot** is a wrapper for create simple Telegram bots on Java uing annotations.
See based rep [TelegramBots](https://github.com/rubenlagus/TelegramBots)


### Status
Indev

### Example

Dice randomizer bot

```java
public class ContextType {
	public static final int START = 0;
	public static final int MAIN = 1;
	public static final int CUSTOM = 2;
}


@TgBotContext(name = ContextType.START)
class StartContext extends TgContext {
    @TgBotCommandHandler(command = "/help")
    public void helpCommand(long user) {
        bot.sendMessage(user, "Use /start command for start.");
    }

    @TgBotCommandHandler(command = "/start")
    public void onStart(long user) {
        bot.sendMessage(user, "Hello! Use command for interaction with bot.");
        bot.changeContext(user, ContextType.MAIN);
    }
    
    @TgBotMessageHandler
    public void onReceiveAnyMessage(long user, String message) {
        bot.sendMessage(user, "Use /start command for start");
    }
}


@TgBotContext(name = ContextType.MAIN)
class MainContext extends TgContext {
    private Random random = new Random();
	
    @TgBotCommandHandler(command = "/help")
    public void helpCommand(long user) {
        bot.sendMessage(user, "Use bot buttons for randomize dices");
    }
    
    @TgBotCommandHandler(command = "/dice6")
    public void randomizeDice6(long user) {
        bot.sendMessage(user, "Random value: " + (random.nextInt(6) + 1));
    }
    
    @TgBotCommandHandler(command = "/dice6x2")
    public void randomizeDice6x2(long user) {
        bot.sendMessage(user, 
                "Dice 1: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 2: " + (random.nextInt(6) + 1));
    }
    
    @TgBotCommandHandler(command = "/dice6x4")
    public void randomizeDice6x4(long user) {
        bot.sendMessage(user, 
                "Dice 1: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 2: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 3: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 4: " + (random.nextInt(6) + 1));
    }
    
    @TgBotCommandHandler(command = "/dice6x6")
    public void randomizeDice6x6(long user) {
        bot.sendMessage(user, 
                "Dice 1: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 2: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 3: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 4: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 5: " + (random.nextInt(6) + 1) + "\n" +
                "Dice 6: " + (random.nextInt(6) + 1));
    }
    
    @TgBotCommandHandler(command = "/custom")
    public void customCommand(long user) {
        bot.sendMessage(user, "Please input dices count");
        bot.changeContext(user, ContextType.CUSTOM);
    }
    
    @TgBotMessageHandler
    public void onReceiveAnyMessage(long user, String message) {
        bot.sendMessage(user, "Use command buttons for interaction with bot");
    }
}


@TgBotContext(name = ContextType.CUSTOM)
class CustomDicesContext extends TgContext {
    private Random random = new Random();
    
    @TgBotMessageHandler
    public void handleCustomDicesCount(long user, String message) {
        try {
            int count = Integer.parseInt(message);
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < count; i++) {
                sb.append("Dice ").append(i + 1).append(":")
                        .append(random.nextInt(6) + 1).append("\n");
                bot.sendMessage(user, sb.toString());
                bot.changeContext(user, ContextType.MAIN);
            }
        } catch (Exception e) {
            bot.sendMessage(user, "Invalid number, try again input dices count");
        }
    }
    
    @TgBotCommandHandler(command = "/cancel")
    public void cancel(long user) {
        bot.sendMessage(user, "Custom dices cancelled");
        bot.changeContext(user, ContextType.MAIN);
    }
}


class Main {
    public static void main(String[] args) {
        TgBot bot = new TgBot(botId, botToken);
        bot.registerContext(new StartContext());
        bot.registerContext(new MainContext());
        bot.registerContext(new CustomDicesContext());
        bot.setDefaultContext(ContextType.START);
        bot.start();
    }
}
```

This is simple dice random tg bot.

A context is a stateless object that handles incoming commands and user messages. 
The user's context is stored in the `ContexthHolder` which you need to implement yourself 
(by default, the RuntimeContextHolder is used). 
Using multiple contexts, you can create a finite state machine for bot behavior.
Each context automatically refreshes the list of buttons for users when the context changes.