package app.xda.report;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import java.util.List;


public class ViewD extends Fragment {

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ParseQueryRecyclerViewAdapter queryRecyclerViewAdapter;
    private CustomAdDone customAdDone;
    private ListView listView;
    Context context;
    SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_d, container, false);
        // Refreshing listview
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryRecyclerViewAdapter.loadObjects();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        queryRecyclerViewAdapter = new ParseQueryRecyclerViewAdapter(getActivity(),"Post");
        queryRecyclerViewAdapter.loadObjects();

        customAdDone = new CustomAdDone(getActivity(),container);








        rv.setAdapter(queryRecyclerViewAdapter);




        return rootView;

    }




}
