package be.piyush.fuelinsight;


import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;

import java.util.Objects;

import be.piyush.fuelinsight.data.reserve.ReserveContract;
import be.piyush.fuelinsight.data.reserve.ReserveProvider;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 2/12/15, 01:15 AM
 * Purpose : A placeholder fragment containing a simple view.
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class ReserveListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView listView;
    SimpleCursorAdapter mAdapter;

    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[] {ReserveContract.COLUMN_ID,
            ReserveContract.COLUMN_DATE, ReserveContract.COLUMN_ODOMETER_READING};

    public ReserveListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserve_list, container, false);
        listView = (ListView) view.findViewById(R.id.reserveList);

        // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {ReserveContract.COLUMN_DATE, ReserveContract.COLUMN_ODOMETER_READING};
        int[] toViews = {R.id.reserveItemDateTxt, R.id.reserveItemOdometerTxt}; // The TextView in simple_list_item_1

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(inflater.getContext(),
                R.layout.reserve_list_item, null,
                fromColumns, toViews, 0);
        listView.setAdapter(mAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = this;
        getLoaderManager().initLoader(0, null, loaderCallbacks);


        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getActivity(), ReserveProvider.RESERVE_CONTENT_URI,
                PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
