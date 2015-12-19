package be.piyush.fuelinsight.data.old;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by piyush on 30/11/15.
 */
public class FuelProvider extends ContentProvider {

    private FuelDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new FuelDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            long _id = db.insert(FuelContract.FuelEntry.TABLE_NAME, null, values);
            if (_id > 0) {
                return FuelContract.FuelEntry.buildFuelUri(_id);
            } else {
                throw new android.database.SQLException("Failed to insert row into ");
            }
        } catch (Exception e) {
            Log.e("DB error", e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
