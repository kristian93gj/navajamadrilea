package com.example.krist.navajamadrilea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.example.krist.navajamadrilea.portada.actuaciones;

public class Madrid extends AppCompatActivity implements View.OnClickListener{
    ImageButton rel,map,princi,mad,estrella;
     //ArrayList<Actuacion> actuaciones;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madrid);
        getSupportActionBar().hide();
        rel=findViewById(R.id.reloj);
        map=findViewById(R.id.mapa);
        mad=findViewById(R.id.madrid);
        princi=findViewById(R.id.principal);
        estrella=findViewById(R.id.estrella);

       /* ViewPager viewPager=findViewById(R.id.viewpage);
        ImagenAdapter adapter =new ImagenAdapter(this);
        viewPager.setAdapter(adapter);

        aqui el codigo en donde ponemos las fotos y las pasamos haciendo slice con el dedo y ayudandonos co la clase imagenadapter

        */


        /*        actuaciones= new ArrayList<>();
        new Thread(new Runnable() {
            public void run(){

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
                                actuaciones.add(new Actuacion(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("address").item(0).getTextContent(), eliminarTags(eElement.getElementsByTagName("body").item(0).getTextContent()), eElement.getElementsByTagName("item").item(3).getTextContent(), eElement.getElementsByTagName("url").item(0).getTextContent()));

                            }else {

                                actuaciones.add(new Actuacion(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("address").item(0).getTextContent(), eliminarTags(eElement.getElementsByTagName("body").item(0).getTextContent()), eElement.getElementsByTagName("item").item(3).getTextContent(), eElement.getElementsByTagName("url").item(0).getTextContent(), Double.parseDouble(eElement.getElementsByTagName("latitude").item(0).getTextContent()), Double.parseDouble(eElement.getElementsByTagName("longitude").item(0).getTextContent())));
                            }

                        }


                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();*/

        ListView listView = findViewById(R.id.listview2);
        Adaptador2 adaptador2 =new Adaptador2(this);
        listView.setAdapter(adaptador2);



    }

    public static String eliminarTags(String cadena){
        while(true){
            int izdaTag= cadena.indexOf("</");
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
    } //para eliminar etiqutas htlm ya se usan en otra clase
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
    } //para eliminar etiqutas htlm

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.madrid: //boton de alarma
                Intent intent2 = new Intent(this, Madrid.class);

                startActivity(intent2);
                break;
            case R.id.principal: //boton de tiempo
                Intent intent3 = new Intent(this, MainActivity.class);

                startActivity(intent3);
                break;
            case R.id.mapa:
                Intent intent4 = new Intent(this, MapsActivity.class);

                startActivity(intent4);
                break;
            case R.id.reloj:
                Intent intent5 = new Intent(this, Alarma.class);

                startActivity(intent5);
                break;
            case R.id.start:
                Intent intent6 = new Intent(this, Tiempo_horas.class);

                startActivity(intent6);
                break;
        }
    }


class Adaptador2 extends ArrayAdapter<Actuacion> {
    AppCompatActivity appCompatActivity;

    public Adaptador2(AppCompatActivity context) {
        super(context,R.layout.eventos,actuaciones);
        appCompatActivity = context;
    }

    public View getView(int posicion, View vista, ViewGroup parent){
        LayoutInflater inflater = appCompatActivity.getLayoutInflater();
        String aux; //para convertir de int a String y poder ponerlo en el textview
        View item = inflater.inflate(R.layout.eventos,null);
        TextView textViewperi = item.findViewById(R.id.textnombre);

        textViewperi.setText(actuaciones.get(posicion).getNombre());

        TextView textViewestado = item.findViewById(R.id.textdescripcion); // descrripcion
        textViewestado.setText(actuaciones.get(posicion).getDescripcion());
        TextView textViewcategoria = item.findViewById(R.id.textcategoria); // categoria
        textViewcategoria.setText(actuaciones.get(posicion).getCategoria());

        TextView textViewfechainicio = item.findViewById(R.id.textfrcinicio); // fecha inicio
        textViewfechainicio.setText(actuaciones.get(posicion).getFinicio());

        TextView textViewfechafin = item.findViewById(R.id.textfecfin); // fecha fin
        textViewfechafin.setText(actuaciones.get(posicion).getFfin());

        TextView textViewdirec = item.findViewById(R.id.textdireccion); // direccion
        textViewdirec.setText(actuaciones.get(posicion).getDireccion());

        TextView textViewprecio = item.findViewById(R.id.textprecio); // fecha inicio
        textViewprecio.setText(actuaciones.get(posicion).getPrecio());



        ImageView imageView=item.findViewById(R.id.imageView4);
        //imageView.setImageBitmap(getImageBitmap(actuaciones.get(posicion).getFoto()));



        return item;

    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            System.out.println("no se ha cargado la foto");
        }
        return bm;
    }
}
}