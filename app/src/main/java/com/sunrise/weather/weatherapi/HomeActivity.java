package com.sunrise.weather.weatherapi;

import android.support.v4.app.Fragment;

public class HomeActivity extends MultiFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return LocSpecFragment.newInstance();
    }


}
