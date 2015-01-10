package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ila on 25/11/2014.
 */
public class DbManager{

    private DatabaseHelper dbhelper;

    public DbManager(Context ctx){
        dbhelper=new DatabaseHelper(ctx);
    }

    public void saveEsameFuturo(String nome, int crediti, String data){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AgendaStrings.KEY_NOME, nome);
        cv.put(AgendaStrings.KEY_CREDITI, crediti);
        cv.put(AgendaStrings.KEY_DATA, data);
        try
        {
            db.insert(AgendaStrings.TBL_NAME, null, cv);
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }

    public void saveEsame(String nome, String crediti, String data, String voto, boolean lode, boolean idoneita){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LibrettoStrings.FIELD_NOME, nome);
        cv.put(LibrettoStrings.FIELD_CREDITI, crediti);
        cv.put(LibrettoStrings.FIELD_DATA, data);
        cv.put(LibrettoStrings.FIELD_VOTO, voto);
        cv.put(LibrettoStrings.FIELD_LODE, lode);
        cv.put(LibrettoStrings.FIELD_IDONEITA, idoneita);
        try
        {
            db.insert(LibrettoStrings.TBL_NAME, null, cv);
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }

    public boolean deleteEsame(long id)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try
        {
            if (db.delete(LibrettoStrings.TBL_NAME, LibrettoStrings.FIELD_ID+"=?", new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }
    }

    public boolean deleteEsameFuturo(long id)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try
        {
            if (db.delete(AgendaStrings.TBL_NAME, AgendaStrings.KEY_ID+"=?", new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }
    }

    public Cursor query() {
        Cursor crs = null;
        try {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            crs = db.query(LibrettoStrings.TBL_NAME, null, null, null, null, null, null, null);
        } catch (SQLiteException sqle) {
            //Gestisci eccezione
        }
        return crs;
    }

    public Cursor queryAgenda() {
        Cursor crs = null;
        try {
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            crs = db.query(AgendaStrings.TBL_NAME, null, null, null, null, null, null, null);
        } catch (SQLiteException sqle) {
            //Gestisci eccezione
        }
        return crs;
    }

    public Cursor crediti(){
        Cursor crs = null;
        try{
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String[] c = {LibrettoStrings.FIELD_CREDITI, LibrettoStrings.FIELD_IDONEITA};
            crs = db.query(LibrettoStrings.TBL_NAME, c, null, null, null, null, null);
        }catch(SQLiteException sqle){
            //Gestisci eccezione
        }
        return crs;
    }

    public Cursor voti(){
        Cursor crs = null;
        try{
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String[] c = {LibrettoStrings.FIELD_VOTO, LibrettoStrings.FIELD_IDONEITA};
            crs = db.query(LibrettoStrings.TBL_NAME, c, null, null, null, null, null);
        }catch(SQLiteException sqle){
            //Gestisci eccezione
        }
        return crs;
    }

    public Cursor mediaPonderata(){
        Cursor crs = null;
        try{
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String[] c = {LibrettoStrings.FIELD_VOTO, LibrettoStrings.FIELD_CREDITI};
            crs = db.query(LibrettoStrings.TBL_NAME, c, null, null, null, null, null);
        }catch(SQLiteException sqle){
            //Gestisci eccezione
        }
        return crs;
    }


    public int controlloData(String datae) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datainserita = formatter.parse(datae);
        Calendar calendar = new GregorianCalendar();
        Date newDate = calendar.getTime();
        int controllo = datainserita.compareTo(newDate);
        return controllo;
    }
}