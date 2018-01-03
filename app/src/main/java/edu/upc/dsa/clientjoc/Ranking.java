package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.upc.dsa.beans.Jugador;

public class Ranking extends AppCompatActivity {

    private Button btnPersonal;
    private  Button btnJugar;

    private Jugador jugador = null;
    private String value;
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //convertint el jugador
        Intent intent = getIntent();
        value = intent.getStringExtra("jugador");
        try {
            jugador = mapper.readValue(value, Jugador.class);
            EditText posicio = (EditText) findViewById(R.id.posJugador);
            posicio.setText(jugador.getId());}catch (Exception e) {
            e.printStackTrace();
        }

        btnPersonal = (Button) findViewById(R.id.btnDatosPers);
        btnJugar = (Button) findViewById(R.id.btnJoc);


        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDades = new Intent(Ranking.this, DatosPersonales.class);
                intentDades.putExtra("jugador", value);
                startActivity(intentDades);

            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJugar = new Intent(Ranking.this, Juego.class);
                intentJugar.putExtra("jugador", value);
                startActivity(intentJugar);

            }
        });
    }
}
