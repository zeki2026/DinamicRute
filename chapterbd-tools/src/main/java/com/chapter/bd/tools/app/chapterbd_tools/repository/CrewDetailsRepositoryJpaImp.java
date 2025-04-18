package com.chapter.bd.tools.app.chapterbd_tools.repository;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Repository
public class CrewDetailsRepositoryJpaImp implements CrewDetailsRepository{

    @PersistenceContext
    private EntityManager entityManager;

     public static final Logger logger = LoggerFactory.getLogger(CrewDetailsRepositoryImp.class);



    @Override
    public String getData(String function) {
        try {
            // Opción 1: Usando StoredProcedureQuery (recomendado para REF CURSOR)
            StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("RCREDITO.FNCOREBDCREW_STATUS")
                .registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR);
            
            query.execute();
            
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            // Convertir resultados a JSON
            return convertResultsToJson(results);
            
        } catch (Exception e) {
            logger.error("Error al obtener datos de crew status", e);
            throw new RuntimeException("Error al obtener datos de crew status", e);
        }
    }

    private String convertResultsToJson(List<Object[]> results) {
        if (results == null || results.isEmpty()) {
            return "[]";
        }
        
        // Implementa la conversión según la estructura de tus datos
        // Esto es un ejemplo básico:
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Object[] row : results) {
            jsonBuilder.append("{");
            for (int i = 0; i < row.length; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append("\"column").append(i+1).append("\":");
                jsonBuilder.append(row[i] != null ? "\"" + row[i].toString() + "\"" : "null");
            }
            jsonBuilder.append("},");
        }
        if (results.size() > 0) {
            jsonBuilder.setLength(jsonBuilder.length() - 1); // Eliminar última coma
        }
        jsonBuilder.append("]");
        
        return jsonBuilder.toString();
    }


}
