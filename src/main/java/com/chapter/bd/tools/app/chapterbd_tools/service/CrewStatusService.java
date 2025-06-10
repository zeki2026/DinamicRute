package com.chapter.bd.tools.app.chapterbd_tools.service;

import java.util.List;

import com.chapter.bd.tools.app.chapterbd_tools.models.CrewStatusDetailDto;
import com.chapter.bd.tools.app.chapterbd_tools.models.CrewStatusDto;

public interface CrewStatusService {
    
  List<CrewStatusDto> getCrewStatus();  

  List<CrewStatusDetailDto> getCrewStatusDetail();  


}
