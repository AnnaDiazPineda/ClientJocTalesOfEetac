package edu.upc.dsa.clientjoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import edu.upc.dsa.beans.Jugador;
import edu.upc.dsa.beans.Partida;
import edu.upc.dsa.clientjoc.inputOutput.ApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ranking extends AppCompatActivity {

    private Button btnPersonal;
    private Button btnJugar;
    private ListView miRankingList;
    private Jugador jugador = null;
    private String value;
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Intent intent = getIntent();
        miRankingList = (ListView) findViewById(R.id.ranking);
        ArrayList<Partida> miranking = getRankingFromServer();
        ListAdapter adapter = new ListAdapter(Ranking.this, miranking);
        miRankingList.setAdapter(adapter);
    }
    private ArrayList<Partida> getRankingFromServer() {
        ArrayList<Partida> miranking = new ArrayList<Partida>();
        Partida mipartida = new Partida(45, 200, "fefe");
        miranking.add(mipartida);
        return miranking;
    }
}
