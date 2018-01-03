package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.upc.dsa.beans.Jugador;

import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;

public class DatosPersonales extends AppCompatActivity {

    // UI references.
    private Button iniciaJoc;
    private Button modificarJugador;
    private Button donarseBaixa;
    private Button personajes;

    private Jugador mijugador;
    private Button veureRanking;

    private ObjectMapper mapper = new ObjectMapper();
    private String value;
    private Jugador jugador= null;
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
        donarseBaixa = (Button) findViewById(R.id.btnBaixaJugador);
        personajes =(Button) findViewById(R.id.personajesb);
        veureRanking = (Button) findViewById(R.id.btnRanking) ;
        Intent intent = getIntent();
        value = intent.getStringExtra("jugador"); //if it's a string you stored.

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

                Intent intentMapa = new Intent(DatosPersonales.this, MapaActivity.class);
                DatosPersonales.this.startActivity(intentMapa);
            }
        });

        modificarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentModificar = new Intent(DatosPersonales.this, ModificarJugador.class);

                try {

                    intentModificar.putExtra("jugador",value);
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                    startActivity(intentModificar);

            }
        });

        veureRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRanking = new Intent(DatosPersonales.this, Ranking.class);
                try {
                  intentRanking.putExtra("jugador",value);
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                startActivity(intentRanking);
            }
        });

        donarseBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEliminar = new Intent(DatosPersonales.this, BaixaJugador.class);
                try {
                   intentEliminar.putExtra("jugador", value);

                }catch (Exception e){
                    e.getCause();
                }
                startActivity(intentEliminar);

            }
        });
        personajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DatosPersonales.this, PersonajesActivity.class);
                ObjectMapper mapper = new ObjectMapper();

                try {
                    String jsonResult = mapper.writeValueAsString(mijugador);
                    myIntent.putExtra("jugador",jsonResult);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                startActivity(myIntent);
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


    public void iniciarJuegoBueno(View view) {
        Intent intentMapa = new Intent(DatosPersonales.this, Juego.class);
        intentMapa.putExtra("jugador", value);
        DatosPersonales.this.startActivity(intentMapa);
    }
}
