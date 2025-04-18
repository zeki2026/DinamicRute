package com.chapter.bd.tools.app.chapterbd_tools.service;

import java.util.ArrayList;
import java.util.List;

import com.chapter.bd.tools.app.chapterbd_tools.exceptions.ObjectNotConvertedException;
import com.chapter.bd.tools.app.chapterbd_tools.models.CrewStatusDetailDto;
import com.chapter.bd.tools.app.chapterbd_tools.repository.CrewDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CrewStatusServiceImpl implements CrewStatusService{


    private CrewDetailsRepository crewDetail;

    private final ObjectMapper objectMapper;


    public CrewStatusServiceImpl(CrewDetailsRepository crewDetail){
        this.crewDetail = crewDetail;
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // public String getCrewStatusDetail(){
    //     return this.crewDetail.getCrewStatusData();

    // }

    
    @Override
    public List<CrewStatusDetailDto> getCrewStatus() {
        String jsonData = crewDetail.getData("{ ? = call RCREDITO.FNCOREBDCREW_STATUS() }");

        if(jsonData == null) {
            List<CrewStatusDetailDto> ls = new ArrayList<>();

            ls.add(new CrewStatusDetailDto(22L,22,"ddsa",3,3,3,3));
            return  ls;
        }
        try {
            JsonNode root = objectMapper.readTree(jsonData);
            JsonNode dataNode = root.path("data");
            if(dataNode.isNull()) throw new ObjectNotConvertedException("Es vacios");
            return objectMapper.convertValue(dataNode, new TypeReference<List<CrewStatusDetailDto>>() {});
        } catch (JsonProcessingException e) {
            throw new DataAccessException("Error parsing JSON data", e) {};
        }
    }
} 
