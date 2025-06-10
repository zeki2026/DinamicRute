package com.chapter.bd.tools.app.chapterbd_tools.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrewStatusDto {

    @JsonProperty("numCuadrillas")
    private String counter;

    @JsonProperty("estatus")
    private String status;

    @JsonProperty("descripcion")
    private String description;

    public CrewStatusDto(){
        
    }


    public CrewStatusDto(String counter, String status, String description) {
        this.counter = counter;
        this.status = status;
        this.description = description;
    }


    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    
}
