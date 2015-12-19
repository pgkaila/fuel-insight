package be.piyush.fuelinsight;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.piyush.fuelinsight.data.refuel.FuelContract;
import be.piyush.fuelinsight.data.refuel.FuelProvider;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 2/12/15, 01:23 AM
 * Purpose : A placeholder fragment containing a simple view.
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class StatusFragment extends Fragment {

    public StatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] projection = { FuelContract.COLUMN_AMOUNT, FuelContract.COLUMN_ID };
        Cursor fuelData = inflater.getContext().getContentResolver().query(FuelProvider.FUEL_CONTENT_URI, projection, null, null, null);
        while (fuelData.getCount()!=0) {
            fuelData.moveToNext();
            Log.i("FuelData", "ID: " + fuelData.getString(1) + "  Amount: " + fuelData.getString(0));
        }
        return inflater.inflate(R.layout.fragment_status, container, false);
    }
}
