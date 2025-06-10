package com.chapter.bd.tools.app.chapterbd_tools.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chapter.bd.tools.app.chapterbd_tools.exceptions.ObjectNotConvertedException;
import com.chapter.bd.tools.app.chapterbd_tools.models.ErrorModel;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler({ObjectNotConvertedException.class})
    public ResponseEntity<ErrorModel> repositoryFormatError(Exception e){

        ErrorModel error = new ErrorModel();

        error.setDate(new Date());
        error.setMessage("Error de formato");
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(error.getStatus()).body(error);

    }

}
