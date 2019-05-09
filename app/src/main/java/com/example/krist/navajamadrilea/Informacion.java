package com.example.krist.navajamadrilea;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Informacion {
    OkHttpClient client = new OkHttpClient();
     static String cadena="";
     static String tiempohoras="";
    static String cadenagoogle="";
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public String conexion()   throws IOException, ParseException {
        System.setProperty("javax.net.ssl.trustStore","C:\\Users\\krist\\Documents\\alma");
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        //StrictMode.setThreadPolicy(policy); escapar un fallo de conexion no recomendable

        Informacion info =new Informacion();
        String response1 = info.run("https://opendata.aemet.es/opendata/api/prediccion/nacional/hoy/" + "?api_key=******");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response1);
        String httpDatos = (String) json.get("datos");
        System.out.println("Dirección para conectar : " + httpDatos);
        System.setProperty("javax.net.ssl.trustStore","C:\\Users\\krist\\Documents\\alma");


        String conect= info.run(httpDatos);
        System.out.println(conect);
        cadena=cadena+conect;

        return cadena;
    }
    public String conexiontiempo_horas()   throws IOException, ParseException {
        System.setProperty("javax.net.ssl.trustStore","C:\\Users\\krist\\Documents\\alma");
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        //StrictMode.setThreadPolicy(policy); escapar un fallo de conexion no recomendable

        Informacion info =new Informacion();
      String response1 = info.run("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/horaria/28079/?api_key=*****");

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response1);
        String httpDatos = (String) json.get("datos");
        System.out.println("Dirección para conectar : " + httpDatos);
        System.setProperty("javax.net.ssl.trustStore","C:\\Users\\krist\\Documents\\alma");


        String conect= info.run(httpDatos);
        System.out.println(conect);
        tiempohoras=conect;

        return tiempohoras;
    }
    public String conexion(String url) throws IOException {
        Informacion info =new Informacion();
        String response1 = info.run(url);
        ObjectMapper objectMapper = new ObjectMapper();

        //read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(response1);
        Iterator<JsonNode> nodos = rootNode.elements();
        JsonNode locatedNode = rootNode.path("rows").findValue("elements").findValue("duration_in_traffic").findValue("text");
        System.out.println("########################33");
        cadenagoogle="El destino -> "+nodos.next().get(0)+" el origen ->"+nodos.next().get(0);
        System.out.println(locatedNode);
        return locatedNode.toString();
    }
    public static void main(String[] args) throws IOException, ParseException {
        Informacion infi=new Informacion();
        infi.conexion();
    }


}
