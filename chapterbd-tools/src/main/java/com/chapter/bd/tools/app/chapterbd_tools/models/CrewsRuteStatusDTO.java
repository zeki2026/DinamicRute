package com.chapter.bd.tools.app.chapterbd_tools.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrewsRuteStatusDTO {


    @JsonProperty("FIDEPTOID")
    private String crew;

    @JsonProperty("FISTATUS")
    private String status;

    @JsonProperty("FITIPOCUADRILLA")
    private String crewType;

    public String getCrew() {
        return crew;
    }

    public CrewsRuteStatusDTO() {
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCrewType() {
        return crewType;
    }

    public void setCrewType(String crewType) {
        this.crewType = crewType;
    }

}
