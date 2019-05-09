package com.example.krist.navajamadrilea;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Alarma extends AppCompatActivity implements View.OnClickListener{
    EditText tardanza, textorigen, textdestino, fecha,hora;
    TextView resultado;
    String recoger,horaformat,minutoformat,acumular="";
    private final static String CHANNEL_ID = "NOTIFICACION";
    private PendingIntent pendingIntent;
    private final static int notificacion_id = 0;
    int tiempo;//tiempo de espera del proceso
    Informacion info;
    ImageView reloj,mapa,principal,madrid,estrella;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);
        getSupportActionBar().hide();

        reloj=findViewById(R.id.reloj);
        mapa=findViewById(R.id.mapa);
        madrid=findViewById(R.id.madrid);
        principal=findViewById(R.id.principal);
        estrella=findViewById(R.id.estrella);


        reloj.setOnClickListener(this);
        madrid.setOnClickListener(this);
        mapa.setOnClickListener(this);
        principal.setOnClickListener(this);



        //***************************

        Button boton,boton2,botonubicacion;
        boton = findViewById(R.id.button);
        boton2 = findViewById(R.id.button2);
        botonubicacion = findViewById(R.id.buttonubicacion);
        boton.setOnClickListener(this);
        boton2.setOnClickListener(this);
        botonubicacion.setOnClickListener(this);
        Vibrator vibrador = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        tardanza = findViewById(R.id.editText2);
        textorigen = findViewById(R.id.editText3);
        textdestino = findViewById(R.id.editText4);
        fecha = findViewById(R.id.editText5);
        hora = findViewById(R.id.editText6);
        resultado=findViewById(R.id.textView5);
        Intent intent4=getIntent();
        Bundle bundle=intent4.getExtras();
        if (bundle!=null){
            fecha.setText((String)bundle.get("fecha"));

            hora.setText((String) bundle.get("hora"));
            horaformat=(String) bundle.get("horaformat");
            minutoformat=(String) bundle.get("minutoformat");
            System.out.println(horaformat);
            System.out.println(minutoformat);



        }
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck==PackageManager.PERMISSION_DENIED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
            }

        }



    }


    private void AlarmaClick(int tiempo) { // el que uso
        AlarmManager manager;
        manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int resta=((tiempo*1000)-(int) System.currentTimeMillis()); //multiplico por mil para pasarlo a milisegundo menos el tiempo actual
        System.out.println(System.currentTimeMillis()+" el tiempo actual");


        System.out.println(tiempo+" el tiempo introducido");
        System.out.println(resta+" esta de la resta");
        manager.setAlarmClock(new AlarmManager.AlarmClockInfo( System.currentTimeMillis() + resta ,pIntent),pIntent);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button){

            // System.out.println(fecha.getText().toString());

            if (fecha.getText().toString().isEmpty()){
                Toast.makeText(this,"el campo fecha esta vacio",Toast.LENGTH_LONG).show();
            }else{

                new Thread(new Runnable() {
                    public void run() {
                        int t1= (int) System.currentTimeMillis();
                        Informacion infi=new Informacion();
                        try {
                            acumular=infi.conexion("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+textorigen.getText().toString()+",+España"+"&destinations="+textdestino.getText().toString()+"+España"+"&departure_time=now&language=es-ES&key=AIzaSyC0UyH8RiVldOp19z1F2YvPF0HuHcGmrPU");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            int t2= (int) System.currentTimeMillis();
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                            tiempo=t2-t1;
                            System.out.println(t1-t2);
                        }
                    }
                }).start();
                try {
                    sleep(tiempo+5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println(acumular);
                System.out.println(tiempo);
                String [] coche=acumular.split(" ");
                String [] coche2=coche[0].split("\""); //consegui consegui tranformar el dato de los min de la api con dos split

                System.out.println(coche[0]);
                System.out.println(coche2[1]);

                int convertircoche=calcular_hora("0",coche2[1]);

                System.out.println(convertircoche); //min convertidos a segundos que tarda el coche

                int result=calcular_fecha(fecha.getText().toString());
                int resulthora=calcular_hora(horaformat,minutoformat);
                if(tardanza.getText().toString().isEmpty()){
                    int resultfinal=(resulthora+result-3600)-convertircoche;
                    acumular=acumular+" tiempo en tardar en llegar \n";
                    long tiempoenmin=(resultfinal-(System.currentTimeMillis()/1000))/60; //convertir tiempo en segundos que quedan para saltar la alarma
                    acumular=acumular+" "+tiempoenmin+" tiempo para la alarma \n";
                    resultado.setText(acumular+" "+info.cadenagoogle);
                    AlarmaClick(resultfinal);
                }else{
                    int convertirtardanza=calcular_hora("0",tardanza.getText().toString());
                    int resultfinal=((resulthora+result-3600)-convertircoche)-convertirtardanza;
                    acumular=acumular+" tiempo en tardar en llegar \n";
                    long tiempoenmin=(resultfinal-(System.currentTimeMillis()/1000))/60; //convertir tiempo en segundos que quedan para saltar la alarma
                    acumular=acumular+" "+tiempoenmin+" tiempo para la alarma \n";
                    resultado.setText(acumular+" "+info.cadenagoogle);
                    AlarmaClick(resultfinal);
                }
            }

        }
        if (v.getId() == R.id.button2){
            Intent intent2 = new Intent(this, conf_alarma.class);

            startActivity(intent2);
        }
        if (v.getId() == R.id.buttonubicacion){
            LocationManager locationManager = (LocationManager) Alarma.this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    textorigen.setText(location.getLatitude()+","+location.getLongitude());
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };
            int permissionCheck = ContextCompat.checkSelfPermission(Alarma.this,Manifest.permission.ACCESS_FINE_LOCATION);
// Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }


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
            case R.id.estrella:
                Intent intent6 = new Intent(this, Tiempo_horas.class);

                startActivity(intent6);
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


        System.out.println(total+" segundos");
        String cambiar=Integer.toString(total);
        resultado.setText(cambiar+" segundos");
        return total;
    }
    public int calcular_hora(String hora,String minuto){
        int converhora=Integer.parseInt(hora);
        int convermin=Integer.parseInt(minuto);
        //int huso=3600; //utc+1 en españa hay que añadir  una hora **seha puesto en el boton
        int resultado=(converhora*3600)+(convermin*60);//-huso;
        return resultado;
    }
}
