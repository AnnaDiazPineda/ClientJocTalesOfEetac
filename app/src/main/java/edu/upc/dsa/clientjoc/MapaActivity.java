package edu.upc.dsa.clientjoc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import edu.upc.dsa.DAOG.DAOMapa;
import edu.upc.dsa.beans.ContexteDelJoc;
import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.beans.mapa.Mapa;
import edu.upc.dsa.clientjoc.Grafics.MapaView;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anna on 12/12/2017.
 */

public class MapaActivity extends AppCompatActivity {
    DAOMapa mapa;
    Personatge pers;
    private String value;
    private ObjectMapper mapper = new ObjectMapper();
    private Jugador mijugador;
    private String mapaString;
    private Mapa mimapa=null;
    private MapaView mapaView;
    private String mapaState;
    DAOMapa sender = new DAOMapa(10,10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_layout);
        Intent intent = getIntent();
        mijugador =  SingletonDades.getInstancia().getJugador();
        mapaView = findViewById(R.id.surfaceView1);

        getMapaFromServer();//if it's a string you stored.

        Button bt = (Button) findViewById(R.id.upButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapa.moure(-1,0,pers);
            }
        });
        Button bt2 = (Button) findViewById(R.id.down);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapa.moure(1,0,pers);
            }
        });

        Button bt3 = (Button) findViewById(R.id.left);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContexteDelJoc.getDialogador().isNoPong()) {
                    mapa.moure(0, -1, pers);
                }else {
                    mapaView.moureBat(-1);
                }
            }
        });
        Button bt4 = (Button) findViewById(R.id.right);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContexteDelJoc.getDialogador().isNoPong()) {
                    mapa.moure(0, +1, pers);
                }else {
                    mapaView.moureBat(+1);
                }

            }
        });
        ContexteDelJoc.setDialogador(new DialogadorImplAndroid(this,getApplicationContext(), (MapaView)findViewById(R.id.surfaceView1)));
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMapToServer();

            }
        });
    }
//***

    public void saveMapToServer() {
        sender = mapa;

        if(pers.getNivel() != mapa.getNivel()){
            ContexteDelJoc.getDialogador().globus("el mapa nomes es pot guardar en l'ultim nivell desbloqueijat");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            mapaState = mapper.writeValueAsString(sender);
            mapaState = mapaState.substring(0,mapaState.length()-12);
            mapaState = mapaState +"}]";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<String> mapaCall = ApiAdapter.getApiService().SaveMapa(mapaState,mijugador.getId());
                mapaCall.request().header("application/json");
                mapaCall.enqueue(new Callback<String>() {


                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }




                });

            }
        }).start();

    }


    public void getMapaFromServer() {
/**** de moment id de manera estatica**/
        Call<String> mapaCall = ApiAdapter.getApiService().getMapa(mijugador.getId());
        mapaCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        //showLoginError("mapa correcte");
                        String mapastr= response.body();
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

                        try {
                            mapa = mapper.readValue(mapastr, DAOMapa.class);
                            ContexteDelJoc.getDialogador().globus("el nivell associat al mapa es:"+mapa.getNivel());
                            MapaView v = (MapaView)findViewById(R.id.surfaceView1);
                            v.setMap(mapa);

                            pers = mapa.findPersonatge();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //enviar jugador rebut nova activitat
                        break;
                    case 204://cas de no hi ha partida desada
                        Context context = getApplicationContext();
                        CharSequence text = "No tens cap partida iniciada ves a personatges i inician una";
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

    private void showLoginError(String error) {
        Toast.makeText(this.getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }




    public void crearNewMapaPerPersonatgeSellecionat() {

        // If jugador no te personatge , dir que es crei un al menu android corresponent i interrompre la crida a NewMapa

        Call<String> mapaCall = ApiAdapter.getApiService().newMapa(mijugador.getId(), pers);

        mapaCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        showLoginError("mapa creat ! nivell associat al mapa:");
                        getMapaFromServer();
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
    public void actualizeToDatabase(){
        Call<String> characterCall = ApiAdapter.getApiService().ActualizeTheCharacter(pers);
        characterCall.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            if(response.body().equals("OK")){
                ContexteDelJoc.dialogador.globus(" El objecte s'ha afegit al personatge");
            }

            if(response.body().equals("KO")){
                ContexteDelJoc.dialogador.globus(" Hi ha un problema al servidor, intenta-ho més tard");
            }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void EstablirMapabuit() {
        DAOMapa mimapa = new DAOMapa(10,10);
        int nuevoNivel = mapa.getNivel()-1;
        mimapa = mimapa.readMapFromile(nuevoNivel);
        mimapa.setNivel(nuevoNivel);
        mimapa.putElement(7,7,pers);
        MapaView v = (MapaView)findViewById(R.id.surfaceView1);
        v.setMap(mimapa);
        mapa = mimapa;
    }
}