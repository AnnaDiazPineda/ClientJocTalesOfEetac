package edu.upc.dsa.clientjoc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import edu.upc.dsa.beans.Decisio;
import edu.upc.dsa.beans.Dialogador;
import edu.upc.dsa.clientjoc.Grafics.MapaView;

/**
 * Created by Marta on 11/1/2018.
 */

public class DialogadorImplAndroid implements Dialogador {
    MapaActivity context;
    public  MapaView view;
    Context appcontext;
    int i;
    String[]parts;

    public DialogadorImplAndroid(MapaActivity mapaActivity, Context applicationContext, MapaView viewM) {
        context = mapaActivity;
        this.appcontext = applicationContext;
        view = viewM;
    }

    public void globus(String missatge){
        i=0;
        String[]parts = missatge.split(" ");
        TextView txtView = (TextView) ((Activity)context).findViewById(R.id.textView16);
        if(txtView.getText().length()>60)
        {
            txtView.setText("");
            txtView.append(missatge);
        }
        else{
        txtView.append(missatge);}
    }


    @Override
    public boolean siNoQuestion(String missatge, String trueStr, String falseStr, final Decisio decision) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Responde:");
        alertDialogBuilder.setMessage(missatge).setCancelable(false)
                .setPositiveButton(trueStr,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                    decision.dotrue();
            }
        })
                .setNegativeButton(falseStr,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        decision.dofalse();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    @Override
    public void loadNextMapa() {
        context.crearNewMapaPerPersonatgeSellecionat();
    }

    @Override
    public void loadAndEmptyMap() {
        context.EstablirMapabuit();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void startPong() {
        view.startPong();
    }

    @Override
    public boolean isNoPong() {
        return !view.modepong;
    }

    @Override
    public void actualizeToDatabase() {
        context.actualizeToDatabase();

    }

}
