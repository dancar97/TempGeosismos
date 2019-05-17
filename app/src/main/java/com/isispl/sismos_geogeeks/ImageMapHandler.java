package com.isispl.sismos_geogeeks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Random;

public class ImageMapHandler extends Activity {
    String newString;
    ImageView imgmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_imagen_mapa);

        imgmap = (ImageView)findViewById(R.id.ViewMapa);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
                Random rnd = new Random();

                imgmap.setColorFilter(rnd.nextInt(256));
                new DownloadImage(imgmap).execute(newString);



            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }


    }
}

