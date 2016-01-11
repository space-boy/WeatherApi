package com.sunrise.weather.weatherapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

public class ReportDetailFragment extends Fragment {

    private static final String ARG_REPORT_ID = "report_id";
    private WeatherReport mWeatherReport;

    public static ReportDetailFragment newInstance(UUID weatherUuid) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_REPORT_ID,weatherUuid);
        ReportDetailFragment fragment = new ReportDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID reportid = (UUID) getArguments().getSerializable(ARG_REPORT_ID);
        mWeatherReport = WeatherRepSingleton.getWeatherRepSingleton(getActivity()).getWeatherReport(reportid);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_detail_fragment,container,false);

        TextView mLocationTextview = (TextView) v.findViewById(R.id.location_textview);

        mLocationTextview.setText(mWeatherReport.getLocation());
        return v;

    }
}
