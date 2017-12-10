package dsa.eetac.upc.edu.clientjoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiAdapter;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiService;
import dsa.eetac.upc.edu.clientjoc.inputOutput.Registre;

public class DatosPersonales extends AppCompatActivity {

    // UI references.
    private Button tancarSessio;
    private Button modificarJugador;

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
        tancarSessio = (Button) findViewById(R.id.bTancarSessio);
        modificarJugador= (Button) findViewById(R.id.bModificar);

        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.

        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditText fab = (EditText) findViewById(R.id.namebox);
        fab.setText(jugador.getNom());

        fab = (EditText) findViewById(R.id.emailbox);
        fab.setText(jugador.getEmail());

        final String mail = ((EditText) findViewById(R.id.emailbox)).getText().toString();

        tancarSessio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        modificarJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentModificar = new Intent(DatosPersonales.this, ModificarJugador.class);
                intentModificar.putExtra("mail", mail);
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
