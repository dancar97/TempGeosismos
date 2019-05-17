package com.isispl.sismos_geogeeks;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.util.ListenableList;
import com.isispl.sismos_geogeeks.model.Sismo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class MapaArcgis extends AppCompatActivity {

    private MapView mMapView;
    private GraphicsOverlay grOverlay;

    public List<String> longitudes;
    private List<String> latitudes;
    private List<String> profundidades;
    private List<String> fechas;
    private List<String> magnitudes;

    private TextView textoMap;

    Map<String,String> map =  new HashMap<String,String>();

    Point point;
    SimpleMarkerSymbol pointSymbol;

    Graphic pointGraphic;

    public List<MyBgTask1> tasks;
    public List<Sismo> PointList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mapa);
        tasks = new ArrayList<>();
        longitudes = new ArrayList<>();
        latitudes = new ArrayList<>();
        profundidades = new ArrayList<>();
        fechas = new ArrayList<>();
        magnitudes = new ArrayList<>();
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud9088059687,none,HC5X0H4AH4YDXH46C082");

        textoMap = (TextView) findViewById(R.id.TextoMapa);
        mMapView = (MapView) findViewById(R.id.mapView);
        ArcGISMap mMap = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 10.2333300, -74.0333300, 2);
        mMapView.setMap(mMap);
        requestData("https://srvags.sgc.gov.co/VolcanesSGCJson/Volcanes/sismos/events.json");


        MapViewTouchListener mMapViewTouchListener = new MapViewTouchListener(this, mMapView);
        mMapView.setOnTouchListener(mMapViewTouchListener);

    }


    private void requestData(String uri) {
        MyBgTask1 task = new MyBgTask1();
        task.execute(uri);
    }


    public void updateDisplay(){



        for (int i=0; i<19; i++){
            longitudes.add(PointList.get(i).getLongitud());

            latitudes.add(PointList.get(i).getLatitud());
            magnitudes.add(PointList.get(i).getMagnitud());
            profundidades.add(PointList.get(i).getProfundidad());
            fechas.add(PointList.get(i).getFecha());
        }

       // SismoAdapter adapter = new SismoAdapter(this, R.layout.row_item, PointList);

        addGraphicsOverlay(18,longitudes,latitudes,magnitudes,profundidades,fechas);




        //listaClick.setAdapter(adapter);


    }


    protected boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }

    private void addGraphicsOverlay( int counter, List<String> longs, List<String> lati, List<String> magnis, List<String> Prof, List<String> fech) {
        //        // create the polygon
        grOverlay = new GraphicsOverlay();
        //Log.d("DDDDDDDDDDDDDDDDDDD", PointList.get(0).getFecha());
        for (int j = 0; j <= counter; j++){
           point = new Point(Double.parseDouble(longs.get(j)), Double.parseDouble(lati.get(j)), SpatialReferences.getWgs84());
           int color;
           if(Double.parseDouble(magnis.get(j))<4) {
                color = Color.GREEN;
            }else if(Double.parseDouble(magnis.get(j))>7){
               color = Color.YELLOW;
           }else{
               color = Color.RED;
           }

            pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, color, (float) ((4*Double.parseDouble(magnis.get(j)))+10.0f));

            pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
             pointGraphic = new Graphic(point, pointSymbol);

            Log.e("ERASDASDA",longs.get(j));

            ListenableList<Graphic> graphics = grOverlay.getGraphics();


            graphics.add(pointGraphic);


            map.put(String.valueOf(graphics.get(j)), "Fecha: "+ fechas.get(j)+ "\n" + "Movimiento sismico con magnitud: " + Double.parseDouble(magnis.get(j))+"\n" +
                    "profundidad de " + profundidades.get(j));



        }









        mMapView.getGraphicsOverlays().add(grOverlay);

    }

    /**
     * Override default gestures of the MapView
     */
    class MapViewTouchListener extends DefaultMapViewOnTouchListener {

        /**
         * Constructs a DefaultMapViewOnTouchListener with the specified Context and MapView.
         *
         * @param context the context from which this is being created
         * @param mapView the MapView with which to interact
         */
        public MapViewTouchListener(Context context, MapView mapView){
            super(context, mapView);
        }

        /**
         * Override the onSingleTapConfirmed gesture to handle tapping on the MapView
         * and detected if the Graphic was selected.
         * @param e the motion event
         * @return true if the listener has consumed the event; false otherwise
         */


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            android.graphics.Point screenPoint = new android.graphics.Point((int)e.getX(), (int)e.getY());
            final ListenableFuture<IdentifyGraphicsOverlayResult> identifyGraphic = mMapView.identifyGraphicsOverlayAsync(grOverlay, screenPoint, 10.0, false, 2);

            identifyGraphic.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        IdentifyGraphicsOverlayResult grOverlayResult = identifyGraphic.get();

                        List<Graphic> graphic = grOverlayResult.getGraphics();

                        int identifyResultSize = graphic.size();
                        if(!graphic.isEmpty()){
                            // show a toast message if graphic was returned
                           // Toast.makeText(getApplicationContext(),   map.get(String.valueOf(graphic.get(0))) , Toast.LENGTH_SHORT).show();

                            textoMap.setText(map.get(String.valueOf(graphic.get(0))));


                        }
                    }catch(InterruptedException | ExecutionException ie){
                        ie.printStackTrace();
                    }

                }
            });

            return super.onSingleTapConfirmed(e);
        }

    }


    private class MyBgTask1 extends AsyncTask<String, String, List<Sismo>> {

        @Override
        protected void onPreExecute() {
            //updateDisplay();
            if (tasks.size() == 0) {                        // to check whether tasks array empty or not.
                // if empty then start progressbar on execute

            }
            tasks.add(this);
        }

        @Override
        protected List<Sismo> doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            PointList = SismoJSONParser.parseFeed(content);

            for(Sismo sismo : PointList){
                try{
                   /*String imgURL = PHOTO_BASE_URL + sismo.getPhoto();
                    InputStream in = (InputStream)new URL(imgURL).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    sismo.setBitmap(bitmap);
                    in.close();*/
                }
                catch (Exception e){
                    e.printStackTrace();

                }
            }
            return PointList;

        }

        @Override
        protected void onPostExecute(List<Sismo> result) {

            // PointList = SismoJSONParser.parseFeed(result);

            updateDisplay();
            tasks.remove(this);             // to remove all task from the tasks array and when it get empty, progress bar should become invisiblee
            if (tasks.size() == 0) {
                //pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }
    }
}















        /*extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mapa);

        // get the reference to the map view
        mMapView = (MapView)findViewById(R.id.mapView);

        // create a map with the dark gray canvas basemap
        ArcGISMap map = new ArcGISMap(Basemap.Type.DARK_GRAY_CANVAS_VECTOR, 4, -72.0000000, 4);

        // set the map to the map view
        mMapView.setMap(map);
        changeSourceToURL();

        mMapView.getOnTouchListener();
        //requestReadPermission();
    }


    private void display(KmlLayer kmlLayer) {
        // clear the existing layers from the map
        mMapView.getMap().getOperationalLayers().clear();

        // add the KML layer to the map
        mMapView.getMap().getOperationalLayers().add(kmlLayer);
    }


    private void changeSourceToURL() {
        // create a kml data set from a URL
        KmlDataset kmlDataset = new KmlDataset("https://sismos.sgc.gov.co/events/SGC2019jlyal/SGC2019jlyal.kml");

        // a KML layer created from a remote KML file
        KmlLayer kmlLayer = new KmlLayer(kmlDataset);

        display(kmlLayer);


Log.e("11111111111",kmlLayer.getDescription());
        Log.e("w22222222",kmlLayer.getName());

exploreKml(kmlDataset.getRootNodes());

      //  Log.e("4444444444444444",kmlLayer.getDataset().getRootNodes().get(0).getBalloonContent());


        // report errors if failed to load
        kmlDataset.addDoneLoadingListener(() -> {
            if (kmlDataset.getLoadStatus() != LoadStatus.LOADED) {
                String error = "Failed to load kml layer from URL: " + kmlDataset.getLoadError().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                Log.e(TAG, error);
            }
        });
    }


   private void changeSourceToPortalItem() {
        // create a portal to ArcGIS Online

    }



    private void changeSourceToFileExternalStorage() {
        // a data set made from data in external storage

    }




    private void exploreKml(List<KmlNode> nodes) {
        for (KmlNode node : nodes){
            if (node instanceof KmlScreenOverlay) {
                node.setVisible(false);
            }
            exploreKml(childNodes(node));
        }
    }

    // returns all the child nodes of this node
    private List<KmlNode> childNodes(KmlNode node) {
        List<KmlNode> children = new ArrayList<>();
        if (node instanceof KmlContainer) {
            children.addAll(((KmlContainer) node).getChildNodes());
        }
        if (node instanceof KmlNetworkLink) {
            children.addAll(((KmlNetworkLink) node).getChildNodes());
        }
        Log.e("WARNINGGGGGGGGA", children.toString());
        Log.e("WARNINGGGGGGGGA", children.get(0).toString());
        return children;
    }

    @Override
    protected void onPause() {
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        mMapView.dispose();
        super.onDestroy();
    }
}
*/