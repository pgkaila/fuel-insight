package be.piyush.fuelinsight.data.reserve;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 1/12/15, 12:09 AM
 * Purpose :
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class ReserveContract {

    // Database table
    public static final String TABLE_RESERVE = "reserve";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ODOMETER_READING = "odometerReading";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CREATED_DATE = "createDate";
    public static final String COLUMN_UPDATED_DATE = "updateDate";

    // Database creation SQL statement
    private static final String CREATE_FUEL_DATA_TABLE = "create table "
            + TABLE_RESERVE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ODOMETER_READING + " real not null,"
            + COLUMN_DATE + " text not null,"
            + COLUMN_TIME + " text, "
            + COLUMN_CREATED_DATE + " real not null, "
            + COLUMN_UPDATED_DATE + " real"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_FUEL_DATA_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ReserveContract.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVE);
        onCreate(database);
    }
}
