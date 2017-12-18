package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Producto;
import edu.upc.dsa.clientjoc.Grafics.MapaView;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;
import edu.upc.dsa.clientjoc.inputOutput.Response.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosPersonales extends AppCompatActivity {

    // UI references.
    private Button iniciaJoc;
    private Button modificarJugador;
    private Button listarOrdenados;
    private Jugador mijugador;

    //retrofit
    private ApiService mRestAdapter;
    public void llistarproductes(){
        Call productcall = ApiAdapter.getApiService().getProductosPrecio();
        productcall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response<Producto> response) {
                switch (response.code()) {
                    case 200:// tot correcte

                        Intent myIntent = new Intent(LoginActivity.this, DatosPersonales.class);
                        Jugador jug = response.body();
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            String jsonResult = mapper.writeValueAsString(jug);
                            myIntent.putExtra("jugador", jsonResult); //Optional parameters
                            showProgress(false);
                            LoginActivity.this.startActivity(myIntent);
                        }catch (Exception e){
                            showLoginError("no serializable");
                        }
                        //enviar jugador rebut nova activitat
                        break;
                    case 204://la contrassenya esta malament
                        showLoginError(getString(R.string.error_password));
                        break;
                    case 500://el email no existeix
                        showLoginError(getString(R.string.error_user));
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //servei rest, singleton
        mRestAdapter =  ApiAdapter.getApiService();
        iniciaJoc = (Button) findViewById(R.id.bIniciaJoc);
        modificarJugador= (Button) findViewById(R.id.bModificar);
        listarOrdenados = (Button) findViewById(R.id.bListarOrdenado);

        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.

        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
            mijugador = jugador;
            EditText fab = (EditText) findViewById(R.id.namebox);
            fab.setEnabled(false);
            fab.setText(jugador.getNom());

            fab = (EditText) findViewById(R.id.emailbox);
            fab.setEnabled(false);
            fab.setText(jugador.getEmail());

            final String mail = ((EditText) findViewById(R.id.emailbox)).getText().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        iniciaJoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DatosPersonales.this, MapaActivity.class);
                DatosPersonales.this.startActivity(myIntent);
            }
        });

        listarOrdenados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llistarproductes();

            }
        });

        modificarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentModificar = new Intent(DatosPersonales.this, ModificarJugador.class);
                ObjectMapper mapper = new ObjectMapper();

                try {
                    String jsonResult = mapper.writeValueAsString(mijugador);
                    intentModificar.putExtra("jugador",jsonResult);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                startActivity(intentModificar);

            }
        });





/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


}
