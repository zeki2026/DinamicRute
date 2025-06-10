package com.chapter.bd.tools.app.chapterbd_tools.models;

public class ServiceHeader {

    public String textSel;
    public String query;
    public String type;
    public String datasource;
    public String datos;


    public ServiceHeader(String textSel, String query, String type, String datasource, String datos) {
        this.textSel = textSel;
        this.query = query;
        this.type = type;
        this.datasource = datasource;
        this.datos = datos;
    }

    public ServiceHeader() {
    }

    public String getTextSel() {
        return textSel;
    }
    public void setTextSel(String textSel) {
        this.textSel = textSel;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDatasource() {
        return datasource;
    }
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
    public String getDatos() {
        return datos;
    }
    public void setDatos(String datos) {
        this.datos = datos;
    }

    

}
 