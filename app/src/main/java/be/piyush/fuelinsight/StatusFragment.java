package be.piyush.fuelinsight;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

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

    private LineChart mChart;

    public StatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_status, container, false);

        //TODO Need lot of design changes in chart
        //TODO Have to update LineChart with CombineChart to display LineChart with BarChart
        mChart = (LineChart) view.findViewById(R.id.chartInsight);
        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);

        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < 12; index++)
            entries.add(new Entry(getRandom(25, 10), index));

        LineDataSet set = new LineDataSet(entries, "DataSet1");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleSize(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));


        for (int index = 0; index < 12; index++)
            entries.add(new Entry(getRandom(25, 10), index));

        LineDataSet set2 = new LineDataSet(entries, "DataSet2");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleSize(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setDrawCubic(true);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);
        d.addDataSet(set2);

        mChart.setData(d);
        mChart.setDescription("Fuel Insight");
        mChart.animateXY(2000, 2000);
        mChart.invalidate();

//        String[] projection = { FuelContract.COLUMN_AMOUNT, FuelContract.COLUMN_ID };
//        Cursor fuelData = inflater.getContext().getContentResolver().query(FuelProvider.FUEL_CONTENT_URI, projection, null, null, null);
//        while (fuelData.getCount()!=0) {
//            fuelData.moveToNext();
//            Log.i("FuelData", "ID: " + fuelData.getString(1) + "  Amount: " + fuelData.getString(0));
//        }
        return view;
    }

    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
