package app.report;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DispatchActivity extends Fragment {


    protected List<ParseObject> mMessages;

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CustomAd urgentTodosAdapter;
    private ListView listView;
    Context context;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_dispatch, container, false);
        // Refreshing listview
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listView.setAdapter(mainAdapter);
                mainAdapter.loadObjects();
                if (listView.getAdapter() == mainAdapter) {
                    listView.setAdapter(urgentTodosAdapter);
                    urgentTodosAdapter.loadObjects();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(this.getActivity(), Pid.P_POST);
        mainAdapter.setTextKey(Pid.B_BULD);
        mainAdapter.setTextKey(Pid.P_PARK);
        mainAdapter.setTextKey(Pid.P_PLAT);
        mainAdapter.setImageKey(Pid.I_IMG);





        // Initialize the subclass of ParseQueryAdapter
        urgentTodosAdapter = new CustomAd(this.getActivity());


        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();







// Initialize toggle button
        if (listView.getAdapter() == mainAdapter) {
            listView.setAdapter(urgentTodosAdapter);
            urgentTodosAdapter.loadObjects();
            }

        return rootView;

        }




    }
