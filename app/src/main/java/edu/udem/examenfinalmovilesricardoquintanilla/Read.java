package edu.udem.examenfinalmovilesricardoquintanilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Read extends AppCompatActivity {

    //Sharedpreferences variables
    SharedPreferences sharedPreferences;
    TextView usuario, password, usuarionombre;
    public static final String misPreferencias = "misPref";
    public static final String Usuario = "llaveUsuario";
    public Button btndelogin;
    //JSONAPI variables
    String datos = "";
    RequestQueue requestQueue;
    //Se establece el database helper y database y boton
    DatabaseHelper CarTopDB;
    SQLiteDatabase database;
    boolean isInserted, isUpdated;


    TextView auto, precio;
    ImageView imgcarro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        auto = findViewById(R.id.nombreauto);
        precio = findViewById(R.id.precioauto);
        imgcarro = findViewById(R.id.imagenCarro);
        requestQueue = Volley.newRequestQueue(this);
        CarTopDB = new DatabaseHelper(this);


        //Codigo bottom menu
        //inicializamos variables
        BottomNavigationView bottomNavigationView =  findViewById(R.id.navigation);

        //seteamos la opcion por defecto
        bottomNavigationView.setSelectedItemId(R.id.readtab);

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
    }


    public void read (View v){
        /////////////Codigo para sacar del json api
        String jsonURL = "https://ubiquitous.udem.edu/~raulms/content/SC3110/Examen/servicio.autos.php";
        // Usamos JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                jsonURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Checo la respuesta
                        Log.d("Login", "Response is " + response.toString());

                        // Dentro del JSON
                        try{
                            // Agarramos el elemento que quiero dentro del arreglo

                            // Agarro el 3er elemnto porque solo quiero uno
                            JSONObject carroobjeto = response.getJSONObject(2);

                            String nombreauto = carroobjeto.getString("Nombre");
                            auto.append("Auto: " + nombreauto);

                            //accdemos a la marca
                            String marcaauto = carroobjeto.getString("Precio");
                            precio.append("Marca: " + marcaauto);

                            //accdemos a la imagen
                            String imagen = carroobjeto.getString("imagen");
                            Picasso.get().load(imagen).into(imgcarro);

                            // Get the current student (json object) data

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void submitTop(View v){
        String titulotopmovie = auto.getText().toString();
        String id = "";
        Cursor cursor = CarTopDB.retrieveID();
        if (cursor != null){
            if (cursor.moveToFirst())
                id = cursor.getString(0);
        }else{
            id = "1";
        }
        int idint = Integer.parseInt(id)+1;
        isInserted = CarTopDB.insertDataTopCar(Integer.toString(idint), titulotopmovie);
        if (isInserted == true)
            Toast.makeText(Read.this, "Datos insertados correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Read.this, "Algo pasó", Toast.LENGTH_LONG).show();
    }

    public void deleteTop(View v){
        String titulotopmovie = auto.getText().toString();
        int delRows = CarTopDB.deleteDataTopMovie(titulotopmovie);

        if(titulotopmovie.equals("")){
            Toast.makeText(Read.this, "Cargue los datos primero", Toast.LENGTH_LONG).show();
        }
        else{
            if(delRows > 0){
                Toast.makeText(Read.this, "Carro borrada de top", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Read.this, "Carro no borrada ni encontrada en top", Toast.LENGTH_LONG).show();
            }
        }
    }
}