package db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Ila on 25/11/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DBNAME="db.esami";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String creaAgenda = "CREATE TABLE "+ AgendaStrings.TBL_NAME +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                AgendaStrings.KEY_NOME + " TEXT," +
                AgendaStrings.KEY_CREDITI + " TEXT," +
                AgendaStrings.KEY_DATA + " DATE )";
        db.execSQL(creaAgenda);

        String creaLibretto = "CREATE TABLE "+ LibrettoStrings.TBL_NAME +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                LibrettoStrings.FIELD_NOME + " TEXT," +
                LibrettoStrings.FIELD_CREDITI + " TEXT," +
                LibrettoStrings.FIELD_DATA + " DATE," +
                LibrettoStrings.FIELD_VOTO + " TEXT," +
                LibrettoStrings.FIELD_LODE + " BOOLEAN," +
                LibrettoStrings.FIELD_IDONEITA + " TEXT )";
        db.execSQL(creaLibretto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}