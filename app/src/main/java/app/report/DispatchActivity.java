package app.report;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DispatchActivity extends ListActivity {

    ListView listview;
    List<ParseObject> ob;
    CustomAd adapter;
    private List<Post> post = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
    }

    protected Void doInBackground(Void... params) {
        // Create the array
        post = new ArrayList<Post>();
        try {
            // Locate the class table named "Country" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Post");
            // Locate the column named "ranknum" in Parse.com and order list
            // by ascending
            ob = query.find();
            for (ParseObject Post : ob) {
                // Locate images in flag column
                ParseFile image = (ParseFile) Post.get("picture");

                Post map = new Post();
                map.setBuild((String) Post.get("build"));
                map.setPark((String) Post.get("park"));
                map.setPlate((String) Post.get("plate"));
                map.setPicture(image.getUrl());
                post.add(map);
            }
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        // Locate the listview in listview_main.xml
        listview = (ListView) findViewById(android.R.id.list);
        // Pass the results into ListViewAdapter.java
        adapter = new CustomAd(DispatchActivity.this,
                post);
        // Binds the Adapter to the ListView
        listview.setAdapter(adapter);
        // Close the progressdialog
    }
}