package be.piyush.fuelinsight.data.old;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by piyush on 30/11/15.
 */
public class FuelContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FUEL = "fuel";

    public static final class FuelEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FUEL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FUEL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FUEL;

        // Table name
        public static final String TABLE_NAME = "fuel";

        public static final String COLUMN_AMOUNT = "amount";

        public static Uri buildFuelUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
