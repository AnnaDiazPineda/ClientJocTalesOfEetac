package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ranking extends AppCompatActivity {

    private Button btnPersonal;
    private  Button btnJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        btnPersonal = (Button) findViewById(R.id.btnDatosPers);
        btnJugar = (Button) findViewById(R.id.btnJoc);

        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDades = new Intent(Ranking.this, DatosPersonales.class);
                startActivity(intentDades);

            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJugar = new Intent(Ranking.this, MapaActivity.class);
                startActivity(intentJugar);

            }
        });
    }
}
