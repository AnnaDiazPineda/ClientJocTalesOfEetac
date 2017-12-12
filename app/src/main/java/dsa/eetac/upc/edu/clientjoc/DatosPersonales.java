package dsa.eetac.upc.edu.clientjoc;

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

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.Grafics.MapaView;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiAdapter;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiService;

public class DatosPersonales extends AppCompatActivity {

    // UI references.
    private Button iniciaJoc;
    private Button modificarJugador;
    private Jugador mijugador;

    //retrofit
    private ApiService mRestAdapter;

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

        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.

        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
            mijugador = jugador;
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditText fab = (EditText) findViewById(R.id.namebox);
        fab.setEnabled(false);
        fab.setText(jugador.getNom());

        fab = (EditText) findViewById(R.id.emailbox);
        fab.setEnabled(false);
        fab.setText(jugador.getEmail());

        final String mail = ((EditText) findViewById(R.id.emailbox)).getText().toString();

        iniciaJoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DatosPersonales.this, MapaActivity.class);
                DatosPersonales.this.startActivity(myIntent);
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
