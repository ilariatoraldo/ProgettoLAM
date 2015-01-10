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

import db.DbManager;
import db.LibrettoStrings;

/**
 * Created by Ila on 25/11/2014.
 */

public class Libretto extends Activity {

    private DbManager db = null;
    private CursorAdapter adapter;
    private ListView listview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libretto);

        db = new DbManager(this);
        listview = (ListView) findViewById(R.id.listView);
        Cursor crs = db.query();
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
                String nomeesame =crs.getString(crs.getColumnIndex(LibrettoStrings.FIELD_NOME));
                String crediti = crs.getString(crs.getColumnIndex(LibrettoStrings.FIELD_CREDITI));
                String data = crs.getString(crs.getColumnIndex(LibrettoStrings.FIELD_DATA));
                String voto = crs.getString(crs.getColumnIndex(LibrettoStrings.FIELD_VOTO));
                String lode = crs.getString(crs.getColumnIndex(LibrettoStrings.FIELD_LODE));

                TextView nome=(TextView) v.findViewById(R.id.txt_nome);
                nome.setText(nomeesame);
                TextView cr=(TextView) v.findViewById(R.id.txt_crediti);
                cr.setText(" (" + crediti + " cfu)");
                TextView d = (TextView) v.findViewById(R.id.txt_data);
                d.setText(data + " - ");
                TextView vo = (TextView) v.findViewById(R.id.txt_voto);
                String L = "";
                if(voto.equals(""))
                    voto = "Idoneità";
                else if (lode.equals("1"))
                    L = "L";
                vo.setText(voto + L);
                Button imgbtn=(Button) v.findViewById(R.id.btn_delete);
                imgbtn.setOnClickListener(clickListener);
            }

            @Override
            public long getItemId(int position)
            {
                Cursor crs=adapter.getCursor();
                crs.moveToPosition(position);
                return crs.getLong(crs.getColumnIndex(LibrettoStrings.FIELD_ID));
            }
        };

        listview.setAdapter(adapter);

        Button btnNewExam = (Button) findViewById(R.id.aggiungiesame);
        btnNewExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent newExam = new Intent(Libretto.this, NuovoEsameLibretto.class);
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
            if (db.deleteEsame(id))
                adapter.changeCursor(db.query());
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
                Intent openEsamiFuturi = new Intent (Libretto.this, Agenda.class);
                startActivity(openEsamiFuturi);
                break;
            case R.id.menu2:
                //Esami passati
                break;
            case R.id.menu3:
                //Statistiche
                Intent openStatistiche = new Intent (Libretto.this, Statistiche.class);
                startActivity(openStatistiche);
                break;
            case R.id.menu4:
                //Info
                Intent openInfo = new Intent (Libretto.this, Info.class);
                startActivity(openInfo);
                break;
        }
        return false;
    }
}