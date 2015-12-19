package be.piyush.fuelinsight.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import be.piyush.fuelinsight.data.refuel.FuelContract;
import be.piyush.fuelinsight.data.reserve.ReserveContract;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 1/12/15, 12:03 AM
 * Purpose :
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fuelinsight.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FuelContract.onCreate(db);
        ReserveContract.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FuelContract.onUpgrade(db, oldVersion, newVersion);
        ReserveContract.onUpgrade(db, oldVersion, newVersion);
    }
}
