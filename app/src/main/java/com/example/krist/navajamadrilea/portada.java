package com.example.krist.navajamadrilea;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.lang.Thread.sleep;

public class portada extends AppCompatActivity {
    ProgressBar progressBar;
    static ArrayList<Actuacion> actuaciones= new ArrayList<>();;
    Intent intent2;
    Thread thread1,thread;
    String tiempo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream file =getResources().openRawResource(R.raw.madrid);
                try {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList nList = doc.getElementsByTagName("service");
                    System.out.println("Número de resultados: " + nList.getLength());

                    for (int i=0;i<nList.getLength();i++){

                        Node nNode = nList.item(i);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            System.out.println("el nombre : "+eElement.getElementsByTagName("name").item(0).getTextContent());
                            System.out.println("descripcion : "+eliminarTags(eElement.getElementsByTagName("body").item(0).getTextContent()));
                            System.out.println("url : "+eElement.getElementsByTagName("url").item(0).getTextContent());
                            System.out.println("address : "+eElement.getElementsByTagName("address").item(0).getTextContent());
                            System.out.println("latitude : "+eElement.getElementsByTagName("latitude").item(0).getTextContent());
                            System.out.println("longitude : "+eElement.getElementsByTagName("longitude").item(0).getTextContent());
                            System.out.println("fecha inicio : "+eElement.getElementsByTagName("inicio").item(0).getTextContent());
                            System.out.println("fecha fin : "+eElement.getElementsByTagName("fin").item(0).getTextContent());

                            System.out.println("categoria : "+eElement.getElementsByTagName("item").item(3).getTextContent());
                            System.out.println("precio : "+eliminarTags(quitar(eElement.getElementsByTagName("item").item(6).getTextContent())));


                            System.out.println(i);

                            if (eElement.getElementsByTagName("latitude").item(0).getTextContent().isEmpty()){
                                actuaciones.add(new Actuacion(eElement.getElementsByTagName("name").item(0).getTextContent().trim(), eElement.getElementsByTagName("address").item(0).getTextContent().trim(), quitar(eliminarTags(eElement.getElementsByTagName("body").item(0).getTextContent()).trim()), eElement.getElementsByTagName("item").item(3).getTextContent(), eElement.getElementsByTagName("url").item(0).getTextContent(),eElement.getElementsByTagName("inicio").item(0).getTextContent(),eElement.getElementsByTagName("fin").item(0).getTextContent(),eliminarTags(quitar(eElement.getElementsByTagName("item").item(6).getTextContent()))));

                            }else {

                                actuaciones.add(new Actuacion(eliminarTags(eElement.getElementsByTagName("name").item(0).getTextContent().trim()), eElement.getElementsByTagName("address").item(0).getTextContent().trim(), quitar(eliminarTags(eElement.getElementsByTagName("body").item(0).getTextContent())).trim(), eElement.getElementsByTagName("item").item(3).getTextContent(), eElement.getElementsByTagName("url").item(0).getTextContent(),eElement.getElementsByTagName("inicio").item(0).getTextContent(),eElement.getElementsByTagName("fin").item(0).getTextContent(),eliminarTags(quitar(eElement.getElementsByTagName("item").item(6).getTextContent())),Double.parseDouble(eElement.getElementsByTagName("latitude").item(0).getTextContent()), Double.parseDouble(eElement.getElementsByTagName("longitude").item(0).getTextContent())));
                            }

                        }


                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

        thread1 = new Thread(new Runnable() {
            Informacion   infi =new Informacion();
            @Override
            public void run() {
                try {
                    infi.conexion();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /*-------------------------------*/







                for (int i=0;i<50;i++){
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);}
                try {

                    tiempo=infi.conexiontiempo_horas();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }finally {
                }
                for (int i=50;i<=100;i++){
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);}

            }
        });
        thread1.start();
        intent2 = new Intent(this, MainActivity.class);
        Thread thread = new Thread() {
            @Override
            public void run() {

                    while(true) {
                        System.out.println(progressBar.getProgress()+"hola");
                        if (progressBar.getProgress()==100){
                            System.out.println("has entrado en el if");
                            startActivity(intent2);
                            break;
                        }
                    }

            }
        };

        thread.start();


    }
    public static String eliminarTags(String cadena){
        while(true){
            int izdaTag= cadena.indexOf("<");
            if (izdaTag < 0 ) return cadena;
            int derTag = cadena.indexOf('>',izdaTag);
            if (derTag < 0) return cadena;
            cadena= cadena.substring(0,izdaTag)+" "+ cadena.substring(derTag+1);
            int izdTag= cadena.indexOf('&');
            if (izdTag < 0 ) return cadena;
            int dereTag = cadena.indexOf(';',izdTag);
            if (dereTag < 0) return cadena;
            cadena= cadena.substring(0,izdTag)+" "+ cadena.substring(dereTag+1);


        }
    }
    public static String quitar(String cadena){
        while (true){
            int izdaTag= cadena.indexOf('<');
            if (izdaTag < 0 ) return cadena;
            int derTag = cadena.indexOf('"',izdaTag);
            if (derTag < 0) return cadena;
            cadena= cadena.substring(0,izdaTag)+" "+ cadena.substring(derTag+1);
            int izdTag= cadena.indexOf('"');
            if (izdTag < 0 ) return cadena;
            int dereTag = cadena.indexOf('>',izdTag);
            if (dereTag < 0) return cadena;
            cadena= cadena.substring(0,izdTag)+" "+ cadena.substring(dereTag+1);
        }
    }
}
