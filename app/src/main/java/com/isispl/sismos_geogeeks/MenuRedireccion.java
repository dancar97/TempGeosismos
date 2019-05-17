package com.isispl.sismos_geogeeks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuRedireccion extends Activity {
    Button botonMapa;
    Button botonLista;
    ImageView logo1;
    ImageView logo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_menu_principal);

        botonMapa = (Button) findViewById(R.id.button);
        botonLista = (Button) findViewById(R.id.button2);



        logo1=(ImageView)findViewById(R.id.imageView2);
        Drawable myDrawable = getResources().getDrawable(R.drawable.serv);
        logo1.setImageDrawable(myDrawable);
        logo2=(ImageView)findViewById(R.id.imageView3);
        Drawable myDrawable1 = getResources().getDrawable(R.drawable.geeks2);
        logo2.setImageDrawable(myDrawable1);

        botonMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MenuRedireccion.this, MapaArcgis.class);


                startActivity(i);
            }
        });

        botonLista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MenuRedireccion.this, MainActivity.class);


                startActivity(i);
            }
        });


    }
}

