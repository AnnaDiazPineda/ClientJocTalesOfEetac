package edu.upc.dsa.MiniJoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import edu.upc.dsa.clientjoc.R;

public class MiniJoc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mini_joc);

        Button btnEsquerra = (Button) findViewById(R.id.btnEsquerra);
        btnEsquerra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiniJocView.mourePersonatge(-1);

            }
        });

        Button btnDreta = (Button) findViewById(R.id.btnDreta);
        btnDreta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiniJocView.mourePersonatge(1);
            }
        });


    }
}
