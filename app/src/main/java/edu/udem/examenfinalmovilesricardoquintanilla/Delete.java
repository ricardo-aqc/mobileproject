package edu.udem.examenfinalmovilesricardoquintanilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Delete extends AppCompatActivity {

    //Sharedpreferences variables
    SharedPreferences sharedPreferences;
    TextView usuario, password, usuarionombre;
    public static final String misPreferencias = "misPref";
    public static final String Usuario = "llaveUsuario";
    public Button btndelogin;

//Variables JSONAPI
TextView deletetxt;
    ImageView img;

    String jsonURL = "https://ubiquitous.udem.edu/~raulms/content/SC3110/Examen/servicio.d.autos.php?id_auto=";
    String datos = "";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deletetxt = findViewById(R.id.idDelete);
        requestQueue = Volley.newRequestQueue(this);



        //Codigo bottom menu
        //inicializamos variables
        BottomNavigationView bottomNavigationView =  findViewById(R.id.navigation);

        //seteamos la opcion por defecto
        bottomNavigationView.setSelectedItemId(R.id.deletetab);

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

    public void delete(View v){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL+deletetxt.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String respuesta = response.getString("Mensaje");
                    Toast.makeText(Delete.this, respuesta, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Delete.this, "Hay un error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(objectRequest);
    }







}