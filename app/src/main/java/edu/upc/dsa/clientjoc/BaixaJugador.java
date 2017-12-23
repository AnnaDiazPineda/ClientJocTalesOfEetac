package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;


public class BaixaJugador extends AppCompatActivity {

    private Button tornar;
    private  Button eliminar;
    private EditText mail;
    private EditText contra1;
    private EditText contra2;
    private TextView usuariMarxant;

    private Jugador jugador = null;
    private ObjectMapper mapper = new ObjectMapper();
    private String value;
    //retrofit
    private ApiService mRestAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baixa_jugador);

        tornar = (Button) findViewById(R.id.btnTornar);
        eliminar = (Button) findViewById(R.id.btnBaixaJugador);
        mail = (EditText) findViewById(R.id.edMail);
        contra1 = (EditText) findViewById(R.id.edContrauno);
        contra2 = (EditText) findViewById(R.id.edContrados);
        usuariMarxant = (TextView) findViewById(R.id.userMarxant);

        //servei rest, singleton
        mRestAdapter =  ApiAdapter.getApiService();

        Intent intent = getIntent();
        value = intent.getStringExtra("jugador"); //if it's a string you stored.

        try {
            jugador = mapper.readValue(value, Jugador.class);
            usuariMarxant.setText("Segu que vols marxar? " +jugador.getNom() + ".");


        } catch (Exception e) {
            e.printStackTrace();
        }

        tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPerfil = new Intent(BaixaJugador.this, MapaActivity.class);
                intentPerfil.putExtra("jugador", value);
                startActivity(intentPerfil);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


    }
}
