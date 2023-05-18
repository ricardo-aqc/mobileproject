package edu.udem.examenfinalmovilesricardoquintanilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class sqliteactivity extends AppCompatActivity {

    //Actividad que muestra los carros favoritos/top guardados en SQLite

    //Se establece el database helper y database y boton
    DatabaseHelper CarTopDB;
    SQLiteDatabase database;
    Button view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqliteactivity);


        //Inicializamos los componentes
        CarTopDB = new DatabaseHelper(this);
        view = findViewById(R.id.buttonviewtop);


        //Codigo bottom menu
        //inicializamos variables
        BottomNavigationView bottomNavigationView =  findViewById(R.id.navigation);

        //seteamos la opcion por defecto
        bottomNavigationView.setSelectedItemId(R.id.sqlitetab);

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


        //Codigo para ver carros preferidos


    }


    public void showMessage(String title, String message) {
        //Te aparece una pequena ventana como si fuera un popup en web
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    //Metodo de show data que checa la base de datos por la tabla creada y que los muestra en un popup
    public void showData() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = CarTopDB.retrieveData();
                if (cursor.getCount() == 0) {
                    showMessage("Error", "Sin datos");
                    return;
                }
                // StringBuffer separa un espacio de memoria que guarda los datos como me llegue de la consulta del query
                // Si no hay un buffer, solo se guardaria un string
                // Es muy utliizado cuandod detecte que existe datos itera sobre ellos y los guarda
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    //El appende nos va a agarrar cada columna con su numero empezando desde 0
                    buffer.append("Id: " + cursor.getString(0) + "\n");
                    buffer.append(cursor.getString(1) + "\n");
                }
                showMessage("Lista de Top Peliculas", buffer.toString());
            }
        });
    }
}