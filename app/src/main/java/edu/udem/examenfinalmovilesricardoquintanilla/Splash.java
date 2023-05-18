package edu.udem.examenfinalmovilesricardoquintanilla;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private long tiempodeespera = 5000; //milisegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent intentoPrincipal  = new Intent().setClass(Splash.this,Login.class);
                startActivity(intentoPrincipal);
            }
        };

        Timer timer = new Timer();
        timer.schedule(tarea,tiempodeespera);
    }
}