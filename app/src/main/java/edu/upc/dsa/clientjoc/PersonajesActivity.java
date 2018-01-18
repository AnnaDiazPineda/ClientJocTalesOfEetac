package edu.upc.dsa.clientjoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Objeto;
import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marta on 21/12/2017.
 */

public class PersonajesActivity extends AppCompatActivity {
    private ApiService mRestAdapter;
    public Jugador mijugador;
    public Personatge seleccionat;
    public TextView nombreview;
    public TextView nivelview;
    public TextView ataqueview;
    public TextView resistenciaview;
    public TextView defensaview;
    public ImageView miimagen;
    public Button initBtn;
    public Button newBtn;
    public int limitCharacters;
    public ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personajes);
        mRestAdapter = ApiAdapter.getApiService();
        Intent intent = getIntent();
        mapper = new ObjectMapper();
        associatedViews();
        initBtn.setEnabled(false);
        newBtn.setEnabled(true);
        mijugador = SingletonDades.getInstancia().getJugador();
        rellenar();

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistre = new Intent(PersonajesActivity.this, NewPersonaje.class);
                startActivity(intentRegistre);
            }
        });

        initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistre = new Intent(PersonajesActivity.this, NewPersonaje.class);
                crearNewMapaPerPersonatgeSellecionat();
                startActivity(intentRegistre);
            }
        });
    }


    private void crearNewMapaPerPersonatgeSellecionat() {

        // If jugador no te personatge , dir que es crei un al menu android corresponent i interrompre la crida a NewMapa

        Call<String> mapaCall = ApiAdapter.getApiService().newMapa(mijugador.getId(), seleccionat);

        mapaCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        showLoginError("mapa creat ! nivell associat al mapa:");
                        //enviar jugador rebut nova activitat
                        break;
                    case 204://cas de no hi ha partida desada
                        Context context = getApplicationContext();
                        CharSequence text = "ERROR CREANT UNA";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        finish();
                        break;
                    case 500://el email no existeix
                        showLoginError("500");
                        break;
                }
            }

            public void onFailure(Call<String> call, Throwable t) {
                showLoginError("error");
                t.printStackTrace();
                return;
            }
        });
    }

    public void associatedViews() {
        nombreview = (TextView) findViewById(R.id.nombreview);
        nivelview = (TextView) findViewById(R.id.nivelview);
        ataqueview = (TextView) findViewById(R.id.ataqueview);
        resistenciaview = (TextView) findViewById(R.id.resistenciaview);
        defensaview = (TextView) findViewById(R.id.defensaview);
        miimagen = (ImageView) findViewById(R.id.charactericon);
        initBtn = (Button) findViewById(R.id.initBtn);
        newBtn = (Button) findViewById(R.id.newbtn);
    }

    public void setCharacterPicture() {
        switch (seleccionat.getTipo()) {
            case 1:
                miimagen.setImageResource(R.mipmap.type1);
                break;
            case 2:
                miimagen.setImageResource(R.mipmap.type2);
                break;
            case 3:
                miimagen.setImageResource(R.mipmap.type3);
                break;
            case 4:
                miimagen.setImageResource(R.mipmap.type4);
                break;

        }

    }

    public void setDatos() {
        nombreview.setText(seleccionat.getNombre());
        nivelview.setText("" + seleccionat.getNivel());
        ataqueview.setText("" + seleccionat.getAtaque());
        resistenciaview.setText("" + seleccionat.getResistencia());
        defensaview.setText("" + seleccionat.getResistencia());
    }

    public void addCharactersWithObjects(View v) {
        final TableLayout tableview2 = (TableLayout) findViewById(R.id.objetos);
        final TableRow tr2 = new TableRow(PersonajesActivity.this);
        tr2.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tableview2.removeAllViews();
        for (int i = 0; i < mijugador.getPersonatges().size(); i++) {
            if (mijugador.getPersonatges().get(i).getId() == v.getId()) {
                List<Objeto> milista = mijugador.getPersonatges().get(i).getArrMisObjetos();

                for (int j = 0; j < milista.size(); j++) {//añadir type en objetos y contruir switch
                    if (milista.get(j).getNombre().equals("casco")) {
                        ImageView mi = new ImageView(PersonajesActivity.this);
                        mi.setImageResource(R.mipmap.helmet);
                        tr2.addView(mi);
                    }
                    if (milista.get(j).getNombre().equals("espada")) {
                        ImageView mi = new ImageView(PersonajesActivity.this);
                        mi.setImageResource(R.mipmap.espada);
                        tr2.addView(mi);
                    }
                }
                TableLayout.LayoutParams mis = new TableLayout.LayoutParams(100, 250);
                tableview2.setLayoutParams(mis);
                tableview2.addView(tr2);

            }

        }
    }

    public void rellenar() {
        //taula personatges
        final TableLayout tableview = (TableLayout) findViewById(R.id.personatges);
        final TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView textview = new TextView(this);
        textview.setText("Personajes disponibles");
        tr1.addView(textview);
        final HashMap<Integer, Personatge> mihash = new HashMap<Integer, Personatge>();
        //recibimos personaje añadimos
        ArrayList<Personatge> mis = new ArrayList<Personatge>();
        mis = mijugador.getPersonatges();
        //taula dels objectes

        final TableRow tr3 = new TableRow(this);

        limitCharacters = 0;
        for (int i = 0; i < mijugador.getPersonatges().size(); i++) {
            limitCharacters++;
            Button btn = new Button(this);
            mihash.put(mis.get(i).getId(), mis.get(i));
            btn.setText(mis.get(i).getNombre());
            btn.setId(mis.get(i).getId());

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    seleccionat = mihash.get(v.getId());
                    setDatos();
                    setCharacterPicture();
                    addCharactersWithObjects(v);
                    initBtn.setEnabled(true);


                }
            });

            if (i > 1) {
                tr3.addView(btn);
            } else {
                tr1.addView(btn);
            }
            if (limitCharacters > 3) {
                newBtn.setEnabled(false);
            }
        }
        tableview.addView(tr1);
        tableview.addView(tr3);


    }

    private void showLoginError(String error) {
        Toast.makeText(this.getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

}

