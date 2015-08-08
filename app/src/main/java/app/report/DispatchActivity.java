package app.report;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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


public class DispatchActivity extends Activity {


    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CustomAd urgentTodosAdapter;
    private ListView listView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

        // Initialize main ParseQueryAdapter
        mainAdapter = new ParseQueryAdapter<ParseObject>(this, "Post");
        mainAdapter.setTextKey("build");
        mainAdapter.setTextKey("park");
        mainAdapter.setTextKey("plate");
        mainAdapter.setImageKey("picture");

        // Initialize the subclass of ParseQueryAdapter
        urgentTodosAdapter = new CustomAd(this);

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

// Initialize toggle button
        if (listView.getAdapter() == mainAdapter) {
            listView.setAdapter(urgentTodosAdapter);
            urgentTodosAdapter.loadObjects();
            }

        }

    }
