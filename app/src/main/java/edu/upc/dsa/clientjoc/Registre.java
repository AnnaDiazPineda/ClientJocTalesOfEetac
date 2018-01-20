package edu.upc.dsa.clientjoc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Login;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registre extends AppCompatActivity {

    // UI references.
    private EditText nouNom;
    private EditText novaContra;
    private EditText nouMail;
    private Button btnRegistre;
    //retrofit
    private ApiService mRestAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        //servei rest, singleton
        mRestAdapter =  ApiAdapter.getApiService();

        nouNom = (EditText) findViewById(R.id.rNouNom);
        nouMail = (EditText) findViewById(R.id.rMailNou);
        novaContra = (EditText) findViewById(R.id.rContrasenya);
        btnRegistre = (Button) findViewById(R.id.bregistre);

        //acció del boto
        btnRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptRegistre();
            }
        });

    }

    private void attemptRegistre() {
        // Store values at the time of the login attempt.
        String nom = nouNom.getText().toString();
        String mail = nouMail.getText().toString();
        String contrasenya = novaContra.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(contrasenya) && !isPasswordValid(contrasenya)) {
            novaContra.setError(getString(R.string.error_invalid_password));
            focusView = novaContra;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(mail)) {
            nouMail.setError(getString(R.string.error_field_required));
            focusView = nouMail;
            cancel = true;
        } else if (!isEmailValid(mail)) {
            nouMail.setError(getString(R.string.error_invalid_email));
            focusView = nouMail;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            Call<Jugador> registreCall = ApiAdapter.getApiService().getNouJugador( mail, new Login(nom,contrasenya));
            registreCall.enqueue(new Callback<Jugador>() {
                @Override
                public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                    switch (response.code()) {
                        case 200:// tot correcte
                            showLoginError("login correcte");
                            Intent myIntent = new Intent(Registre.this, DatosPersonales.class);
                            Jugador jug = response.body();
                            try {

                                SingletonDades.getInstancia().setJugador(jug);//Optional parameters
                                Registre.this.startActivity(myIntent);
                            } catch (Exception e) {
                                showLoginError("no serializable");
                            }
                            //enviar jugador rebut nova activitat
                            break;
                        case 204://la contrassenya esta malament
                            showLoginError(getString(R.string.error_password));
                            break;
                        case 500://el email no existeix
                            showLoginError("Aquest mail ja existeix");
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

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }
    private void returnToInit(){

    }
    //verificar connexió de xarxa
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
}
