package com.chapter.bd.tools.app.chapterbd_tools.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chapter.bd.tools.app.chapterbd_tools.models.CrewStatusDetailDto;
import com.chapter.bd.tools.app.chapterbd_tools.service.CrewStatusService;

@RestController
@RequestMapping("/crews")
public class CrewStatusController {

    private CrewStatusService crewStatus;

    public CrewStatusController(CrewStatusService crewStatus){
        this.crewStatus = crewStatus;  
    }


    @GetMapping("getCrewsDetail")
    public ResponseEntity<List<CrewStatusDetailDto>> getCrewStatus() {
        return ResponseEntity.ok(crewStatus.getCrewStatus());
    }


}
