package edu.udem.examenfinalmovilesricardoquintanilla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

//Sharedpreferences variables
    SharedPreferences sharedPreferences;
    TextView usuario, password, usuarionombre;
    public static final String misPreferencias = "misPref";
    public static final String Usuario = "llaveUsuario";
    public Button btndelogin;
//JSONAPI variables
String datos = "";
RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.UsuarioInput);
        password = findViewById(R.id.PasswordInput);
        usuarionombre = findViewById(R.id.usuarionombre);
        requestQueue = Volley.newRequestQueue(this);





////////////Codigo para shared preferences
        sharedPreferences = getSharedPreferences(misPreferencias, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Usuario)) {
            usuario.setText(sharedPreferences.getString(Usuario, ""));
        }

        btndelogin = findViewById(R.id.buttonlogin);


    }

    public void login(View v){

        /////////////Codigo para sacar del json api
        String jsonURL = "https://ubiquitous.udem.edu/~raulms/content/SC3110/Examen/servicio.login.php?usuario="+usuario.getText().toString()+"&password="+password.getText().toString();
        // Usamos JsonArrayRequest
        String nombreapellido="";
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

                                // Agarro el 3er elemnto con nombre y apellido
                                JSONObject perfilUsuario = response.getJSONObject(2);
                                String nombreUsuario = perfilUsuario.getString("Nombre");
                                String apellidoUsuario = perfilUsuario.getString("Appaterno");
                                String nombreapellido= nombreUsuario+" " + apellidoUsuario;
                                usuarionombre.append("Hola " + nombreUsuario + apellidoUsuario);

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

        String u = nombreapellido;
        System.out.println(u);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Usuario, u);
        editor.commit();
        Intent actividad = new Intent(Login.this, Home.class);
        startActivity(actividad);

    }

}