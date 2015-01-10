package com.example.ila.projectlam;

/**
 * Created by Ila on 20/11/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Info extends Activity {

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
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
                //Esammi futuri
                Intent openEsamiFuturi = new Intent (Info.this, Agenda.class);
                startActivity(openEsamiFuturi);
                break;
            case R.id.menu2:
                //Esami passati
                Intent openLibretto = new Intent (Info.this, Libretto.class);
                startActivity(openLibretto);
                break;
            case R.id.menu3:
                //Statistiche
                Intent openStatistiche = new Intent (Info.this, Statistiche.class);
                startActivity(openStatistiche);
                break;
            case R.id.menu4:
                //Info
                break;
        }
        return false;
    }

}
