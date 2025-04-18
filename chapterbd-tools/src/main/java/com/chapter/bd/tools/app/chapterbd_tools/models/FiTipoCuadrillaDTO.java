package com.chapter.bd.tools.app.chapterbd_tools.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FiTipoCuadrillaDTO {

    @JsonProperty("FITIPOCUADRILLA")
    private String tipoCuadrilla;
    
    @JsonProperty("FCDESCRIPCION")
    private String descripcion;

    public FiTipoCuadrillaDTO(){
        
    }

    public String getTipoCuadrilla() {
        return tipoCuadrilla;
    }

    public void setTipoCuadrilla(String tipoCuadrilla) {
        this.tipoCuadrilla = tipoCuadrilla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
