package dsa.eetac.upc.edu.clientjoc;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import dsa.eetac.upc.edu.clientjoc.Grafics.MapaView;

/**
 * Created by Marta on 12/12/2017.
 */

public class MapaActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new MapaView(this));
        //setContentView(R.layout.activity_main);
    }
}