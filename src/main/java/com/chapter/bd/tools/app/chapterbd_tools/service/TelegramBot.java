package com.chapter.bd.tools.app.chapterbd_tools.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@Service
public class TelegramBot {

    @Value("${telegram.name}")
    private String name;

    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.chatId}")
    private String chatId;

    private Client client;
    
    private WebTarget baseTarget;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class); 

    @PostConstruct
    void initClient(){
        client = ClientBuilder.newClient();

        baseTarget = client.target("https://api.telegram.org/bot{token}")
                            .resolveTemplate("token", this.token);
    }

    public void sendMessage(String message){
        Response response = baseTarget.path("sendMessage")
                            .queryParam("chat_id",chatId)
                            .queryParam("text", message)
                            .request()
                            .get();
        
        if(response.getStatus() == 200){
            logger.info("si se mando el mensaje");
        } 
    }

    @PreDestroy
    private void close(){
        client.close();
    }


}
