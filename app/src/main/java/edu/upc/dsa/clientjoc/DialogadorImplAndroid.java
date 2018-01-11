package edu.upc.dsa.clientjoc;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;

import edu.upc.dsa.beans.Decisio;
import edu.upc.dsa.beans.Dialogador;
/**
 * Created by Marta on 11/1/2018.
 */

public class DialogadorImplAndroid implements Dialogador {
    Context context;
    Context appcontext;
    public DialogadorImplAndroid(Context ctxt, Context appcontext){
        context = ctxt;
        this.appcontext = appcontext;
    }
    public void globus(String missatge){
        Toast toast = Toast.makeText(appcontext, missatge, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    @Override
    public boolean siNoQuestion(String missatge, String trueStr, String falseStr, final Decisio decision) {

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

}
