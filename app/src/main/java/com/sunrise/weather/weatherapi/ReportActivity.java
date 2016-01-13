package com.sunrise.weather.weatherapi;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class ReportActivity extends MultiFragmentActivity {

    public static final String EXTRA_REP_ID = "com.sunrise.weather.weatherapi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Fragment createFragment() {

        UUID reportUuid = (UUID) getIntent().getExtras().getSerializable(EXTRA_REP_ID);

        return ReportDetailFragment.newInstance(reportUuid);
    }
}
