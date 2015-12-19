package be.piyush.fuelinsight.data.old;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by piyush on 30/11/15.
 */
public class FuelDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "fuel.db";

    public FuelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FUEL_TABLE = "CREATE TABLE " + FuelContract.FuelEntry.TABLE_NAME + " (" +
                FuelContract.FuelEntry._ID + " INTEGER PRIMARY KEY," +
                FuelContract.FuelEntry.COLUMN_AMOUNT + " REAL NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_FUEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FuelContract.FuelEntry.TABLE_NAME);
        onCreate(db);
    }
}
