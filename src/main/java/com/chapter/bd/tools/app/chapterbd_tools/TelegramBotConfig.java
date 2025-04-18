package com.chapter.bd.tools.app.chapterbd_tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.chapter.bd.tools.app.chapterbd_tools.components.TelegrambotListener;


@Configuration
public class TelegramBotConfig {

    @Value("${telegram.webhook-host}")
    private String webhookHost; // URL base del webhook (por ejemplo, https://12345124.eu.ngrok.io)

    @Value("${telegram.token}")
    private String botToken; // Token del bot

    @Value("${telegram.username}")
    private String botUsername; // Nombre de usuario del bot



    // private static final Logger logger = LoggerFactory.getLogger(TelegramBotConfig.class);

    // @Bean("setNet")
    // public SetWebhook setWebhookInstance() {
    //     return SetWebhook.builder().url(webhookHost).build(); // Configura la URL del webhook
    // }

    // @Bean
    // public TelegrambotListener getBot(@Qualifier("setNet") SetWebhook setWebhook) throws TelegramApiException {


    //     TelegrambotListener telegrambotListener = new TelegrambotListener(setWebhook, botToken);

    //     telegrambotListener.onRegister();
    //     TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    //     telegramBotsApi.registerBot(telegrambotListener, setWebhook); // Registra el bot
        
    //     return telegrambotListener;
    // }

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegrambotListener telegrambotListener) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegrambotListener); // Registra el bot
        return telegramBotsApi;
    }

}