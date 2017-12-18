package edu.upc.dsa.clientjoc.inputOutput;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import edu.upc.dsa.beans.Producto;
import edu.upc.dsa.beans.Productes;
import edu.upc.dsa.clientjoc.R;

/**
 * Created by Marta on 18/12/2017.
 */

public class ProductosActivity extends AppCompatActivity {
    private ListView milista;
    private ApiService mRestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        mRestAdapter =  ApiAdapter.getApiService();
        Intent intent = getIntent();
        String value = intent.getStringExtra("milista"); //if it's a string you stored.
        ObjectMapper mapper = new ObjectMapper();
        Productes productes = new Productes();
        try {
            productes = mapper.readValue(value, Productes.class);
            ArrayAdapter<Producto> itemsAdapter = new ArrayAdapter<Producto>(this, android.R.layout.activity_list_item, productes.getMilista());
            ListView lv=(ListView)findViewById(R.id.lista);
            lv.setAdapter(itemsAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
