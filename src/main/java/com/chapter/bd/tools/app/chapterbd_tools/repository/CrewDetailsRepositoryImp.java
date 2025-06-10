package com.chapter.bd.tools.app.chapterbd_tools.repository;

import java.sql.CallableStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chapter.bd.tools.app.chapterbd_tools.exceptions.ObjectNotConvertedException;
import com.fasterxml.jackson.core.JacksonException; 
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import oracle.jdbc.OracleTypes; 

@Repository
@Primary
public class CrewDetailsRepositoryImp implements CrewDetailsRepository{

    @PersistenceContext
    private EntityManager entityManager;


    public static final Logger logger = LoggerFactory.getLogger(CrewDetailsRepositoryImp.class);

    private final JdbcTemplate jdbcTemplate;

    // Inyecta las configuraciones de la base de datos
    public CrewDetailsRepositoryImp(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public String getData(String function) {
        return jdbcTemplate.execute((ConnectionCallback<String>) connection -> {
            try (CallableStatement cs = connection.prepareCall(function)) {
                
            
                // Registrar el parámetro de salida como cursor
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.execute();
                
                if(!(cs.getObject(1) instanceof ResultSet)) 
                    throw new ObjectNotConvertedException("No es instancia de ResultSet");
            
                try (ResultSet rs = (ResultSet) cs.getObject(1)) { 
                    if(!rs.next()) throw new ObjectNotConvertedException("No se encontro respuesta");

                    ObjectMapper response = new ObjectMapper();  

                    // logger.info("1-----------------"+(String) rs.getString(1));
                    try {
                        JsonNode rootNode = response.readTree((String) rs.getString(1));
                        return rootNode.toString();
                    } catch (JacksonException e) {
                        throw new ObjectNotConvertedException("Error en el formato: " + e.getMessage());   
                    }
                }

            }catch(SQLException e ){
                throw new ObjectNotConvertedException("Error de servidor " + e.getMessage());
            }
        });
    }

    // public String processResultSet(ResultSet rs) throws SQLException {
    //     List<Map<String, Object>> resultList = new ArrayList<>();
    //     ResultSetMetaData metaData = rs.getMetaData();
    //     int columnCount = metaData.getColumnCount();
        
    //     while (rs.next()) {
    //         Map<String, Object> row = new LinkedHashMap<>();
    //         for (int i = 1; i <= columnCount; i++) {
    //             row.put(metaData.getColumnName(i), rs.getObject(i));
    //             logger.info("1-----------------"+rs.getObject(i).toString());
    //         }
    //         logger.info("2--------------"+row.toString());
    //         resultList.add(row);
    //     }
        
    //     if (resultList.isEmpty()) {
    //         return null;
    //     }
        
    //     try {
    //         return new ObjectMapper().writeValueAsString(resultList);
    //     } catch (JsonProcessingException e) {
    //         throw new SQLException("Error converting to JSON", e);
    //     }
    // }


}
