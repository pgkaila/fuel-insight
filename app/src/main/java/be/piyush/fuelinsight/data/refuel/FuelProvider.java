package be.piyush.fuelinsight.data.refuel;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import be.piyush.fuelinsight.data.DatabaseHelper;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 1/12/15, 12:15 AM
 * Purpose :
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class FuelProvider extends ContentProvider {

    //Database
    private DatabaseHelper database;

    // used for the UriMacher
    private static final int FUEL_DATA_LIST = 10;
    private static final int FUEL_DATA_DETAILS = 20;

    public static final String PATH_FUEL = "fuel";

    public static final String CONTENT_AUTHORITY = "be.piyush.fuelinsight.data.refuel";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri FUEL_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_FUEL);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, PATH_FUEL, FUEL_DATA_LIST);
        sURIMatcher.addURI(CONTENT_AUTHORITY, PATH_FUEL + "/#", FUEL_DATA_DETAILS);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(FuelContract.TABLE_FUEL_DATA);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case FUEL_DATA_LIST:
                break;
            case FUEL_DATA_DETAILS:
                // adding the ID to the original query
                queryBuilder.appendWhere(FuelContract.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        values.put(FuelContract.COLUMN_CREATED_DATE, new Date().getTime());
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id;
        switch (uriType) {
            case FUEL_DATA_LIST:
                id = sqlDB.insert(FuelContract.TABLE_FUEL_DATA, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PATH_FUEL + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case FUEL_DATA_LIST:
                rowsDeleted = sqlDB.delete(FuelContract.TABLE_FUEL_DATA, selection,
                        selectionArgs);
                break;
            case FUEL_DATA_DETAILS:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FuelContract.TABLE_FUEL_DATA,
                            FuelContract.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(FuelContract.TABLE_FUEL_DATA,
                            FuelContract.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        values.put(FuelContract.COLUMN_UPDATED_DATE, new Date().getTime());
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case FUEL_DATA_LIST:
                rowsUpdated = sqlDB.update(FuelContract.TABLE_FUEL_DATA,
                        values,
                        selection,
                        selectionArgs);
                break;
            case FUEL_DATA_DETAILS:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(FuelContract.TABLE_FUEL_DATA,
                            values,
                            FuelContract.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(FuelContract.TABLE_FUEL_DATA,
                            values,
                            FuelContract.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {
                FuelContract.COLUMN_ID,
                FuelContract.COLUMN_AMOUNT,
                FuelContract.COLUMN_QUANTITY,
                FuelContract.COLUMN_PRICE,
                FuelContract.COLUMN_ODOMETER_READING,
                FuelContract.COLUMN_DATE,
                FuelContract.COLUMN_TIME,
                FuelContract.COLUMN_PETROL_BUNK
        };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
