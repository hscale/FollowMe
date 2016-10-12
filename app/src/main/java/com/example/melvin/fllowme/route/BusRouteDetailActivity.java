package com.example.melvin.fllowme.route;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.example.melvin.fllowme.R;
import com.example.melvin.fllowme.utils.AmapUtil;


/**
 * Created by admin on 2016/8/15.
 */
public class BusRouteDetailActivity extends Activity {

    private BusPath mBuspath;
    private BusRouteResult mBusRouteResult;
    private TextView mTitle, mTitleBusRoute, mDesBusRoute;
    private ListView mBusSegmentList;
    private BusSegmentListAdapter mBusSegmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        getIntentData();
        init();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBuspath = intent.getParcelableExtra("bus_path");
            mBusRouteResult = intent.getParcelableExtra("bus_result");
        }
    }

    private void init() {
        mTitle = (TextView) findViewById(R.id.title_center);
        mTitle.setText("公交线路详情");
        mTitleBusRoute = (TextView) findViewById(R.id.firstline);
        mDesBusRoute = (TextView) findViewById(R.id.secondline);
        String dur = AmapUtil.getFriendlyTime((int) mBuspath.getDuration());
        String dis = AmapUtil.getFriendlyLength((int) mBuspath.getDistance());
        mTitleBusRoute.setText(dur + "(" + dis + ")");
        int taxiCost = (int) mBusRouteResult.getTaxiCost();
        mDesBusRoute.setText("打车约" + taxiCost + "元");
        mDesBusRoute.setVisibility(View.VISIBLE);
        configureListView();
    }

    private void configureListView() {
        mBusSegmentList = (ListView) findViewById(R.id.bus_segment_list);
        mBusSegmentListAdapter = new BusSegmentListAdapter(this.getApplicationContext(), mBuspath.getSteps());
        mBusSegmentList.setAdapter(mBusSegmentListAdapter);
    }

    public void onBackClick(View v) {
        this.finish();
    }

}