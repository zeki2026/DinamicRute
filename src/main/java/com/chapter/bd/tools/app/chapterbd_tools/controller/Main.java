package com.chapter.bd.tools.app.chapterbd_tools.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.chapter.bd.tools.app.chapterbd_tools.service.DinamicRuteService;
import com.chapter.bd.tools.app.chapterbd_tools.service.TelegramBot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class Main {


    private TelegramBot bot;

    // private DinamicRuteService dinamicRuteService;

    // public Main(TelegramBot bot, DinamicRuteService dinamicRuteService) {
    //     this.bot = bot;
    //     this.dinamicRuteService = dinamicRuteService;
    // }

    @GetMapping("/bot/{message}")
    public String bot(@PathVariable String message){

        this.bot.sendMessage(message);

        return "Se mando el mensaje al bot";
    }

    // @GetMapping("/valid")
    // public Map<String,Object> valid(){

    //     return this.dinamicRuteService.getValid();
    // }

    @GetMapping("/path")
    public String getMethodName(@RequestParam String param) {
        return "String()";
    }
    

    @GetMapping("/test/{message}")
    public Map<String,Object> getTest(@PathVariable String message){

        Map<String,Object> mapMessage = new HashMap<>();

        mapMessage.put("message", message);

        return mapMessage;
    }
}
