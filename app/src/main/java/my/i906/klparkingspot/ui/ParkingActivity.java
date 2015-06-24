package my.i906.klparkingspot.ui;

import android.os.Bundle;

import my.i906.klparkingspot.R;
import my.i906.klparkingspot.fragment.ParkingFragment;

public class ParkingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (savedInstanceState == null) {

            ParkingFragment fragment = ParkingFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }
}
