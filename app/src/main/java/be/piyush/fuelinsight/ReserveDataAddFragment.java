package be.piyush.fuelinsight;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import be.piyush.fuelinsight.data.reserve.ReserveContract;
import be.piyush.fuelinsight.data.reserve.ReserveProvider;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 2/12/15, 12:37 AM
 * Purpose : A placeholder fragment containing a simple view.
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class ReserveDataAddFragment extends Fragment {

    private EditText odometerReading;
    private EditText dateReserve;
    private EditText timeReserve;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private Button saveData;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    public ReserveDataAddFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserve_data_add, container, false);

        findViewById(view);
        createDateTimePickers(inflater.getContext());

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(this.getClass().getName(), "Odometer Reading:" + odometerReading.getText());
                ContentValues values = new ContentValues();
                try {
                    values.put(ReserveContract.COLUMN_ODOMETER_READING, new Double(odometerReading.getText().toString()));
                    if (!TextUtils.isEmpty(dateReserve.getText().toString())) {
                        values.put(ReserveContract.COLUMN_DATE, dateFormatter.parse(dateReserve.getText().toString()).getTime());
                    }
                    values.put(ReserveContract.COLUMN_TIME, timeReserve.getText().toString());
                } catch (NumberFormatException e) {
                    Log.e(this.getClass().getName(), e.getLocalizedMessage());
                    //TODO Print toast
                } catch (ParseException e) {
                    Log.e(this.getClass().getName(), "Date parsing error" + e.getLocalizedMessage());
                    //TODO Print toast
                }
                //Inserting in database
                inflater.getContext().getContentResolver().insert(ReserveProvider.RESERVE_CONTENT_URI, values);
                Log.i(this.getClass().getName(), "Reserve Data inserted in database");
            }
        });
        
        return view;
    }

    private void findViewById(View view) {
        odometerReading = (EditText) view.findViewById(R.id.odometerReserve);
        dateReserve = (EditText) view.findViewById(R.id.dateReserve);
        timeReserve = (EditText) view.findViewById(R.id.timeReserve);

        dateReserve.setInputType(InputType.TYPE_NULL);
        timeReserve.setInputType(InputType.TYPE_NULL);

        saveData = (Button) view.findViewById(R.id.saveReserveData);
    }

    private void createDateTimePickers(Context context){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateReserve.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newTime = Calendar.getInstance();
                newTime.set(1993, 07, 23, hourOfDay, minute);
                timeReserve.setText(timeFormatter.format(newTime.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);

        dateReserve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datePickerDialog.show();
                return true;
            }
        });
        dateReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        timeReserve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timePickerDialog.show();
                return true;
            }
        });
        timeReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
    }
}
