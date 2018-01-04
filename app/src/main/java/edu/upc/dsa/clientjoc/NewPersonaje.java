package edu.upc.dsa.clientjoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.upc.dsa.beans.Personatge;

/**
 * Created by Marta on 4/1/2018.
 */

public class NewPersonaje extends AppCompatActivity {
    public ImageView imageType1view;
    public ImageView imageType2view;
    public ImageView imageType3view;
    public ImageView imageType4view;
    public TextView nameText;
    public Personatge created;
    public void associatedViews(){
        imageType1view = (ImageView)findViewById(R.id.imageViewtype1);
        imageType2view = (ImageView)findViewById(R.id.imageViewType2);
        imageType3view = (ImageView)findViewById(R.id.imageViewType3);
        imageType4view = (ImageView)findViewById(R.id.imageViewType4);
        nameText = (TextView)findViewById(R.id.textView13);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpersonaje);
        associatedViews();
        created = new Personatge();
        imageType1view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                created.setTipo(1);

         }});
        imageType2view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                created.setTipo(2);
            }});
        imageType3view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                created.setTipo(3);
            }});
        imageType4view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                created.setTipo(4);
            }});

    }
    private boolean isNameValid(String name) {
        return name.length() > 4;
    }
    private void attemptCreating(){

    }
}
