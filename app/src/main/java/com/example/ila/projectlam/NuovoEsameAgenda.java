package com.example.ila.projectlam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;

import db.DbManager;

/**
 * Created by Ila on 11/12/2014.
 */
public class NuovoEsameAgenda extends Activity{

    private DbManager db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovoesamefuturo);

        db = new DbManager(this);

        Button btnNewExam = (Button) findViewById(R.id.salva);
        btnNewExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    salva();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void salva() throws ParseException {
        EditText nome=(EditText) findViewById(R.id.insnomeesame);
        EditText crediti=(EditText) findViewById(R.id.inscrediti);
        EditText data=(EditText) findViewById(R.id.insdata);

        if (nome.getEditableText().toString().isEmpty() || crediti.getEditableText().toString().isEmpty()|| data.getEditableText().toString().isEmpty()) {
            final AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
            miaAlert.setMessage("Tutti i campi sono obbligatori.");
            miaAlert.setCancelable(false);
            miaAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            miaAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent libretto = new Intent(NuovoEsameAgenda.this, Agenda.class);
                    startActivity(libretto);
                }
            });
            AlertDialog alert = miaAlert.create();
            alert.show();
        }else {
            int c = Integer.parseInt(crediti.getEditableText().toString());
            String d = data.getEditableText().toString();
            if ( c > 20 ) {
                final AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
                miaAlert.setMessage("Crediti non validi: inserire un numero di crediti adeguato.");
                miaAlert.setCancelable(false);
                miaAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                miaAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent libretto = new Intent(NuovoEsameAgenda.this, Agenda.class);
                        startActivity(libretto);
                    }
                });
                AlertDialog alert = miaAlert.create();
                alert.show();
            } else if ( db.controlloData(d) <= 0 ) {
                final AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
                miaAlert.setMessage("Data non valida: inserire una data maggiore a quella odierna.");
                miaAlert.setCancelable(false);
                miaAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                miaAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent libretto = new Intent(NuovoEsameAgenda.this, Agenda.class);
                        startActivity(libretto);
                    }
                });
                AlertDialog alert = miaAlert.create();
                alert.show();
            } else {
                db.saveEsameFuturo(nome.getEditableText().toString(), Integer.parseInt(crediti.getEditableText().toString()), data.getEditableText().toString());
                Intent newExam = new Intent(NuovoEsameAgenda.this, Agenda.class);
                startActivity(newExam);
                finish();
            }
        }
    }
}
