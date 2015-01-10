package com.example.ila.projectlam;

/**
 * Created by Ila on 27/12/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import db.DbManager;

import static android.graphics.Color.rgb;

public class Grafico extends Activity {

    private DbManager db = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafico);
        db = new DbManager(this);

        Button btnLineGraph = (Button) findViewById(R.id.lineGraph);
        btnLineGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                lineGraph();
            }
        });

        Button btnBarGraph = (Button) findViewById(R.id.BarGraph);
        btnBarGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                barGraph();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {
            case R.id.menu1:
                //Esami futuri
                Intent openEsamiFuturi = new Intent (Grafico.this, Agenda.class);
                startActivity(openEsamiFuturi);
                break;
            case R.id.menu2:
                //Esami passati
                Intent openLibretto = new Intent (Grafico.this, Libretto.class);
                startActivity(openLibretto);
                break;
            case R.id.menu3:
                //Statistiche
                Intent openStatistiche = new Intent (Grafico.this, Statistiche.class);
                startActivity(openStatistiche);
                break;
            case R.id.menu4:
                //Info
                Intent openInfo = new Intent (Grafico.this, Info.class);
                startActivity(openInfo);
                break;
        }
        return false;
    }

    public void lineGraph(){
        // Crediti y
        Cursor cursore = db.mediaPonderata(); //[Voto,Crediti]
        int[] arraycrediti = new int[cursore.getCount()];
        if (cursore.moveToFirst()) {
            for (int i = 0; i < cursore.getCount(); ) {
                arraycrediti[i++] = cursore.getInt(1);
                cursore.moveToNext();
            }
        }
        // Voti x
        int[] arrayvoti = new int[cursore.getCount()];
        if (cursore.moveToFirst()) {
            for (int i = 0; i < cursore.getCount(); ) {
                arrayvoti[i++] = cursore.getInt(0);
                cursore.moveToNext();
            }
        }
        // Numero idoneità
        Cursor num = db.voti();
        int numeroidoneita = 0;
        while(num.moveToNext()){
            if(num.getString(1).equals("1"))
                numeroidoneita++;
        }
        double[] rapporto = new double[cursore.getCount()];
        for(int i=0; i< cursore.getCount(); i++){
            rapporto[i]= (double) arrayvoti[i]/ (double) arraycrediti[i];
        }
        int[] numeri = new int[cursore.getCount()];
        int nonloso = 0;
        for (int i = 0; i < rapporto.length; i++) {
            if(!(rapporto[i]==0))
                numeri[nonloso]=nonloso;
        }
        TimeSeries series = new TimeSeries("Rapporto");
        for (int i = 0; i < rapporto.length; i++) {
            if(!(rapporto[i]==0))
                series.add(numeri[i], rapporto[i]);
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
        XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setLabelsTextSize(15.0f);
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(numeri.length - numeroidoneita - 1);
        mRenderer.setYAxisMin(0);
        mRenderer.setYAxisMax(10);
        // Customization time for line 1!
        renderer.setColor(rgb(150,0,24));
        renderer.setPointStyle(PointStyle.DIAMOND);
        renderer.setFillPoints(true);
        Intent intent = ChartFactory.getLineChartIntent(this, dataset, mRenderer, "Laureando");
        startActivity(intent);
    }

    public void barGraph(){
        // Crediti
        Cursor cursore = db.mediaPonderata(); //[Voto,Crediti]
        int[] arraycrediti = new int[cursore.getCount()];
        if (cursore.moveToFirst()) {
            for (int i = 0; i < cursore.getCount(); ) {
                arraycrediti[i++] = cursore.getInt(1);
                cursore.moveToNext();
            }
        }
        // Voti
        int[] arrayvoti = new int[cursore.getCount()];
        if (cursore.moveToFirst()) {
            for (int i = 0; i < cursore.getCount(); ) {
                arrayvoti[i++] = cursore.getInt(0);
                cursore.moveToNext();
            }
        }
        // Numero idoneità
        Cursor num = db.voti();
        int numeroidoneita = 0;
        while(num.moveToNext()){
            if(num.getString(1).equals("1"))
                numeroidoneita++;
        }
        CategorySeries series = new CategorySeries("Crediti");
        CategorySeries series2 = new CategorySeries("Voti");
        for (int i = 0; i < arrayvoti.length; i++) {
            if (!(arrayvoti[i] == 0)){
                series2.add("Bar " + (i + 1), arrayvoti[i]);
                series.add("Bar " + (i+1), arraycrediti[i]);
            }
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());
        dataset.addSeries(series2.toXYSeries());
        // This is how the "Graph" itself will look like
        XYMultipleSeriesRenderer grafico = new XYMultipleSeriesRenderer();
        grafico.setAxesColor(Color.LTGRAY);
        grafico.setLabelsColor(rgb(150,0,24));
        grafico.setLabelsTextSize(15.0f);
        grafico.setXAxisMin(0.5);
        grafico.setXAxisMax(arrayvoti.length - numeroidoneita + 0.5);
        grafico.setYAxisMin(0);
        //grafico.setZoomButtonsVisible(true);
        // Customize bar 1
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 0.5);
        grafico.addSeriesRenderer(renderer);
        // Customize bar 2
        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(rgb(150,0,24));
        renderer.setColor(Color.LTGRAY);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 0.5);
        grafico.addSeriesRenderer(renderer2);
        Intent intent = ChartFactory.getBarChartIntent(this, dataset, grafico, BarChart.Type.DEFAULT);
        startActivity(intent);
    }
}