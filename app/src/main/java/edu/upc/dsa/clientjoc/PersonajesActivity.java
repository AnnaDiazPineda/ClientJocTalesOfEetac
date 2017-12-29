package edu.upc.dsa.clientjoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;

/**
 * Created by Marta on 21/12/2017.
 */

public class PersonajesActivity extends AppCompatActivity {
    private ApiService mRestAdapter;
    public Jugador mijugador;
    public Personatge actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personajes);
        mRestAdapter = ApiAdapter.getApiService();
        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.
        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
            mijugador = jugador;
            rellenar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void rellenar()
    {
    //taula personatges
        final TableLayout tableview = (TableLayout) findViewById(R.id.personajes);
        final TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView textview = new TextView(this);
        textview.setText("Personajes disponibles");
        tr1.addView(textview);
        ArrayList<Personatge> mis = new ArrayList<Personatge>();
        mis = mijugador.getPersonatges();
    //taula dels objectes

        final TableRow tr3 = new TableRow(this);

        int characteres = 0;
    for(int i=0;i<mijugador.getPersonatges().size();i++)
        { characteres++;
            Button btn = new Button(this);
            btn.setText(mis.get(i).getNombre());
            btn.setId(mis.get(i).getId());

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final TableLayout tableview2 = (TableLayout) findViewById(R.id.objetos);
                    final TableRow tr2 = new TableRow(PersonajesActivity.this);
                    tr2.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    tableview2.removeAllViews();
                    for(int i =0;i<mijugador.getPersonatges().size();i++)
                    {
                        if(mijugador.getPersonatges().get(i).getId()==v.getId())
                        {
                            List<Objeto> milista =mijugador.getPersonatges().get(i).getArrMisObjetos();

                            for(int j=0;j<milista.size();j++)
                            {//añadir type en objetos y contruir switch
                                if(milista.get(j).getNombre().equals("casco")) {
                                    ImageView mi = new ImageView(PersonajesActivity.this);
                                    mi.setImageResource(R.mipmap.helmet);
                                    tr2.addView(mi);
                                }
                                if(milista.get(j).getNombre().equals("espada")) {
                                    ImageView mi = new ImageView(PersonajesActivity.this);
                                    mi.setImageResource(R.mipmap.espada);
                                    tr2.addView(mi);
                                }
                            }
                            TableLayout.LayoutParams mis = new TableLayout.LayoutParams(100,250);
                            tableview2.setLayoutParams(mis);
                            tableview2.addView(tr2);

                        }

                    }
                }
            });

            if(i>1)
            {
                tr3.addView(btn);
            }
            else{
                tr1.addView(btn);
            }
        }
    tableview.addView(tr1);
    tableview.addView(tr3);


    }
}

