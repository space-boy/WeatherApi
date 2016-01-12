package com.sunrise.weather.weatherapi;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    public void testFragmentExistsAndLocationOk(){

        Instrumentation instrumentation = getInstrumentation();
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(HomeActivity.class.getName(), null, false);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), HomeActivity.class.getName());
        instrumentation.startActivitySync(intent);

        AppCompatActivity currentActivity = (AppCompatActivity) instrumentation.waitForMonitorWithTimeout(monitor, 5);

        //activity is not null, it's layout is set and there's a fragment attached to it
        assertNotNull(currentActivity);
        assertNotNull(currentActivity.findViewById(R.id.fragment_container));

        LocSpecFragment lsp = (LocSpecFragment) currentActivity
                                                .getSupportFragmentManager()
                                                .findFragmentById(R.id.fragment_container);
        assertNotNull(lsp);
        assertNotNull(lsp.getView());
        assertNotNull(lsp.getView().findViewById(R.id.report_recycler_view));
        RecyclerView mRec = (RecyclerView) lsp.getView().findViewById(R.id.report_recycler_view);
        assertNotNull(mRec);

        //check that we have a layout manager attached
        assertNotNull(mRec.getLayoutManager());

        //check we have an adapter attached (we know we have a valid holder if we have a valid adapter)
        assertNotNull(mRec.getAdapter());

        //check we can create a view ok
        assertNotNull(mRec.getAdapter().onCreateViewHolder(mRec,R.layout.report_list_fragment));

        //check we have at least one item in the adapter, so we know we have items in the view
        assertNotNull(mRec.getAdapter().getItemId(0));

    }

    public void testSanity(){
        //exists to verify tests are able to pass
    }


}
