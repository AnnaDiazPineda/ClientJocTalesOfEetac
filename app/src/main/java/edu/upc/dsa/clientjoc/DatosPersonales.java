package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private Button provarMiniJoc;

    private Jugador mijugador;
    private Button veureRanking;

    private ObjectMapper mapper = new ObjectMapper();
    private String value;

    //retrofit
    private ApiService mRestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ObjectMapper mapper = new ObjectMapper();
        //servei rest, singleton
        mRestAdapter =  ApiAdapter.getApiService();
        iniciaJoc = (Button) findViewById(R.id.bIniciaJoc);
        modificarJugador= (Button) findViewById(R.id.bModificar);
        donarseBaixa = (Button) findViewById(R.id.btnBaixaJugador);
        personajes =(Button) findViewById(R.id.personajesb);
        veureRanking = (Button) findViewById(R.id.btnRanking) ;
        Intent intent = getIntent();
        ; //if it's a string you stored.



        try {

            mijugador = SingletonDades.getInstancia().getJugador();
            EditText fab = (EditText) findViewById(R.id.namebox);
            fab.setEnabled(false);
            fab.setText(mijugador.getNom());

            fab = (EditText) findViewById(R.id.emailbox);
            fab.setEnabled(false);
            fab.setText(mijugador.getEmail());

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
                startActivity(intentModificar);

            }
        });

        veureRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRanking = new Intent(DatosPersonales.this, Ranking.class);
                startActivity(intentRanking);
            }
        });

        donarseBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEliminar = new Intent(DatosPersonales.this, BaixaJugador.class);
                startActivity(intentEliminar);

            }
        });
        personajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewPersonatge = new Intent(DatosPersonales.this, PersonajesActivity.class);
                ObjectMapper mapper = new ObjectMapper();
                startActivity(intentNewPersonatge);
            }
        });








    }


}
