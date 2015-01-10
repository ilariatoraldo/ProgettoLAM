package com.example.ila.projectlam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.ParseException;

import db.DbManager;

/**
 * Created by Ila on 02/12/2014.
 */

public class NuovoEsameLibretto extends Activity {

    private DbManager db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovoesame);

        db = new DbManager(this);

        final CheckBox idon=(CheckBox) findViewById((R.id.idon));
        final CheckBox lod=(CheckBox) findViewById((R.id.lode));
        final EditText voto=(EditText) findViewById(R.id.insvoto);

        idon.setOnClickListener(new View.OnClickListener() {
            int conteggioclick=0;
            @Override
            public void onClick(View arg0) {
                if((conteggioclick % 2) == 0) {
                    lod.setEnabled(false);
                    voto.setEnabled(false);
                    voto.setText("");
                    conteggioclick++;
                }else{
                    lod.setEnabled(true);
                    voto.setEnabled(true);
                    conteggioclick++;
                }
            }
        });

        lod.setOnClickListener(new View.OnClickListener() {
            int conteggioclicklode=0;
            @Override
            public void onClick(View arg0) {
                if((conteggioclicklode % 2) == 0) {
                    idon.setEnabled(false);
                    voto.setText("30");
                    voto.setEnabled(false);
                    conteggioclicklode++;
                }else{
                    idon.setEnabled(true);
                    voto.setEnabled(true);
                    voto.setText("");
                    conteggioclicklode++;
                }
            }
        });

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
        EditText voto=(EditText) findViewById(R.id.insvoto);
        CheckBox idon=(CheckBox) findViewById((R.id.idon));
        CheckBox lod=(CheckBox) findViewById((R.id.lode));

        boolean idoneita;
        if(idon.isChecked()){
            idoneita = true;
        }else{
            idoneita = false;
        }
        boolean lode;
        if(lod.isChecked()){
            lode = true;
        }else{
            lode = false;
        }

        if (nome.getEditableText().toString().isEmpty() || crediti.getEditableText().toString().isEmpty()|| data.getEditableText().toString().isEmpty() || (voto.getEditableText().toString().isEmpty() && !(idon.isChecked()) )) {
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
                    Intent libretto = new Intent(NuovoEsameLibretto.this, Libretto.class);
                    startActivity(libretto);
                }
            });
            AlertDialog alert = miaAlert.create();
            alert.show();
        }else {
            int v = 0;
            if (voto.isEnabled()) {
                v = Integer.parseInt(voto.getEditableText().toString());
            }
            int c = Integer.parseInt(crediti.getEditableText().toString());
            String d = data.getEditableText().toString();
            if (voto.isEnabled() && (v < 18 || v > 30)) {
                final AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
                miaAlert.setMessage("Inserire un voto tra 18 e 30.");
                miaAlert.setCancelable(false);
                miaAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                miaAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent libretto = new Intent(NuovoEsameLibretto.this, Libretto.class);
                        startActivity(libretto);
                    }
                });
                AlertDialog alert = miaAlert.create();
                alert.show();
            } else if (c > 20) {
                final AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
                miaAlert.setMessage("Il numero di crediti è troppo alto.");
                miaAlert.setCancelable(false);
                miaAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                miaAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent libretto = new Intent(NuovoEsameLibretto.this, Libretto.class);
                        startActivity(libretto);
                    }
                });
                AlertDialog alert = miaAlert.create();
                alert.show();
            } else if (db.controlloData(d) > 0) {
                final AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
                miaAlert.setMessage("Data non valida: inserire una data minore o uguale a quella odierna.");
                miaAlert.setCancelable(false);
                miaAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                miaAlert.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent libretto = new Intent(NuovoEsameLibretto.this, Libretto.class);
                        startActivity(libretto);
                    }
                });
                AlertDialog alert = miaAlert.create();
                alert.show();
            } else {
                db.saveEsame(nome.getEditableText().toString(), crediti.getEditableText().toString(), data.getEditableText().toString(), voto.getEditableText().toString(), lode, idoneita);
                Intent newExam = new Intent(NuovoEsameLibretto.this, Libretto.class);
                startActivity(newExam);
                finish();
            }
        }
    }
}
