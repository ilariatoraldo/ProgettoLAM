package com.example.ila.projectlam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import db.DbManager;


public class MainActivity extends Activity {

    DbManager db = new DbManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TaskAsincrono(this).execute();
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
                Intent openEsamiFuturi = new Intent (MainActivity.this, Agenda.class);
                startActivity(openEsamiFuturi);
                break;
            case R.id.menu2:
                //Esami passati
                Intent openLibretto = new Intent (MainActivity.this, Libretto.class);
                startActivity(openLibretto);
                break;
            case R.id.menu3:
                //Statistiche
                Intent openStatistiche = new Intent (MainActivity.this, Statistiche.class);
                startActivity(openStatistiche);
                break;
            case R.id.menu5:
                //Statistiche
                Intent openGeo = new Intent (MainActivity.this, Geo.class);
                startActivity(openGeo);
                break;
            case R.id.menu4:
                //Info
                Intent openInfo = new Intent (MainActivity.this, Info.class);
                startActivity(openInfo);
                break;
        }
        return false;
    }
}
