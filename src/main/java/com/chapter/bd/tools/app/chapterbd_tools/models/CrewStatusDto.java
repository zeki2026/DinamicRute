package com.chapter.bd.tools.app.chapterbd_tools.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrewStatusDto {

    @JsonProperty("COUNTER")
    private String counter;

    @JsonProperty("STATUS")
    private String status;

    @JsonProperty("DESCRIPTION")
    private String description;

    public CrewStatusDto(){
        
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
