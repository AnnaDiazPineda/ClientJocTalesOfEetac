package edu.upc.dsa.clientjoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;
import edu.upc.dsa.clientjoc.inputOutput.Registre;
import edu.upc.dsa.clientjoc.inputOutput.Response.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class LoginActivity extends AppCompatActivity {

    // UI references.
    private ImageView mLogoView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextInputLayout mFloatLabelUserId;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button bRegistrarse;

    //retrofit
    private ApiService mRestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //servei rest, singleton
        mRestAdapter =  ApiAdapter.getApiService();

        mLogoView = (ImageView) findViewById(R.id.imageLogo);
        mProgressView = findViewById(R.id.login_progress);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailView.setText("martavivesluis@gmail.com");
        mPasswordView.setText("1234");

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);


        mLoginFormView = findViewById(R.id.login_form);

        mFloatLabelUserId = (TextInputLayout) findViewById(R.id.float_label_user_id);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password);


        //events del password i el clic al boto
        // Setup
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
               /* if (id == R.id.login_form || id == EditorInfo.IME_NULL) {
                    //atemposar resultat del online
                    if (!isOnline()) {
                        //showLoginError(getString(R.string.error_network));
                        toast("You are not online");
                        return false;
                    }
                    attemptLogin();
                    return true;
                }
                return false;*/
            return false;




            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();
            }
        });

        bRegistrarse = (Button) findViewById(R.id.bNouUsuari);
        bRegistrarse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistre = new Intent(LoginActivity.this, Registre.class);
                startActivity(intentRegistre);
            }
        });

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private void returnToInit(){
        showProgress(false);
        mEmailView.requestFocus();
    }

    private void attemptLogin() {


        // Reset errors. si hem posat quelcom malament
        mFloatLabelUserId.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            String mail = mEmailView.getText().toString();
            String pass = mPasswordView.getText().toString();
            Call<Jugador>  loginCall = ApiAdapter.getApiService().getLogin(mail,new Login(pass));
            loginCall.enqueue(new Callback<Jugador>() {
                @Override
                public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                    switch (response.code()) {
                        case 200:// tot correcte
                            showLoginError("login correcte");
                            Intent myIntent = new Intent(LoginActivity.this, DatosPersonales.class);
                            Jugador jug = response.body();
                            ObjectMapper mapper = new ObjectMapper();
                            try {
                                String jsonResult = mapper.writeValueAsString(jug);
                                myIntent.putExtra("jugador", jsonResult); //Optional parameters
                                showProgress(false);
                                LoginActivity.this.startActivity(myIntent);
                            }catch (Exception e){
                                showLoginError("no serializable");
                            }
                        //enviar jugador rebut nova activitat
                            break;
                        case 204://la contrassenya esta malament
                            showLoginError(getString(R.string.error_password));
                            Intent myIntent2 = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(myIntent2);
                            finish();

                            break;
                        case 500://el email no existeix
                            showLoginError(getString(R.string.error_user));
                            Intent myIntent3 = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(myIntent3);
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




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    //mostra el progreso i amaga el formulari
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);

    }

    private void showMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    //verificar connexió de xarxa
    private boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}

