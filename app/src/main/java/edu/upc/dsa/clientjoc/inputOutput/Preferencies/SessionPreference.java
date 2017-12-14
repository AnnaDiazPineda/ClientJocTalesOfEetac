package edu.upc.dsa.clientjoc.inputOutput.Preferencies;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import edu.upc.dsa.beans.Jugador;


/**
 * Created by anita on 04/12/2017.
 */

public class SessionPreference {

    public static final String PREF_JUGADOR_NAME = "PREF_JUGADOR_NAME";
    public static final String PREF_JUGADOR_MAIL = "PREF_JUGADOR_MAIL";
    public static final String PREF_JUGADOR_TOKEN = "PREF_JUGADOR_TOKEN";

    //instancia shared preference
    private final SharedPreferences mPrefs;

    //comproba si l'usuari esta loggejat o no
    private boolean mIsLoggedIn = false;

    private static SessionPreference INSTANCE;

    public static SessionPreference get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPreference(context);
        }
        return INSTANCE;
    }

    //inicialitzar les preferencies, mitjançant context que invoca el metode sharedprefe
    private SessionPreference(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREF_JUGADOR_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_JUGADOR_TOKEN, null));
    }
    //endevinar estat de l'usuari
    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveJugador(Jugador jugador) {
        if (jugador != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_JUGADOR_MAIL, jugador.getEmail());
            editor.putString(PREF_JUGADOR_NAME, jugador.getNom());
            editor.apply();

            mIsLoggedIn = true;
        }
    }
    //tancar sessió usuari
    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_JUGADOR_MAIL, null);
        editor.putString(PREF_JUGADOR_NAME, null);
        editor.putString(PREF_JUGADOR_TOKEN, null);

        editor.apply();
    }

}
