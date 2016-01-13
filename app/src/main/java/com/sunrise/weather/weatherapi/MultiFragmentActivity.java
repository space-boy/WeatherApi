package com.sunrise.weather.weatherapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public abstract class MultiFragmentActivity extends AppCompatActivity implements LocSpecFragment.Callbacks {
    private static final String TAG = "MultiFragmentAct";
    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_materdetail;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onReportSelected(WeatherReport report) {

        if(findViewById(R.id.detail_fragment_container) == null){

            Intent i = new Intent(this,ReportActivity.class);
            i.putExtra(ReportActivity.EXTRA_REP_ID,report.getWeatherId());

            startActivity(i);

            //Fragment reportDetail = ReportDetailFragment.newInstance(report.getWeatherId());
            //FragmentManager fm = getSupportFragmentManager();
            //fm.beginTransaction().replace(R.id.detail_fragment_container,reportDetail).commit();

        }else{
            Fragment reportDetail = ReportDetailFragment.newInstance(report.getWeatherId());
            FragmentManager fm = getSupportFragmentManager();

            fm.beginTransaction().remove(reportDetail).commit();

            fm.beginTransaction().replace(R.id.detail_fragment_container,reportDetail).commit();
        }
    }
}
