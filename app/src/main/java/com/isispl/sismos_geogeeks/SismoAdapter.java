package com.isispl.sismos_geogeeks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.isispl.sismos_geogeeks.model.Sismo;

import java.util.List;


public class SismoAdapter extends ArrayAdapter<Sismo> {

    private Context context;
    private List<Sismo> sismoList;

    public SismoAdapter(Context context, int resource, List<Sismo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.sismoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row_item, parent, false);

        // Display Sismo name
        Sismo sismo = sismoList.get(position);

        TextView tv_item = (TextView)view.findViewById(R.id.row_item_txt1);
        TextView tv_item1 = (TextView)view.findViewById(R.id.row_item_txt2);
        TextView tv_item2 = (TextView)view.findViewById(R.id.row_item_txt3);
        TextView tv_item3 = (TextView)view.findViewById(R.id.row_item_txt4);
        TextView tv_item4 = (TextView)view.findViewById(R.id.row_item_txt5);

        tv_item.setText("Fecha: " + sismo.getFecha());
        tv_item1.setText("Magnitud: " + sismo.getMagnitud());
        tv_item2.setText("Profundidad: "+ sismo.getProfundidad());
        tv_item3.setText("Ubicación: Latitud:" + sismo.getLatitud()+" Longitud" + sismo.getLongitud());
        tv_item4.setText("Descripción: "+ sismo.getDescripcion());
        //Display image
        //ImageView img = (ImageView)view.findViewById(R.id.row_item_img);
        //img.setImageBitmap(sismo.getBitmap());

        return view;

    }



}
