package com.example.krist.navajamadrilea;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;


public class sonar extends AppCompatActivity {
    MediaPlayer mp;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private PendingIntent pendingIntent;
    private final static int notificacion_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonar);
        mp = MediaPlayer.create(this,R.raw.rey_leon);
        mp.start();
        createcanal();
        createNotificacion();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
        //if (keyCode == event.KEYCODE_HOME) {

            System.out.println(mp.isPlaying()+" esta reproduciendo?");
            mp.stop();

            System.out.println("ha pasado por aqui");
      /*  }
        if (keyCode == event.KEYCODE_POWER) {
            MediaPlayer mp = MediaPlayer.create(this,R.raw.rey_leon);
            System.out.println(mp.isPlaying()+" esta reproduciendo?");
            mp.stop();
            System.out.println("ha pasado por aqui");
        }*/
        break;}
        return super.onKeyDown(keyCode, event);
    }

    public void createcanal(){//te crea el canal en versiones suberiores a O para tener notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name="notificacion";
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,name,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void createNotificacion(){
        System.out.println("pasa por la notificacion");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.madrid);
        mBuilder.setContentTitle("!!!ALARMA!!!!");
        mBuilder.setContentText("Esta es la alarma tienes que levantarte");
        mBuilder.setColor(Color.CYAN);
        mBuilder.setLights(Color.MAGENTA, 1000, 1000);
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(notificacion_id, mBuilder.build());

    }
}
