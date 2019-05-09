package com.example.krist.navajamadrilea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView pronostico;
    ImageButton rel,map,princi,mad,start;
    Button capital,traffic,meteo,alar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        rel=findViewById(R.id.reloj);
        map=findViewById(R.id.mapa);
        mad=findViewById(R.id.madrid);
        princi=findViewById(R.id.principal);
        start=findViewById(R.id.start);

        capital=findViewById(R.id.mad);
        traffic=findViewById(R.id.trafico);
        meteo=findViewById(R.id.tempo);
        alar=findViewById(R.id.clock);
        capital.setBackgroundResource(R.drawable.gifmadrid);
        meteo.setBackgroundResource(R.drawable.gifnube);
        alar.setBackgroundResource(R.drawable.gifalarma);
        AnimationDrawable frameAnimationDrawable= (AnimationDrawable) capital.getBackground();
        AnimationDrawable frameAnimationnube= (AnimationDrawable) meteo.getBackground();
        AnimationDrawable frameAnimationalarma= (AnimationDrawable) alar.getBackground();
        frameAnimationDrawable.start();
        frameAnimationnube.start();
        frameAnimationalarma.start();




        /*rel.setOnClickListener(this);
        mad.setOnClickListener(this);
        map.setOnClickListener(this);
        princi.setOnClickListener(this);*/


      /*  pronostico=findViewById(R.id.pronostico);
        pronostico.setMovementMethod(new ScrollingMovementMethod());
        pronostico=findViewById(R.id.pronostico);
        pronostico.setText(Informacion.cadena);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.madrid:
            case R.id.mad:
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
            case R.id.clock:
                Intent intent5 = new Intent(this, Alarma.class);
                startActivity(intent5);
                break;
            case R.id.start:
            case R.id.tempo:
                Intent intent6 = new Intent(this, Tiempo_horas.class);
                startActivity(intent6);
                break;
        }
    }
}
