package com.example.ila.projectlam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.GregorianCalendar;

import db.AgendaStrings;
import db.DbManager;

/**
 * Created by Ila on 08/01/2015.
 */
public class TaskAsincrono extends AsyncTask<Void, Void, String> {

    private String nomeesame =  "";
    private Context mContext;
    private int NOTIFICATION_ID = 1;
    private Notification mNotification;
    private NotificationManager mNotificationManager;

    public TaskAsincrono(Context context){
        this.mContext = context;
        //Get the notification manager
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected void onProgressUpdate(Void... values) {

    };

    @Override
    protected String doInBackground(Void... params) {
        DbManager db = new DbManager(mContext);
        Cursor agenda = db.queryAgenda();
        GregorianCalendar gc = new java.util.GregorianCalendar();
        gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
        //notificaEsame("Domani : " + gc.getTime());
        String mese = String.valueOf(gc.get(Calendar.MONTH) + 1);
        if((gc.get(Calendar.MONTH)+1) <= 9){
            mese = "0" + String.valueOf(gc.get(Calendar.MONTH) + 1);
        }
        String domani = gc.get(Calendar.DAY_OF_MONTH) + "/" + mese + "/" + gc.get(Calendar.YEAR);
        while(agenda.moveToNext()){
            String data = agenda.getString(agenda.getColumnIndex(AgendaStrings.KEY_DATA));
            if(domani.equals(data)) {
                nomeesame += agenda.getString(agenda.getColumnIndex(AgendaStrings.KEY_NOME));
            }
        }
        return nomeesame;
    }

    @Override
    protected void onPostExecute(String nomeesame) {
        createNotification(nomeesame);
    }

    @Override
    protected void onCancelled() {

    }

    private void createNotification(String nomeesame) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(mContext, Agenda.class);
        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //Build the notification using Notification.Builder
        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(R.drawable.android3)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContentTitle("Ricordati")
                .setContentText("Domani hai l'esame di " + nomeesame + ".")
                .setSound(sound);

        //Get current notification
        mNotification = builder.build();

        //Show the notification
        if(!(nomeesame.equals("")))
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

}