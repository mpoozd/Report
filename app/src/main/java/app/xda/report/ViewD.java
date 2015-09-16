package app.xda.report;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;


public class ViewD extends Fragment {

    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CustomAdDone urgentTodosAdapter;
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
        urgentTodosAdapter = new CustomAdDone(this.getActivity());




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
