package com.chapter.bd.tools.app.chapterbd_tools.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrewStatusDetailDto {
    @JsonProperty("cuadrilla")
    private Long crew;

    @JsonProperty("estatus")
    private Integer status;

    @JsonProperty("descripcion")
    private String crewType;

    @JsonProperty("maxClientes")
    private Integer maxCu;

    @JsonProperty("minClientes")
    private Integer minCu;

    @JsonProperty("numEmpleados")
    private Integer employeeCounter;

    @JsonProperty("numTareas")
    private Integer dutyCounter;

    public CrewStatusDetailDto(){
        
    }
    
    public CrewStatusDetailDto(Long crew, Integer status, String crewType, Integer maxCu, Integer minCu,
        Integer employeeCounter, Integer dutyCounter) {
        this.crew = crew;
        this.status = status;
        this.crewType = crewType;
        this.maxCu = maxCu;
        this.minCu = minCu;
        this.employeeCounter = employeeCounter;
        this.dutyCounter = dutyCounter;
    }
    public Long getCrew() {
        return crew;
    }
    public void setCrew(Long crew) {
        this.crew = crew;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getCrewType() {
        return crewType;
    }
    public void setCrewType(String crewType) {
        this.crewType = crewType;
    }
    public Integer getMaxCu() {
        return maxCu;
    }
    public void setMaxCu(Integer maxCu) {
        this.maxCu = maxCu;
    }
    public Integer getMinCu() {
        return minCu;
    }
    public void setMinCu(Integer minCu) {
        this.minCu = minCu;
    }
    public Integer getEmployeeCounter() {
        return employeeCounter;
    }
    public void setEmployeeCounter(Integer employeeCounter) {
        this.employeeCounter = employeeCounter;
    }
    public Integer getDutyCounter() {
        return dutyCounter;
    }
    public void setDutyCounter(Integer dutyCounter) {
        this.dutyCounter = dutyCounter;
    }
    
}
