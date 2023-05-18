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

import org.json.JSONException;
import org.json.JSONObject;

public class Update extends AppCompatActivity {

    //Sharedpreferences variables
    SharedPreferences sharedPreferences;
    TextView usuario, password, usuarionombre;
    public static final String misPreferencias = "misPref";
    public static final String Usuario = "llaveUsuario";
    public Button btndelogin;
    //JSONAPI variables

    TextView idtxt,nombretxt, preciotxt, imgurltxt, idmarcatxt;

    String jsonURLcreate = "https://ubiquitous.udem.edu/~raulms/content/SC3110/Examen/servicio.c.autos.php?";
    String jsonURLupdate = "https://ubiquitous.udem.edu/~raulms/content/SC3110/Examen/servicio.u.autos.php?";

    String datos = "";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        idtxt = findViewById(R.id.idUpdate);
        nombretxt = findViewById(R.id.NameUpdate);
        preciotxt = findViewById(R.id.priceUpdate);
        imgurltxt = findViewById(R.id.imageUpdate);
        idmarcatxt = findViewById(R.id.idmarcaUpdate);
        requestQueue = Volley.newRequestQueue(this);

        //Codigo bottom menu
        //inicializamos variables
        BottomNavigationView bottomNavigationView =  findViewById(R.id.navigation);

        //seteamos la opcion por defecto
        bottomNavigationView.setSelectedItemId(R.id.updatetab);

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
//https://ubiquitous.udem.edu/~raulms/content/SC3110/Examen/servicio.c.autos.php?nombre=PruebaRicardo
// &precio=20000&imagen=https://ubiquitous.udem.edu/~raulms/content/SC3110/images/astra.jpg&id_marca=5
    public void create(View v){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, jsonURLcreate+ "nombre="+ nombretxt.getText().toString()+
                "&precio="+ preciotxt.getText().toString()+"&imagen="+imgurltxt.getText().toString()+
                "&id_marca="+idmarcatxt.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String respuesta = response.getString("Mensaje");
                    Toast.makeText(Update.this, respuesta, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this, "Hay un error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(objectRequest);
    }

    public void update(View v){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, jsonURLupdate+"id_auto="+ idtxt.getText().toString()+ "&nombre="+ nombretxt.getText().toString()+
                "&precio="+ preciotxt.getText().toString()+"&imagen="+imgurltxt.getText().toString()+
                "&id_marca="+idmarcatxt.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String respuesta = response.getString("Mensaje");
                    Toast.makeText(Update.this, respuesta, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this, "Hay un error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(objectRequest);
    }
}