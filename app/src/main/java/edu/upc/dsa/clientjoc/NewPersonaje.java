package edu.upc.dsa.clientjoc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import edu.upc.dsa.beans.Answer;
import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Personatge;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;
import edu.upc.dsa.clientjoc.inputOutput.ApiService;
import edu.upc.dsa.clientjoc.inputOutput.Registre;
import edu.upc.dsa.clientjoc.inputOutput.Response.Login;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marta on 4/1/2018.
 */

public class NewPersonaje extends AppCompatActivity {
    public ImageView imageType1view;
    public ImageView imageType2view;
    public ImageView imageType3view;
    public ImageView imageType4view;
    public TextView nameText;
    private Button binitGamebtn;
    public Personatge created;
    public String type;
    private String value;
    private Jugador jugador;
    private String name;
    private ApiService mRestAdapter;
    final Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpersonaje);
        Intent intent = getIntent();
        mRestAdapter =  ApiAdapter.getApiService();
        ObjectMapper mapper = new ObjectMapper();

            jugador = SingletonDades.getInstancia().getJugador();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text ="nom:"+jugador.getNom()+" id:"+jugador.getId()+"";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        //TODO:AFEGIR QUAN BORREM JUGADOR BORREM TOTS ELS PERSONATGES, OBJECTES
        associatedViews();
        created = new Personatge();
        imageType1view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type ="1";
                Toast toast = Toast.makeText(getApplicationContext(), "tipo1", Toast.LENGTH_SHORT);
                toast.show();


         }});
        imageType2view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type ="2";
                Toast toast = Toast.makeText(getApplicationContext(), type+""+name, Toast.LENGTH_SHORT);
                toast.show();
            }});
        imageType3view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type ="3";;
                Toast toast = Toast.makeText(getApplicationContext(), type+""+name, Toast.LENGTH_SHORT);
                toast.show();
            }});
        imageType4view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                type ="4";;
                Toast toast = Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT);
                toast.show();
            }});
        name = nameText.getText().toString();// hauriam d'afegir errors posibles al recollir el nom ANNA
        binitGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Personatge> loginCall = ApiAdapter.getApiService().newPersonaje(name,type,""+jugador.getId());
                try {
                    loginCall.enqueue(new Callback<Personatge>() {
                        @Override
                        public void onResponse(Call<Personatge> call, Response<Personatge> response) {
                        Personatge p = response.body();
                        int code = response.code();
                        switch(code){
                            case 204:
                            {}
                            break;
                            case 200:
                            {
                                if(p.getId()==-1){Toast toast = Toast.makeText(getApplicationContext(), "superas el número màxim de personatges", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                else{
                                    jugador.getPersonatges().add(p);
                                    created = p;
                                    message();
                                }
                            }break;
                        }

                        }

                        @Override
                        public void onFailure(Call<Personatge> call, Throwable t) {

                        }


                    });}
            catch(Exception e){
                    e.printStackTrace();
            }};});



// Create the AlertDialog

    }








    public void associatedViews(){
        imageType1view = (ImageView)findViewById(R.id.imageViewtype1);
        imageType2view = (ImageView)findViewById(R.id.imageViewType2);
        imageType3view = (ImageView)findViewById(R.id.imageViewType3);
        imageType4view = (ImageView)findViewById(R.id.imageViewType4);
        nameText = (TextView)findViewById(R.id.textView13);
        binitGamebtn = (Button)findViewById(R.id.button4);


    }
    private boolean isNameValid(String name) {
        return name.length() > 4;
    }
    private void startTheGame(){
        Call<String> createCall = ApiAdapter.getApiService().getNewMapa(created,jugador.getId());
        createCall.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    Intent intentMapa = new Intent(NewPersonaje.this, MapaActivity.class);
                    intentMapa.putExtra("mapa",response.body());


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void message(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Inici de partida");
        alertDialogBuilder
                .setMessage("Click yes to start the game!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    startTheGame();
                        //enviem al servidor el personatge rebut

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}

