package be.piyush.fuelinsight;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created with Android Studio.
 * User: piyush
 * Date: 2/12/15, 12:33 AM
 * Purpose : A placeholder fragment containing a simple view.
 *
 * @author : Piyush <pgkaila@gmail.com>
 * @version : 1.0.0
 * @since : 1.0.0
 */
public class MainActivityFragment extends Fragment {

    private Button addReserveButton;
    private Button addRefuelDataButton;
    private Button viewStatusButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addRefuelDataButton = (Button) view.findViewById(R.id.addRefuelDataButton);
        addReserveButton = (Button) view.findViewById(R.id.addReserveButton);
        viewStatusButton = (Button) view.findViewById(R.id.viewStatusButton);

        addRefuelDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FuelDataAddActivity.class);
                startActivity(intent);
            }
        });

        addReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReserveDataAdd.class);
                startActivity(intent);
            }
        });

        viewStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Status.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
