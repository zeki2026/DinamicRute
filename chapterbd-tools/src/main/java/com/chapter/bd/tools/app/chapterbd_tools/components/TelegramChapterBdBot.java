package com.chapter.bd.tools.app.chapterbd_tools.components;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;


// @Component
public class TelegramChapterBdBot extends SpringWebhookBot{
    @Value("${telegram.username}")
    private String botUsername;

    @Value("${telegram.endpoint}")
    private String botPath;

    @Value("${telegram.token}")
    private String botToken;

    public TelegramChapterBdBot(SetWebhook setWebhook, @Value("${telegram.token}") String botToken) {
        super(setWebhook,botToken);
    }
    
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                return new SendMessage(String.valueOf(chatId), "Hola, soy tu bot de Telegram!");
            } else {
                return new SendMessage(String.valueOf(chatId), "No entiendo ese comando.");
            }
        }
        return null;
    }

    public void sendDocument(long chatId, File document) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(document));

        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }
    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}


