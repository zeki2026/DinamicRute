package com.chapter.bd.tools.app.chapterbd_tools.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetallesCuadrillasDto {

    @JsonProperty("CUADRILLA")
    private String cuadrilla;

    @JsonProperty("STATUS")
    private String status;

    public DetallesCuadrillasDto() {
    }

    public String getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(String cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
