package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;

import edu.upc.dsa.beans.Jugador;


/**
 * Created by Marta on 6/12/2017.
 */

public class AccionesUser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.upc.dsa.clientjoc.R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(edu.upc.dsa.clientjoc.R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.

        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //EditText fab = (EditText) findViewById(R.id.welcomebox);
        //fab.setText(jugador.getNom());
    }
}
