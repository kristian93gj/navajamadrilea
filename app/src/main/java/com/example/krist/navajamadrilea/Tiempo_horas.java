package com.example.krist.navajamadrilea;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static com.example.krist.navajamadrilea.R.*;
import static java.lang.Thread.sleep;

public class Tiempo_horas extends AppCompatActivity  implements View.OnClickListener{
      String tiempo="";
      String mostrar="";
      TextView dia,orto,ocaso;
      String estado_cielo="",vientodirec="";
     int periodo,tempera,precipita,sensTer,humedadRel,vientofuerza,tiempo_ocaso,tiempo_orto;
     Drawable drawable;
     Adaptador adaptador;
     ListView listView;
    ArrayList<Tiempo> coleccionhoras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo_horas);
        getSupportActionBar().hide(); //esconder la barra de arriba
        coleccionhoras = new ArrayList<Tiempo>(); //arraylist de tiempo
        //aqui construimos el adaptador
        listView = findViewById(R.id.listview);
        adaptador = new Adaptador(this);
        listView.setAdapter(adaptador);




        final Informacion infi;


        ObjectMapper objetoMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objetoMapper.readTree(Informacion.tiempohoras); //añadir el arbol json al objeto rootNode
        } catch (IOException e) {
            e.printStackTrace();
        }

            //recorremos el json
        for (int j=0;j<2;j++){
            JsonNode estadocielo=rootNode.findPath("dia").get(j).findPath("estadoCielo");
            JsonNode precipitacion=rootNode.findPath("dia").get(j).findPath("precipitacion");
            JsonNode temperatura=rootNode.findPath("dia").get(j).findPath("temperatura");
            JsonNode sensTermica=rootNode.findPath("dia").get(j).findPath("sensTermica");
            JsonNode humedadRelativa=rootNode.findPath("dia").get(j).findPath("humedadRelativa");
            JsonNode vientoAndRachaMax=rootNode.findPath("dia").get(j).findPath("vientoAndRachaMax");
            for (int i=0;i<estadocielo.size();i++){
                System.out.println("cielo  "+estadocielo.get(i).findValue("descripcion"));
                System.out.println("periodo "+estadocielo.get(i).findValue("periodo"));
                System.out.println("precipitacion "+precipitacion.get(i).findValue("value"));
                System.out.println("temperatura "+temperatura.get(i).findValue("value"));
                System.out.println("humedad relativa "+humedadRelativa.get(i).findValue("value"));
                System.out.println("sensacion termica "+sensTermica.get(i).findValue("value"));
                System.out.println("velocidad direccion "+vientoAndRachaMax.get(i*2).findValue("direccion"));
                System.out.println("velocidad viento "+vientoAndRachaMax.get(i*2).findValue("velocidad"));

                //recogemos los datos en variables
                estado_cielo=estadocielo.get(i).findValue("descripcion").toString();
                vientodirec=vientoAndRachaMax.get(i*2).findValue("direccion").toString(); //el por 2 es por la estructura del json
                periodo=estadocielo.get(i).findValue("periodo").asInt();
                tempera=temperatura.get(i).findValue("value").asInt();
                precipita=precipitacion.get(i).findValue("value").asInt();
                sensTer=sensTermica.get(i).findValue("value").asInt();
                humedadRel=humedadRelativa.get(i).findValue("value").asInt();
                vientofuerza=vientoAndRachaMax.get(i*2).findValue("velocidad").asInt();

                System.out.println("****");
                //creamos el objeto tiempo con todos los campos
                Tiempo tiempo=new Tiempo(estado_cielo,vientodirec,vientofuerza,periodo,tempera,precipita,sensTer,humedadRel);
                coleccionhoras.add(tiempo); //introducimos el objeto tiempo en un arraylist
            }
            System.out.println(rootNode.findPath("orto"));
            System.out.println(rootNode.findPath("ocaso"));

        }
        //aqui para escuchar cada item del listview que de momento no lo usamos para nada
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        dia=findViewById(R.id.textViewdia);
        orto=findViewById(R.id.textVieworto);
        ocaso=findViewById(R.id.textViewocaso);
        dia.setText(rootNode.findPath("elaborado").toString());
        orto.setText(rootNode.findPath("orto").toString());
        ocaso.setText(rootNode.findPath("ocaso").toString());
        System.out.println(mostrar);
        String convertirfecha []=rootNode.findPath("elaborado").toString().split("\""); //quitar las comillas del json

        //sacar la fecha desde el sistema
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        System.out.println("Fecha: "+dateFormat.format(date));
        //System.out.println(convertir[0]);
        System.out.println(convertirfecha[1]);

