package edu.upc.dsa.beans;

/**
 * Created by Marta on 11/1/2018.
 */

public interface Dialogador {

    public void globus(String missatge);
    public boolean siNoQuestion(String missatge, String trueStr, String falseStr, Decisio decision);

    public void loadNextMapa();
}
