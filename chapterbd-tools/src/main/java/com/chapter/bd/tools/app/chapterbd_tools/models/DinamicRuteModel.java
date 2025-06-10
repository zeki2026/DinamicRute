package com.chapter.bd.tools.app.chapterbd_tools.models;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Repository
public class DinamicRuteModel {

    private WebTarget urlTarget;

    private Client client;

    private List<String> header = new ArrayList<>();

    private List<Object> columns = new ArrayList<>();

    @Value("${validURL.sessionId}")
    private String sessionId;

    @Value("${validURL.URL}")
    private String URL;

    // private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DinamicRuteService.class);

     // Método para deshabilitar la validación SSL
     private SSLContext disableSSLValidation() throws NoSuchAlgorithmException, KeyManagementException {
        // Crear un TrustManager que confíe en todos los certificados
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };

        // Configurar el SSLContext para usar el TrustManager
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        return sslContext;
    }

    // Inicializa el cliente JAX-RS con la configuración SSL personalizada
    @PostConstruct
    void initUrl() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = disableSSLValidation();
        client = ClientBuilder.newBuilder()
                .sslContext(sslContext) // Configura el SSLContext personalizado
                .hostnameVerifier((hostname, session) -> true) // Deshabilita la verificación del nombre del host
                .build();
        urlTarget = client.target(URL);
    }

    //Access to the breakPoint of the URL
    //@Return return a Map object with the response of the URL but only the rturning data of the query 
    //@Exception Error message
    public Map<String, Object> getValid(String formData){
        Map<String,Object> responserow = new HashMap<>();

        header = new ArrayList<>();
        columns = new ArrayList<>();

        Response response = urlTarget.request(MediaType.APPLICATION_FORM_URLENCODED)
        .header("Cookie", sessionId)
        .post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED));

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String responseBody = response.readEntity(String.class);
            extractTable(responseBody);
            
            responserow.put("data", columns);
            return(responserow);
        } 
        responserow.put("error", response.getStatus());
        return(responserow);
    }

    //Procesa el resultado del URL. Extrae toda la informacion dentro de la tabla de resultados 
    private void extractTable(String response){
        Pattern pattern = Pattern.compile("(border=\"1\">)([\\s\\S]*?)(</table>)");
        Matcher matcher = pattern.matcher(response);

        while(matcher.find()){
            extractColumn(matcher.group(2));
        }
    }
    //Procesa el resultado del URL. Extrae toda la informacion dentro de las columnas
    private void extractColumn(String response){
        Pattern pattern = Pattern.compile("(<tr>)([\\s\\S]*?)(</tr>)");
        Matcher matcher = pattern.matcher(response);

        int i = 0; 
        while(matcher.find()){
            i++;
            if(i==1)continue;
            extractRows(matcher.group(2),i);
        }
    }

    //Procesa el resultado del URL. Extrae toda la informacion dentro de las filas
    private void extractRows(String response, int level){
        Pattern pattern = Pattern.compile("(<td>)([\\s\\S]*?)(</td>)");
        Matcher matcher = pattern.matcher(response);

        Map<String,Object> row = new HashMap<>();

        int i = 0;
        while(matcher.find()){
            //Extrae encabezados
            if(level == 2){
                
                header.add(matcher.group(2));
                continue;
            }
            
            row.put(header.get(i), matcher.group(2));
            i++;
        }
        if(level > 2)columns.add(row);
    }

    //Destruye la sesion
    @PreDestroy
    void closeClient(){
        client.close();
    }
}