//        System.out.println(calcular_fecha(convertirfecha[1]));
        //sacar el orto
        String convertirhora []=rootNode.findPath("orto").toString().split("\""); //quitar las comillas del json
        System.out.println(convertirhora[1]);
        String convertirhoraaux []=convertirhora[1].split(":");
        System.out.println(calcular_hora(convertirhoraaux[0],convertirhoraaux[1]));
        tiempo_orto=(calcular_fecha(dateFormat.format(date))+calcular_hora(convertirhoraaux[0],convertirhoraaux[1]));
        //sacar el ocaso
        String convertirhoraocaso []=rootNode.findPath("ocaso").toString().split("\""); //quitar las comillas del json

        String convertirhoraauxocaso []=convertirhoraocaso[1].split(":");
        tiempo_ocaso=(calcular_fecha(dateFormat.format(date))+calcular_hora(convertirhoraauxocaso[0],convertirhoraauxocaso[1]));

        ImageView imageView = findViewById(R.id.imageViewfondo);
        //drawable = getResources().getDrawable(R.drawable.noche, null);
        imagenes();
        imageView.setImageDrawable(drawable);
        System.out.println("~~~~~~~~~~");
        System.out.println("Fecha "+dateFormat.format(date));
        System.out.println(calcular_fecha(dateFormat.format(date))+" fecha hoy");
        System.out.println(calcular_hora("18","01")+" la hora");

    }
    public void alertOneButton() {

        new AlertDialog.Builder(this)
                .setTitle("Informacion meteorilógica estatal")
                .setMessage(Informacion.cadena)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }
    public void imagenes(){
        int horaactual= (int) (System.currentTimeMillis()/1000);
        if (horaactual>tiempo_orto && horaactual<tiempo_ocaso){
            if ((tiempo_ocaso-horaactual)<300){  //cuando queden menos de 5 min se cambia la foto al ocaso
                drawable = getResources().getDrawable(R.drawable.ocaso3, null);

            }else{drawable = getResources().getDrawable(R.drawable.dia_despejado, null);}//si no se pone la foto de dia
        }else{if ((tiempo_orto-horaactual)<300 && (tiempo_orto-horaactual)>-300){ //cuando queden menos de 5 min se cambia la foto al orto
            drawable = getResources().getDrawable(R.drawable.orto_ver, null);
        }else{drawable = getResources().getDrawable(R.drawable.noche, null);}
            }
        System.out.println(tiempo_ocaso-horaactual+" los segundos para ocaso"+tiempo_ocaso);
        System.out.println(tiempo_orto-horaactual+" los segundos para orto"+tiempo_orto+" "+System.currentTimeMillis()/1000);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.madrid:
                Intent intent2 = new Intent(this, Madrid.class);

                startActivity(intent2);
                break;
            case R.id.principal:
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
            case R.id.mas:
                alertOneButton();
                break;
        }
    }

    public  int calcular_fecha(String hora){  //dia mes año
        String convertir[]=new String[3];
        System.out.println("entra aqui");
        convertir=hora.split("/");
        int[]num=new int[3];
        num[0]=Integer.parseInt(convertir[0]);
        num[1]=Integer.parseInt(convertir[1]);
        num[2]=Integer.parseInt(convertir[2]);
        int año=((num[2]-1970)*31536000);
        int mes=0;
        //int mes=(num[1]-1)*30*24*3600;
        switch(num[1]){
            case 1:
                mes=0;
                break;
            case 2:
                mes=2678400;
                break;
            case 3:
                mes=5097600; //31+28*68400
                if (num[2]%4==0){mes=(31+29)*86400;}
                break;
            case 4:
                mes=7776000; //(31+28+31)*86400;
                if (num[2]%4==0){mes=(31+29+31)*86400;}
                break;
            case 5:
                mes=(31+28+31+30)*86400;
                if (num[2]%4==0){mes=(31+29+31+30)*86400;}
                break;
            case 6:
                mes=(31+28+31+30+31)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31)*86400;}
            case 7:
                mes=(31+28+31+30+31+30)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31+30)*86400;}
                break;
            case 8:
                mes=(31+28+31+30+31+30+31)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31+30+31)*86400;}
                break;
            case 9:
                mes=(31+28+31+30+31+30+31+31)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31+30+31+31)*86400;}
                break;
            case 10:
                mes=(31+28+31+30+31+30+31+31+30)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31+30+31+31+30)*86400;}
                break;
            case 11:
                mes=(31+28+31+30+31+30+31+31+30+31)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31+30+31+31+30+31)*86400;}
                break;
            case 12:
                mes=(31+28+31+30+31+30+31+31+30+31+30)*86400;
                if (num[2]%4==0){mes=(31+29+31+30+31+30+31+31+30+31+30)*86400;}
                break;
        }
        int dia=(num[0]-1)*24*3600;
        int bisiesto=Math.round((num[2]-1970)/4)*(24*3600);
        int total;
        if (num[2]>1972){
            total=dia+mes+año+bisiesto;
            if(num[2]%4==0){
                // total=dia+mes+año+bisiesto;
                System.out.println("es año bisiesto");}
            if (num[2]%4==2 ||num[2]%4==2){
                //total=dia+mes+año+bisiesto;//+(24*3600);
                System.out.println("no es año bisiesto");}
            if(num[2]%4==1){ total=total+(24*3600);
                System.out.println("no es año bisiesto");}

        }else{total=dia+mes+año;

            if(num[2]%4==0){
                total=dia+mes+año+bisiesto;
                System.out.println("es año bisiesto");}
            else  { total=dia+mes+año+bisiesto;//+(24*3600);
                System.out.println("no es año bisiesto");}


        }

        total=total-3600;  //sin esto estaba una hora mas a la hora española
        System.out.println(total+" segundos");


        return total;
    }
    public int calcular_hora(String hora,String minuto){
        int converhora=Integer.parseInt(hora);
        int convermin=Integer.parseInt(minuto);
        //int huso=3600; //utc+1 en españa hay que añadir  una hora **seha puesto en el boton
        int resultado=(converhora*3600)+(convermin*60);//-huso;
        return resultado;
    }

    class Adaptador extends ArrayAdapter<Tiempo> {
        AppCompatActivity appCompatActivity;

        public Adaptador(AppCompatActivity context) {
            super(context,layout.tiempo,coleccionhoras);
            appCompatActivity = context;
        }

        public View getView(int posicion, View vista, ViewGroup parent){
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            String aux; //para convertir de int a String y poder ponerlo en el textview
            View item = inflater.inflate(R.layout.tiempo,null);
            TextView textViewperi = item.findViewById(R.id.textnombre);
            aux=String.valueOf(coleccionhoras.get(posicion).getPeriodo())+":00 hora"; //hora de prediccion
            textViewperi.setText(aux);
            TextView textViewestado = item.findViewById(R.id.textdescripcion); // el estado del cielo
            textViewestado.setText(coleccionhoras.get(posicion).getEstado_cielo());
            TextView textViewpreci = item.findViewById(R.id.textViewprecipi); // las precipitaciones
            aux=String.valueOf(coleccionhoras.get(posicion).getPrecipitacion());
            textViewpreci.setText(aux);
            TextView textViewtempera = item.findViewById(R.id.textViewtermo); // las temperatura
            aux=String.valueOf(coleccionhoras.get(posicion).getTemperatura()+" Tem");
            textViewtempera.setText(aux);
            TextView textViewsens = item.findViewById(R.id.textViewsen); // las sensacion termica
            aux=String.valueOf(coleccionhoras.get(posicion).getSensTermica()+" SenTer");
            textViewsens.setText(aux);
            TextView textViewviento = item.findViewById(R.id.textViewviento); // el viento
            aux=String.valueOf(coleccionhoras.get(posicion).getVientofuerza()+" vel. "+coleccionhoras.get(posicion).getVientodirec());
            textViewviento.setText(aux);
            TextView textViewhume = item.findViewById(R.id.textViewhumedad); // las humedad
            aux=String.valueOf(coleccionhoras.get(posicion).getHumedadRelativa()+" %");
            textViewhume.setText(aux);

            ImageView imageView=item.findViewById(R.id.imageView4);
            switch (coleccionhoras.get(posicion).getEstado_cielo()){
                case "\"Despejado\"":
                    imageView.setImageResource(R.drawable.despejado);
                    break;
                case "\"Nuboso\"":
                case "\"Poco nuboso\"":
                    imageView.setImageResource(R.drawable.poco_nuboso);
                    break;
                case "\"Muy nuboso\"":
                case "\"Cubierto\"":
                    imageView.setImageResource(R.drawable.cubierto);
                    break;
                case "\"Intervalos nubosos\"":
                    imageView.setImageResource(R.drawable.intervalos_nubosos);
                    break;
                case "\"Cubierto con lluvia\"":
                case "\"Muy nuboso con lluvia\"":
                case "\"Cubierto con lluvia escasa\"":
                    imageView.setImageResource(R.drawable.ubierto_lluvia_escasa);
                    break;

                case "\"Nuboso con lluvia\"":
                case "\"Intervalos nubosos con lluvia\"":
                    imageView.setImageResource(R.drawable.ntervalos_nubosos_lluvia_escasa);
                    break;
                case "\"Cubierto con nieve\"":
                case "\"Nuboso con nieve escasa\"":
                case "\"Nuboso con nieve\"":
                case "\"Muy nuboso con nieve escasa\"":
                case "\"Intervalos nubosos con nieve\"":
                case "\"Intervalos nubosos con nieve escasa\"":
                    imageView.setImageResource(R.drawable.nuboso_nieve);
                    break;
                case "\"Nuboso con tormenta\"":
                case "\"Nuboso con tormenta y lluvia escasa\"":
                case "\"Muy nuboso con tormenta y lluvia escasa\"":
                case "\"Cubierto con tormenta y lluvia escasa\"":
                case "\"Muy nuboso con tormenta\"":
                case "\"Cubierto con tormenta\"":
                case "\"Intervalos nubosos con tormenta y lluvia escasa\"":
                case "\"Intervalos nubosos con tormenta\"":
                    imageView.setImageResource(R.drawable.uboso_tormenta);
                    break;
                default:imageView.setImageResource(R.drawable.poco_nuboso);
                break;
            }

            return item;

        }
    }

}





