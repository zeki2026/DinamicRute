package com.chapter.bd.tools.app.chapterbd_tools.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.chapter.bd.tools.app.chapterbd_tools.models.DetallesCuadrillasDto;
import com.chapter.bd.tools.app.chapterbd_tools.models.DinamicRuteModel;
import com.chapter.bd.tools.app.chapterbd_tools.models.ServiceHeader;
import com.chapter.bd.tools.app.chapterbd_tools.models.CrewStatusDto;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class DinamicRuteService {

    // private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DinamicRuteService.class);


    private DinamicRuteModel dinamicRuteModel;

    public DinamicRuteService(DinamicRuteModel dinamicRuteModel){
        this.dinamicRuteModel = dinamicRuteModel;
    }

    //Returns a list of the java object 
    public <T> List<T> getQueryResponse(Class<T> classType,ServiceHeader serviceData){
        String formData = jsonToUrlEncode(serviceData);
        Map<String,Object> statusCuadrillas = this.dinamicRuteModel.getValid(formData);
        ObjectMapper json = new ObjectMapper();

        if(!(statusCuadrillas.containsKey("data") && statusCuadrillas.get("data") instanceof List)) throw new RuntimeException("Formato invalido");

        List<?> arrayResult = (List<?>) statusCuadrillas.get("data");
        return arrayResult.stream().map(data ->{

        //Convierte el json a un objecto java
            try {
                String dataString = json.writeValueAsString(data);
                return json.readValue(dataString,classType);
            } catch (Exception e) {
                throw new RuntimeException("Error al convertir datos: " + e.getMessage());
            }
            
        }).toList(); 
        
    }

    private String jsonToUrlEncode(ServiceHeader header){
        StringBuilder serializer = new StringBuilder();

        serializer.append("textSel=");
        serializer.append(URLEncoder.encode(header.getTextSel(), StandardCharsets.UTF_8));
        serializer.append("&query=");
        serializer.append(URLEncoder.encode(header.getQuery(), StandardCharsets.UTF_8));
        serializer.append("&type=");
        serializer.append(URLEncoder.encode(header.getType(), StandardCharsets.UTF_8));
        serializer.append("&datasource=");
        serializer.append(URLEncoder.encode(header.getDatasource(), StandardCharsets.UTF_8));
        serializer.append("&datos=");
        serializer.append(URLEncoder.encode(header.getDatos(), StandardCharsets.UTF_8));
        return serializer.toString();
    }

    public File createFile(Map<String,Object> text)throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        Row row = sheet.createRow(0);
        CellAddress cell = (CellAddress) row.createCell(0);
        ((Cell) cell).setCellValue("Hola, este es un archivo Excel!");

        File file = new File("example.xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }
        workbook.close();

        return file;
    }

    // @SuppressWarnings("unchecked")
    public String createMessageStatusCuadrilla(List<CrewStatusDto> text){
        StringBuilder message = new StringBuilder();

        text.forEach(data ->{
            message.append("Hay ");
            message.append(data.getCounter());
            message.append(" cuadrillas con status: ");
            message.append(data.getStatus());
            message.append("\n");
        });

        return message.toString();
    }

    public String createMessageDetallesCuadrilla(List<DetallesCuadrillasDto> text){
        StringBuilder message = new StringBuilder();

        text.forEach(data ->{
            message.append("cuadrilla: ");
            message.append(data.getCuadrilla());
            message.append("\tstatus: ");
            message.append(data.getStatus());
            message.append("\n");
        });

        return message.toString();
    }

    // @SuppressWarnings("unchecked")
    public String statusCrews(){

        ServiceHeader header = new ServiceHeader("",
                    "",
                    "function",
                    "java:/jdbc/Oracle",
                    "on");

        //Catalogo de cuadrillas
        header.setQuery("RCREDITO.FNADSSLG0999(Q'[SELECT count(1) counter,b.FISTATUS status, d.FCDESCRIPCION description FROM RCREDITO.TAGERENCIASFFM a  left join RCREDITO.TARUTADINJSON b on a.FIDEPTOID = b.FIDEPTODID  inner join rcredito.tacuadrillas c on fiidcuadrilla = a.FIDEPTOID  LEFT JOIN RCREDITO.tctipocuadrilla d ON d.FITIPOCUADRILLA = c.FITIPOCUADRILLA group by b.fistatus,d.FCDESCRIPCION order by d.FCDESCRIPCION fetch first 100 rows only]')");
        List<CrewStatusDto> crewsList = getQueryResponse(CrewStatusDto.class, header);

        StringBuilder message = new StringBuilder();
        crewsList.stream().forEach(data->{
            message.append("Hay "+ data.getCounter() +" cuadrillas de tipo " 
            + data.getDescription() + " con estatus " + data.getStatus() + "\n");
        });
        return message.toString();
    }

    public String detailsCrews(){

        return "";
    }

}

