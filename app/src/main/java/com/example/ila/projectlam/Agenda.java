package com.example.ila.projectlam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;

import db.AgendaStrings;
import db.DbManager;
/**
 * Created by Ila on 11/12/2014.
 */

public class Agenda extends Activity {

    private DbManager db = null;
    private CursorAdapter adapter;
    private ListView listview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esamifuturi);
        db = new DbManager(this);
        listview = (ListView) findViewById(R.id.listView2);
        Cursor crs = db.queryAgenda();
        adapter = new CursorAdapter(this, crs, 0)
        {
            @Override
            public View newView(Context ctx, Cursor arg1, ViewGroup arg2)
            {
                View v=getLayoutInflater().inflate(R.layout.listactivity_row, null);
                return v;
            }
            @Override
            public void bindView(View v, Context arg1, Cursor crs)
            {
                String nomeesame =crs.getString(crs.getColumnIndex(AgendaStrings.KEY_NOME));
                String crediti = crs.getString(crs.getColumnIndex(AgendaStrings.KEY_CREDITI));
                String data = crs.getString(crs.getColumnIndex(AgendaStrings.KEY_DATA));

                TextView nome=(TextView) v.findViewById(R.id.txt_nome);
                nome.setText(nomeesame);
                TextView cr=(TextView) v.findViewById(R.id.txt_crediti);
                cr.setText(" (" + crediti + " cfu)");
                TextView d = (TextView) v.findViewById(R.id.txt_data);
                d.setText(data);

                Button imgbtn=(Button) v.findViewById(R.id.btn_delete);
                imgbtn.setOnClickListener(clickListener);
            }

            @Override
            public long getItemId(int position)
            {
                Cursor crs=adapter.getCursor();
                crs.moveToPosition(position);
                return crs.getLong(crs.getColumnIndex(AgendaStrings.KEY_ID));
            }
        };

        listview.setAdapter(adapter);

        Button btnNewExam = (Button) findViewById(R.id.aggiungiesamefuturo);
        btnNewExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent newExam = new Intent(Agenda.this, NuovoEsameAgenda.class);
                startActivity(newExam);
                finish();
            }
        });

    }

    private View.OnClickListener clickListener=new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int position=listview.getPositionForView(v);
            long id=adapter.getItemId(position);
            if (db.deleteEsameFuturo(id))
                adapter.changeCursor(db.queryAgenda());
        }
    };


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
                break;
            case R.id.menu2:
                //Esami passati
                Intent openLibretto = new Intent (Agenda.this, Libretto.class);
                startActivity(openLibretto);
                break;
            case R.id.menu3:
                //Statistiche
                Intent openStatistiche = new Intent (Agenda.this, Statistiche.class);
                startActivity(openStatistiche);
                break;
            case R.id.menu4:
                //Info
                Intent openInfo = new Intent (Agenda.this, Info.class);
                startActivity(openInfo);
                break;
        }
        return false;
    }



    public void pulisciDb() throws ParseException {
        Cursor esami = db.queryAgenda();
        String data = esami.getString(esami.getColumnIndex(AgendaStrings.KEY_DATA));
        if(db.controlloData(data) > 0){
            db.deleteEsameFuturo(Long.parseLong(esami.getString(esami.getColumnIndex(AgendaStrings.KEY_ID))));
        }
    }
}
