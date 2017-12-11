package dsa.eetac.upc.edu.clientjoc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import dsa.eetac.upc.edu.clientjoc.ClassesClon.Jugador;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiAdapter;
import dsa.eetac.upc.edu.clientjoc.inputOutput.ApiService;
import dsa.eetac.upc.edu.clientjoc.ClassesClon.Answer;
import dsa.eetac.upc.edu.clientjoc.inputOutput.Response.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarJugador extends AppCompatActivity {

    // UI references.
    private EditText mcanviContraView;
    private EditText mdadesView;
    private EditText mcanviNomView;
    private Button cancelar;
    private Button ferCanvis;
    private ApiService mRestAdapter;
    private TextView mailFix;
    private Jugador mijugador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        mRestAdapter =  ApiAdapter.getApiService();
        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.
        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
            mijugador = jugador;
            } catch (IOException e) {
            e.printStackTrace();
        }
        mcanviContraView = (EditText) findViewById(R.id.editContra);
        mcanviContraView.setText(jugador.getContrasenya());
        mcanviNomView = (EditText) findViewById(R.id.editNom);
        mcanviNomView.setText(jugador.getNom());
        ferCanvis = (Button) findViewById(R.id.bAplicar);
        ferCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempUpdate();
            }
        });
    }
    private void attempUpdate() {
        final String contrassenyaActual = mcanviContraView.getText().toString();
        final String nomActual = mcanviNomView.getText().toString();
        if (contrassenyaActual.equals(mijugador.getContrasenya()) && (nomActual.equals(mijugador.getNom()))) {
            Context context = getApplicationContext();
            CharSequence text = "Les dades introduides no son correctes";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }// cas que no hagi modificat
        else if (TextUtils.isEmpty(contrassenyaActual) || (TextUtils.isEmpty(nomActual))) {
            Context context = getApplicationContext();
            CharSequence text = "Hi ha algun camp en blanc";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }// cas que no hagi introduit res
        else {
            Call<Answer>  updatecall = ApiAdapter.getApiService().updateJugador(mijugador.getEmail(),new Login(nomActual,contrassenyaActual));
            updatecall.enqueue(new Callback<Answer>() {

                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                    Context context = getApplicationContext();
                    CharSequence text = response.body().getResposta();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if (response.body().getResposta().equals("OK")){

                        mijugador.setContrasenya(contrassenyaActual);
                    mijugador.setNom(nomActual);
                    Intent myIntent = new Intent(ModificarJugador.this, DatosPersonales.class);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String jsonResult = mapper.writeValueAsString(mijugador);
                        myIntent.putExtra("jugador", jsonResult); //Optional parameters
                        ModificarJugador.this.startActivity(myIntent);
                    } catch (Exception e) {

                    }
                }
                else{
                        text="Les dades no s'han pogut actualitzar";
                        toast.show();
                    }

                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {

                }
            });
        }
    }

    /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        //servei rest, singleton
        mRestAdapter =  ApiAdapter.getApiService();

        canviNom = (EditText) findViewById(R.id.editNom);
        canviContra = (EditText) findViewById(R.id.editContra);
        cancelar = (Button) findViewById(R.id.bCancelar);
        ferCanvis = (Button) findViewById(R.id.bAplicar);

        //reben el mail de datos Personales
        Intent intent = getIntent();
        String value = intent.getStringExtra("jugador"); //if it's a string you stored.
        ObjectMapper mapper = new ObjectMapper();
        Jugador jugador = null;
        try {
            jugador = mapper.readValue(value, Jugador.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mailFix.setText(jugador.getEmail());

        //acció del boto
        ferCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptModificacio();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ModificarJugador.this, DatosPersonales.class);
                ModificarJugador.this.startActivity(myIntent);


            }
        });



    }

    private void attemptModificacio() {

        boolean cancel = false;
        View focusView = null;


        // Store values at the time of the login attempt.
        String nom = canviNom.getText().toString();
        String contrasenya = canviContra.getText().toString();
        String mail = mailFix.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(contrasenya) && !isPasswordValid(contrasenya)) {
            canviContra.setError(getString(R.string.error_invalid_password));
            focusView = canviContra;
            cancel = true;
        }
        if(!TextUtils.isEmpty(nom)){
            canviNom.setError(getString(R.string.error_user));
            focusView = canviNom;
            cancel = true;
        }else{

            Call<Jugador> modificarCall = ApiAdapter.getApiService().updateJugador(mail, new Login(nom, contrasenya));
            modificarCall.enqueue(new Callback<Jugador>() {
                @Override
                public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                    switch (response.code()) {
                        case 200:// tot correcte
                            showLoginError("login correcte");
                            Intent myIntent = new Intent(ModificarJugador.this, DatosPersonales.class);
                            Jugador jug = response.body();
                            ObjectMapper mapper = new ObjectMapper();
                            try {
                                String jsonResult = mapper.writeValueAsString(jug);
                                myIntent.putExtra("jugador", jsonResult); //Optional parameters
                                ModificarJugador.this.startActivity(myIntent);
                            } catch (Exception e) {
                                showLoginError("no serializable");
                            }
                            //enviar jugador rebut nova activitat
                            break;
                        case 204://la contrassenya esta malament
                            showLoginError(getString(R.string.error_password));
                            break;
                        case 500://el email no existeix
                            showLoginError(getString(R.string.error_user));
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Jugador> call, Throwable t) {
                    showLoginError(getString(R.string.error_network));
                    returnToInit();
                    return;

                }
            });

        }

    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }
    private boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
    private void returnToInit(){
        //showProgress(false);
        //mEmailView.requestFocus();
    }*/
}
