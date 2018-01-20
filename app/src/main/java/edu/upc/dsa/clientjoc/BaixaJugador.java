package edu.upc.dsa.clientjoc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.upc.dsa.beans.Answer;
import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Login;
import edu.upc.dsa.beans.mapa.Mapa;
import edu.upc.dsa.clientjoc.Grafics.MapaView;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaixaJugador extends AppCompatActivity {

    private Button tornar;
    private Button eliminar;
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

        //servei rest, singleton
        mRestAdapter = ApiAdapter.getApiService();
        jugador = SingletonDades.getInstancia().getJugador();


        tornar = (Button) findViewById(R.id.btnTornar);
        eliminar = (Button) findViewById(R.id.btnBaixa);
        mail = (EditText) findViewById(R.id.edMail);
        contra1 = (EditText) findViewById(R.id.edContrauno);
        contra2 = (EditText) findViewById(R.id.edContrados);
        usuariMarxant = (TextView) findViewById(R.id.userMarxant);

        mail.setText(jugador.getEmail());


        tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPerfil = new Intent(BaixaJugador.this, MapaActivity.class);
                //intentPerfil.putExtra("jugador", value);
                startActivity(intentPerfil);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contra1.getText().toString().equals(jugador.contrasenya) && contra1.getText().toString().equals(contra2.getText().toString()) )
                {
                    Call<Answer> deleteCall = ApiAdapter.getApiService().deleteJugador(jugador.getEmail(), new Login(jugador.getNom(),jugador.getContrasenya()));


                    deleteCall.enqueue(new Callback<Answer>() {
                        @Override
                        public void onResponse(Call<Answer> call, Response<Answer> response) {
                            switch (response.code()) {
                                case 200:
                                    Answer answer = response.body();
                                    Context context = getApplicationContext();

                                    CharSequence text = answer.getResposta();
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    Intent intentPerfil = new Intent(BaixaJugador.this, LoginActivity.class);
                                    //intentPerfil.putExtra("jugador", value);
                                    startActivity(intentPerfil);
                                    finish();
                                    break;
                                case 204://cas de no hi ha partida desada
                                    Context context2 = getApplicationContext();
                                    CharSequence text2 = "error eliminant usr";
                                    int duration2 = Toast.LENGTH_SHORT;
                                    Toast toast2 = Toast.makeText(context2, text2, duration2);
                                    toast2.show();
                                    finish();
                                    break;
                                case 500://el email no existeix

                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<Answer> call, Throwable t) {
                            Context context = getApplicationContext();
                            CharSequence text = "error";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            finish();
                            t.printStackTrace();
                            return;

                        }


                    });
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "LEs contrassenyes no son iguals";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    return;
                }

            }
        });


    }
}
