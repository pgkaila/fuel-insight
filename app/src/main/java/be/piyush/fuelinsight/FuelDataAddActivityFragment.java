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

import be.piyush.fuelinsight.data.refuel.FuelContract;
import be.piyush.fuelinsight.data.refuel.FuelProvider;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 2/12/15, 12:23 AM
 * Purpose : A placeholder fragment containing a simple view.
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class FuelDataAddActivityFragment extends Fragment {

    private EditText amount;
    private EditText quantity;
    private EditText price;
    private EditText odometerReading;
    private EditText dateRefuel;
    private EditText timeRefuel;
    private EditText petrolBunk;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private Button saveData;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    public FuelDataAddActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_data_add, container, false);

        findViewById(view);
        createDateTimePickers(inflater.getContext());

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(this.getClass().getName(), "Amount:" + amount.getText());
                ContentValues values = new ContentValues();
                try {
                    values.put(FuelContract.COLUMN_AMOUNT, new Double(amount.getText().toString()));
                    values.put(FuelContract.COLUMN_QUANTITY, new Double(quantity.getText().toString()));
                    values.put(FuelContract.COLUMN_PRICE, new Double(price.getText().toString()));
                    values.put(FuelContract.COLUMN_ODOMETER_READING, new Double(odometerReading.getText().toString()));
                    if (!TextUtils.isEmpty(dateRefuel.getText().toString())) {
                        values.put(FuelContract.COLUMN_DATE, dateFormatter.parse(dateRefuel.getText().toString()).getTime());
                    }
                    values.put(FuelContract.COLUMN_TIME, timeRefuel.getText().toString());
                    values.put(FuelContract.COLUMN_PETROL_BUNK, petrolBunk.getText().toString());
                } catch (NumberFormatException e){
                    Log.e(this.getClass().getName(), e.getLocalizedMessage());
                    //TODO Print toast
                } catch (ParseException e) {
                    Log.e(this.getClass().getName(), "Date parsing error" + e.getLocalizedMessage());
                    //TODO Print toast
                }
                //Inserting in database
                inflater.getContext().getContentResolver().insert(FuelProvider.FUEL_CONTENT_URI, values);
                Log.i(this.getClass().getName(), "Fuel Data inserted in database");
            }
        });
        return view;
    }

    private void findViewById(View view) {
        amount = (EditText) view.findViewById(R.id.amount);
        quantity = (EditText) view.findViewById(R.id.quantity);
        price = (EditText) view.findViewById(R.id.price);
        odometerReading = (EditText) view.findViewById(R.id.odometer);
        dateRefuel = (EditText) view.findViewById(R.id.dateRefuel);
        timeRefuel = (EditText) view.findViewById(R.id.timeRefuel);
        petrolBunk = (EditText) view.findViewById(R.id.petrolBunk);

        dateRefuel.setInputType(InputType.TYPE_NULL);
        timeRefuel.setInputType(InputType.TYPE_NULL);

        saveData = (Button) view.findViewById(R.id.saveFuelData);
    }

    private void createDateTimePickers(Context context){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("h:mm a", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateRefuel.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newTime = Calendar.getInstance();
                newTime.set(1993, 07, 23, hourOfDay, minute);
                timeRefuel.setText(timeFormatter.format(newTime.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);

        dateRefuel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datePickerDialog.show();
                return true;
            }
        });
        dateRefuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        timeRefuel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timePickerDialog.show();
                return true;
            }
        });
        timeRefuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
    }

}
