package com.chapter.bd.tools.app.chapterbd_tools.components;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.chapter.bd.tools.app.chapterbd_tools.models.DetallesCuadrillasDto;
import com.chapter.bd.tools.app.chapterbd_tools.models.ServiceHeader;
import com.chapter.bd.tools.app.chapterbd_tools.models.CrewStatusDto;
import com.chapter.bd.tools.app.chapterbd_tools.service.DinamicRuteService;



@SuppressWarnings("deprecation")
@Component
public class TelegrambotListener extends TelegramLongPollingBot {

    @Value("${telegram.username}")
    private String botUsername;

    @Value("${telegram.endpoint}")
    private String botPath;

    @Value("${telegram.token}")
    private String botToken;

    private DinamicRuteService dinamicRuteService;

    public TelegrambotListener(DinamicRuteService dinamicRuteService){
        this.dinamicRuteService = dinamicRuteService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String message;
            ServiceHeader response = new ServiceHeader("",
                    "",
                    "function",
                    "java:/jdbc/Oracle",
                    "on");

            switch (messageText) {
                case "/start":
                    sendMessage(chatId, "Hola, soy tu bot de Telegram! Y te ayudare con todo lo relacionado a la ruta dinamica. Ejecuta el comando /help para conocer las interacciones que tengo habilitadas.");
                break;
                case "/help":
                    StringBuilder str = new StringBuilder();
                    str.append("/stCuadrillas\nValidar status de cuadrillas\n");
                    str.append("/stCuadrillasD\nValidar el detalle de las cuadrillas\n");
                    str.append("/stCuadrillasS\nValidar status de cuadrillas satelital\n");
                    str.append("/stCuadrillasSD\nValidar el detalle de las cuadrillas satelital\n");
                    sendMessage(chatId, str.toString());
                    break;
                case "/stCuadrillas":
                    response.setQuery("RCREDITO.FNADSSLG0999(q'[SELECT count(1) as conteo, b.FISTATUS as statusCuadrillas FROM RCREDITO.TAGERENCIASFFM a left join RCREDITO.TARUTADINJSON b on a.FIDEPTOID = b.FIDEPTODID inner join rcredito.tacuadrillas on fiidcuadrilla = a.FIDEPTOID where not exists (select 1 from TACUADRILLASATE ts where ts.FIIDCUADRILLASAT = a.FIDEPTOID ) group by b.fistatus ]')");
                    message =  dinamicRuteService.createMessageStatusCuadrilla(
                        dinamicRuteService.getQueryResponse(
                            CrewStatusDto.class,response
                            )
                        );
                    sendMessage(chatId, message);
                break;
                case "/stCuadrillasD":
                    response.setQuery("RCREDITO.FNADSSLG0999(q'[ SELECT a.fideptoid as cuadrilla, b.FISTATUS as status  FROM RCREDITO.TAGERENCIASFFM a  left join RCREDITO.TARUTADINJSON b  on a.FIDEPTOID = b.FIDEPTODID  inner join rcredito.tacuadrillas on fiidcuadrilla = a.FIDEPTOID  where not  exists (select 1  from TACUADRILLASATE ts where ts.FIIDCUADRILLASAT = a.FIDEPTOID ) and b.fistatus is null or b.FISTATUS=0 order by a.fideptoid ]')");
                    message =  dinamicRuteService.createMessageDetallesCuadrilla(
                        dinamicRuteService.getQueryResponse(
                            DetallesCuadrillasDto.class,response
                            )
                        );
                    sendMessage(chatId, message);
                break;
                case "/stCuadrillasS":
                    response.setQuery("RCREDITO.FNADSSLG0999(q'[SELECT count(1) as conteo, b.FISTATUS as statusCuadrillas FROM RCREDITO.TAGERENCIASFFM a left join RCREDITO.TARUTADINJSON b on a.FIDEPTOID = b.FIDEPTODID inner join rcredito.tacuadrillas on fiidcuadrilla = a.FIDEPTOID where exists (select 1 from TACUADRILLASATE ts where ts.FIIDCUADRILLASAT = a.FIDEPTOID ) group by b.fistatus ]')");
                    message =  dinamicRuteService.createMessageStatusCuadrilla(
                        dinamicRuteService.getQueryResponse(
                            CrewStatusDto.class,response
                            )
                        );
                    sendMessage(chatId, message);
                break;
                case "/stCuadrillasSD":
                    response.setQuery("RCREDITO.FNADSSLG0999(q'[ SELECT a.fideptoid as cuadrilla, b.FISTATUS as status  FROM RCREDITO.TAGERENCIASFFM a  left join RCREDITO.TARUTADINJSON b  on a.FIDEPTOID = b.FIDEPTODID  inner join rcredito.tacuadrillas on fiidcuadrilla = a.FIDEPTOID  where exists (select 1  from TACUADRILLASATE ts where ts.FIIDCUADRILLASAT = a.FIDEPTOID ) and b.fistatus is null or b.FISTATUS=0 order by a.fideptoid ]')");
                    message =  dinamicRuteService.createMessageDetallesCuadrilla(
                        dinamicRuteService.getQueryResponse(
                            DetallesCuadrillasDto.class,response
                            )
                        );
                    sendMessage(chatId, message);
                break;
                case "/estatusCuadrillas":
                    message =  dinamicRuteService.statusCrews();
                    sendMessage(chatId, message);
                break;
                case "/detalleCuadrillas":
                    message =  dinamicRuteService.detailsCrews();
                    sendMessage(chatId, message);
                break;
                default:
                    sendMessage(chatId, "No entiendo ese comando.");
                break;
            }

            
        }
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


}
