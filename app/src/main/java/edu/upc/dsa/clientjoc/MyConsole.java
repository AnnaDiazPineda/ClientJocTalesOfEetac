package edu.upc.dsa.clientjoc;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Marta on 12/1/2018.
 */

public class MyConsole {
    // cada cop que avança un tic agafem una nova paraula i l'afegim al text box anomenat consola nostre
    //controlar que ens salti de linea quan tingui massas caracters
    //rebrem contexte de l'activitat
    int contadorLineas = 0;
    int i = 0;
    String paraulaActual="";
    public int getContadorLineas() {
        return contadorLineas;
    }
    public void setContadorLineas(int contadorLineas) {
        this.contadorLineas = contadorLineas;
    }
    public void addMessage(final String message){
       final String[]parts = message.split(" ");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                doStuff(parts,message.length());
                        }
        },0,3);
        if(i==message.length()){
            timer.cancel();
            //finalitza agregacio nova frase
        }


    }
    //exemple: [Hola,Marta] size = 2
    public void doStuff(String[] mensaje,int leng){
        while(i<leng) {
            paraulaActual = mensaje[i];
            //afegir paraula al texbox
            i++;
        }
}}
