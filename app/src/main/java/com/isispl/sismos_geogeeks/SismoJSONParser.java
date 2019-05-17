package com.isispl.sismos_geogeeks;

import com.isispl.sismos_geogeeks.model.Sismo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SismoJSONParser {
    public static List<Sismo> parseFeed(String content){
        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Sismo> sismoList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Sismo sismo = new Sismo();

                sismo.setMagnitud(jsonObject.getString("mag"));
                sismo.setProfundidad(jsonObject.getString("depth"));
                sismo.setLatitud(jsonObject.getString("latitude1"));
                sismo.setLongitud(jsonObject.getString("longitude1"));
                sismo.setDescripcion(jsonObject.getString("description"));
                sismo.setFecha(jsonObject.getString("time_UTC"));
                sismo.setMapa(jsonObject.getString("mapURL"));

                sismoList.add(sismo);
            }
            return sismoList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

