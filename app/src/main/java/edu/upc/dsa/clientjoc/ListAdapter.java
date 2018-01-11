package edu.upc.dsa.clientjoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.beans.Partida;

/**
 * Created by Marta on 10/1/2018.
 */

public class ListAdapter extends ArrayAdapter<Partida> {
    private final Activity context;
    private final List<Partida> listItems;
    public ListAdapter(Activity context, List<Partida> listItems) {
        super(context, R.layout.row, listItems);
        this.context = context;
        this.listItems = listItems;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row, null, true);
        TextView idJugador = (TextView) rowView.findViewById(R.id.textViewidJugador);
        TextView puntos = (TextView) rowView.findViewById(R.id.textViewPuntos);
        TextView inici = (TextView) rowView.findViewById(R.id.textViewInici);
        TextView pos = (TextView)rowView.findViewById(R.id.textViewPosition);
        pos.setText(""+pos);
        idJugador.setText(""+listItems.get(position).getIdJugador());
        inici.setText(listItems.get(position).getPuntos());
        puntos.setText(""+listItems.get(position).getInici());
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("touched").show();
            }

        });

        return rowView;


    }

}

