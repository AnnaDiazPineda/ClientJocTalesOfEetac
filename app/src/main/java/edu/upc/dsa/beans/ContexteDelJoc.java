package edu.upc.dsa.beans;

import edu.upc.dsa.beans.Dialogador;

/**
 * Created by Marta on 11/1/2018.
 */

public class ContexteDelJoc {
    public static Dialogador dialogador = null;
    public static Dialogador getDialogador(){ return dialogador;}
    public static void setDialogador(Dialogador dig){dialogador = dig;}

    public static void loadNextMapa() {
        dialogador.loadNextMapa();
    }

    public static void levelUpPersonatge() {
    }
}
