package com.example.ila.projectlam;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import db.DbManager;

/**
 * Created by Ila on 08/12/2014.
 */
public class Statistiche extends Activity {

    private DbManager db = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistiche);

        Button btnNewExam = (Button) findViewById(R.id.bottonegrafico);
        btnNewExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent grafico = new Intent(Statistiche.this, Grafico.class);
                startActivity(grafico);
            }
        });

        db = new DbManager(this);

        //Numero esami dati
        Cursor crs = db.query();
        int numesami = crs.getCount();
        TextView textnumeroesami=(TextView) findViewById(R.id.totaleEsami);
        String totesami = String.valueOf(numesami).toString();
        textnumeroesami.setText(totesami);

        //Totale crediti
        Cursor c = db.crediti();
        TextView textcrediti = (TextView) findViewById(R.id.totaleCrediti);
        int cfutot = 0;
        while(c.moveToNext()){
            cfutot += Integer.parseInt(c.getString(0));
        }
        String totcrediti = String.valueOf(cfutot).toString();
        textcrediti.setText(totcrediti);

        //Media aritmetica
        Cursor m = db.voti();
        TextView textmediaar = (TextView) findViewById(R.id.mediaAritmetica);
        int votitot = 0;
        while(m.moveToNext()){
            if(!(m.getString(1).equals("1")))
                votitot += Integer.parseInt(m.getString(0));
        }
        Cursor num = db.voti();
        int numeroidoneita = 0;
        while(num.moveToNext()){
            if(num.getString(1).equals("1"))
                numeroidoneita++;
        }
        int numeroesaminonidoneita = numesami - numeroidoneita;
        double ma = (double) votitot/numeroesaminonidoneita;
        double mediaaritmetica = (Math.floor(ma * 100.0) / 100.0); //Arrotonda a due cifre dopo la virgola
        String mediaaritm = String.valueOf(mediaaritmetica).toString();
        textmediaar.setText(mediaaritm);

        //Media ponderata
        Cursor mp = db.mediaPonderata();
        TextView textmediapond = (TextView) findViewById(R.id.mediaPonderata);
        int votocredito = 0;
        while(mp.moveToNext()){
            if(!(mp.getString(0).equals(""))){
                votocredito += (Integer.parseInt(mp.getString(0)) * Integer.parseInt(mp.getString(1)));
            }
        }
        Cursor cr = db.crediti();
        int creditiid = 0;
        while(cr.moveToNext()){
            if(cr.getString(1).equals("1"))
                creditiid += cr.getInt(0);
        }
        int numerocreditinonidoneita = cfutot - creditiid;
        double mpo = (double) votocredito/numerocreditinonidoneita;
        double mediaponderata = (Math.floor(mpo * 100.0) / 100.0); //Arrotonda a due cifre dopo la virgola
        String mediapond = String.valueOf(mediaponderata).toString();
        textmediapond.setText(mediapond);

        //Prospettiva laurea
        TextView textprospettiva = (TextView) findViewById(R.id.prospettivaLaurea);
        double pl = (double) (mediaponderata*110)/30;
        double prospettivalaurea = (Math.floor(pl * 100.0) / 100.0); //Arrotonda a due cifre dopo la virgola
        String prosplaurea = String.valueOf(prospettivalaurea).toString();
        textprospettiva.setText(prosplaurea);

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
                Intent openEsamiFuturi = new Intent (Statistiche.this, Agenda.class);
                startActivity(openEsamiFuturi);
                break;
            case R.id.menu2:
                //Esami passati
                Intent openLibretto = new Intent (Statistiche.this, Libretto.class);
                startActivity(openLibretto);
                break;
            case R.id.menu3:
                //Statistiche
                break;
            case R.id.menu4:
                //Info
                Intent openInfo = new Intent (Statistiche.this, Info.class);
                startActivity(openInfo);
                break;
        }
        return false;
    }

}
