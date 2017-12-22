package edu.upc.dsa.clientjoc;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

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

public class MapaActivity extends AppCompatActivity{
    Mapa mapa;
    Personatge pers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_layout);
        getMapaFromServer();

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
                mapa.moure(0,1,pers);
            }
        });
        Button bt4 = (Button) findViewById(R.id.right);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapa.moure(0,-1,pers);
            }
        });

    }


    public void getMapaFromServer() {

        Call<String> mapaCall = ApiAdapter.getApiService().getMapa();
        mapaCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        showLoginError("mapa correcte");
                        String mapastr= response.body();
                        System.out.println(mapastr);

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

                        try {
                            mapa = mapper.readValue(mapastr, Mapa.class);
                            MapaView v = (MapaView)findViewById(R.id.surfaceView1);
                            v.setMap(mapa);
                            pers = mapa.findJugador();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //enviar jugador rebut nova activitat
                        break;
                    case 204://la contrassenya esta malament
                        showLoginError("204");
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

}