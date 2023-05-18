package edu.udem.examenfinalmovilesricardoquintanilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    //Sharedpreferences variables
    SharedPreferences sharedPreferences;
    TextView usuario, password, textobienvenida;
    public static final String misPreferencias = "misPref";
    public static final String Usuario = "llaveUsuario";
    public Button btndelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Codigo bottom menu
        //inicializamos variables
        BottomNavigationView bottomNavigationView =  findViewById(R.id.navigation);

        //seteamos la opcion por defecto
        bottomNavigationView.setSelectedItemId(R.id.hometab);

        //podemos hacer un listener para el elemento seleccionado
        // set
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //vamos usar un switch en false
                switch (item.getItemId()){
                    case R.id.hometab:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.readtab:
                        startActivity(new Intent(getApplicationContext(), Read.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.updatetab:
                        startActivity(new Intent(getApplicationContext(), Update.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.deletetab:
                        startActivity(new Intent(getApplicationContext(), Delete.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.sqlitetab:
                        startActivity(new Intent(getApplicationContext(), sqliteactivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            };
        });

        //Codigo para cargar de sharedPreferences el nombre de usuario

        textobienvenida = (TextView) findViewById(R.id.textobienvenida);
        sharedPreferences = getSharedPreferences(misPreferencias, Context.MODE_PRIVATE);

        textobienvenida.setText("Bienvenido " + sharedPreferences.getString(Usuario,""));

    }
}