package edu.upc.dsa.MiniJoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import edu.upc.dsa.clientjoc.R;

public class MiniJoc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mini_joc);
    }
}
