package com.isispl.sismos_geogeeks.model;

import android.graphics.Bitmap;


public class Sismo {
    private int productId;
    private String Magnitud;
    private String Latitud;
    private String Longitud;
    private String Profundidad;
    private String Mapa;
    private String Fecha;
    private String Descripcion;


    private double price;
    private String photo;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getMagnitud() {
        return Magnitud;
    }

    public void setMagnitud(String Magnitud) {
        this.Magnitud = Magnitud;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String Latitud) {
        this.Latitud = Latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String Longitud) {
        this.Longitud = Longitud;
    }

    public String getProfundidad() {
        return Profundidad;
    }

    public void setProfundidad(String Profundidad) {
        this.Profundidad = Profundidad;
    }

    public String getMapa() {
        return Mapa;
    }

    public void setMapa(String Mapa) {
        this.Mapa = Mapa;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }



    public String getDescripcion() {
        return Descripcion;
    }


    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }




}
